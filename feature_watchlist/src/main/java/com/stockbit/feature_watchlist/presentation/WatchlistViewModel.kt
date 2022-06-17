package com.stockbit.feature_watchlist.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.stockbit.common.base.BaseViewModel
import com.stockbit.model.Watchlist
import com.stockbit.repository.AppDispatchers
import com.stockbit.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class WatchlistViewModel(
    private val watchlistRepository: WatchlistRepository,
    private val appDispatchers: AppDispatchers
): BaseViewModel() {

    lateinit var pagingDataFlow: Flow<PagingData<Watchlist>>
    fun getWatchlist() {
        viewModelScope.launch(appDispatchers.io) {
            pagingDataFlow = watchlistRepository.getWatchlist()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
        }
    }
}