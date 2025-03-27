package com.ravimhzn.amp.task.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.ravimhzn.amp.task.navigation.MainNavigationGraph
import com.ravimhzn.amp.ui.theme.BaseAmpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseAmpTheme {
                MainNavigationGraph(
                    navHostController = rememberNavController(),
                    this
                )
            }
        }
    }
}