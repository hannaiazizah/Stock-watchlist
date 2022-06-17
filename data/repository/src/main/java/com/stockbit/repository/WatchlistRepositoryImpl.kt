package com.stockbit.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.stockbit.local.dao.RemoteKeysDao
import com.stockbit.local.dao.WatchlistDao
import com.stockbit.model.Watchlist
import com.stockbit.remote.WatchlistService
import com.stockbit.repository.mediator.WatchlistRemoteMediator
import kotlinx.coroutines.flow.Flow

class WatchlistRepositoryImpl (
    private val watchlistDao: WatchlistDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val watchlistService: WatchlistService
): WatchlistRepository {

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }

    override fun getWatchlist(): Flow<PagingData<Watchlist>> {
        val pagingSourceFactory = { watchlistDao.getWatchlist() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = WatchlistRemoteMediator(
                watchlistService = watchlistService,
                remoteKeysDao = remoteKeysDao,
                watchlistDao = watchlistDao
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}