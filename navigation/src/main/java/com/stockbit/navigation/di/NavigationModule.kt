package com.stockbit.navigation.di

import com.stockbit.navigation.Navigator
import org.koin.dsl.module

val navigationModule = module {
    single { Navigator() }
}