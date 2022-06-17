package com.stockbit.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.stockbit.local.dao.WatchlistDao
import com.stockbit.model.Watchlist
import com.stockbit.repository.mediator.WatchlistRemoteMediator
import kotlinx.coroutines.flow.Flow

class WatchlistRepositoryImpl @ExperimentalPagingApi constructor(
    private val watchlistDao: WatchlistDao,
    private val watchlistRemoteMediator: WatchlistRemoteMediator
): WatchlistRepository {


    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }

    override suspend fun getWatchlist(): Flow<PagingData<Watchlist>> {
        val pagingSourceFactory = { watchlistDao.getWatchlist() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = watchlistRemoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}