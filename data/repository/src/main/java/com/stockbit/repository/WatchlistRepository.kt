package com.stockbit.repository

import androidx.paging.PagingData
import com.stockbit.model.ExampleModel
import com.stockbit.model.Watchlist
import com.stockbit.repository.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    suspend fun getWatchlist(): Flow<PagingData<Watchlist>>
}