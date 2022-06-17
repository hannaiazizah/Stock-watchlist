package com.stockbit.feature_watchlist.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.stockbit.common.base.BaseViewModel
import com.stockbit.model.Watchlist
import com.stockbit.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class WatchlistViewModel(
    private val watchlistRepository: WatchlistRepository
): BaseViewModel() {

    val pagingDataFlow: Flow<PagingData<Watchlist>>

    init {
        pagingDataFlow = getWatchlist()
    }

    private fun getWatchlist(): Flow<PagingData<Watchlist>> = watchlistRepository.getWatchlist()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)
}