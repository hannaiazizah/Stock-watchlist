package com.stockbit.feature_login.di

import com.stockbit.feature_login.presentation.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel {
        LoginViewModel()
    }
}