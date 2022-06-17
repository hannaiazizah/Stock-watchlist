package com.stockbit.feature_watchlist.di

import com.stockbit.feature_watchlist.presentation.WatchlistViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val watchlistModule = module {
    viewModel {
        WatchlistViewModel(get(), get())
    }
}