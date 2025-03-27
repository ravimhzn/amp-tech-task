package com.ravimhzn.amp.task

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavigationGraph(
    navHostController: NavHostController,
    activity: MainActivity
) {

    AmpNavHost(navHostController, AmpRoute.MainScreen.route) {
        composable(AmpRoute.MainScreen.route) {
            MainViewController(navHostController, activity)
        }

        composable(AmpRoute.TransferScreen.route) {
            TransferScreen(navHostController, activity)
        }
    }
}

sealed class AmpRoute(val route: String) {
    data object MainScreen : AmpRoute("MainScreen")
    data object TransferScreen : AmpRoute("TransferScreen")
}

@Composable
fun AmpNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController,
        startDestination = startDestination,
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) },
        modifier = modifier,
        builder = builder
    )
}