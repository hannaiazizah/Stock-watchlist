package com.stockbit.repository.di

import androidx.paging.ExperimentalPagingApi
import com.stockbit.repository.AppDispatchers
import com.stockbit.repository.ExampleRepository
import com.stockbit.repository.ExampleRepositoryImpl
import com.stockbit.repository.WatchlistRepository
import com.stockbit.repository.WatchlistRepositoryImpl
import com.stockbit.repository.mediator.WatchlistRemoteMediator
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

@ExperimentalPagingApi
val repositoryModule = module {
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
    factory { ExampleRepositoryImpl(get(), get()) as ExampleRepository }

    factory { WatchlistRepositoryImpl(get(), get()) as WatchlistRepository }
    factory {
        WatchlistRemoteMediator(
            watchlistService = get(),
            watchlistDao = get(),
            remoteKeysDao = get()
        )
    }
}