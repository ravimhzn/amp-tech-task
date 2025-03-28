package com.ravimhzn.amp.task.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ravimhzn.amp.task.model.TransactionResponse
import com.ravimhzn.amp.task.ui.MainActivity
import com.ravimhzn.amp.task.ui.MainViewController
import com.ravimhzn.amp.task.ui.TransferAndSaveView

const val TRANSACTION_DETAIL_KEY = "transaction_detail"

@Composable
fun MainNavigationGraph(
    navHostController: NavHostController,
    activity: MainActivity
) {
    AmpNavHost(navHostController, AmpRoute.MainScreen.route) {
        composable(AmpRoute.MainScreen.route) {
            MainViewController(navHostController)
        }

        composable(AmpRoute.TransferAndSaveScreen.route) {
            val result =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<TransactionResponse>(
                    TRANSACTION_DETAIL_KEY
                )
            TransferAndSaveView(navHostController, result)
        }
    }
}

sealed class AmpRoute(val route: String) {
    data object MainScreen : AmpRoute("MainScreen")
    data object TransferAndSaveScreen : AmpRoute("TransferScreen")
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
        modifier = modifier,
        builder = builder
    )
}