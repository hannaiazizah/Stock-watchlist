package com.stockbit.navigation

import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController

    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        NavigationFlow.WatchlistFlow -> navController.navigate(MainNavGraphDirections.actionToWatchlistFlow())
    }
}