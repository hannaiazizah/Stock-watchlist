package com.stockbit.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.stockbit.common.utils.toChange
import com.stockbit.common.utils.toCurrency
import com.stockbit.common.utils.toPercentage
import com.stockbit.local.dao.RemoteKeysDao
import com.stockbit.local.dao.WatchlistDao
import com.stockbit.model.RemoteKeys
import com.stockbit.model.Watchlist
import com.stockbit.remote.WatchlistService
import java.io.IOException

@ExperimentalPagingApi
class WatchlistRemoteMediator(
    val watchlistService: WatchlistService,
    val watchlistDao: WatchlistDao,
    val remoteKeysDao: RemoteKeysDao
): RemoteMediator<Int, Watchlist>() {
    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Watchlist>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            val apiResponse = watchlistService.fetchCryptoWatchlist(page, state.config.pageSize)

            val repos = apiResponse.body()
            val endOfPaginationReached = repos == null

            // clear all tables in the database
            if (loadType == LoadType.REFRESH) {
                remoteKeysDao.clearRemoteKeys()
                watchlistDao.clearWatchlist()
            }
            val prevKey = if (page == 0) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val keys = repos?.watchlist?.map {
                RemoteKeys(repoId = it.coinInfo.id.toLong(), prevKey = prevKey, nextKey = nextKey)
            }
            val watchlist = repos?.watchlist?.map {
                Watchlist(
                    id = it.coinInfo.id.toLong(),
                    symbol = it.coinInfo.symbol,
                    name = it.coinInfo.description,
                    change = it.rawInfo?.detailInfo?.change?.toChange() ?: "-",
                    changepct = it.rawInfo?.detailInfo?.changePercentage?.toPercentage() ?: "-",
                    price = it.rawInfo?.detailInfo?.price?.toCurrency() ?: "-",
                    changeAmount = it.rawInfo?.detailInfo?.change ?: 0.0
                )
            }

            keys?.let {
                remoteKeysDao.save(it)
            }
            watchlist?.let {
                watchlistDao.save(it)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            println(exception.localizedMessage)
            return MediatorResult.Error(exception)
        } catch (exception: Exception) {
            println(exception.localizedMessage)
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Watchlist>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { watchlist ->
                // Get the remote keys of the last item retrieved
                remoteKeysDao.remoteKeysRepoId(watchlist.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Watchlist>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { watchlist ->
                // Get the remote keys of the first items retrieved
                remoteKeysDao.remoteKeysRepoId(watchlist.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Watchlist>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { watchlistId ->
                remoteKeysDao.remoteKeysRepoId(watchlistId)
            }
        }
    }
}