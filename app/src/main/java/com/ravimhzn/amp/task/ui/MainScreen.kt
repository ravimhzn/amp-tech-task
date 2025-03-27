package com.ravimhzn.amp.task.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ravimhzn.amp.task.viewModel.MainViewModel
import com.ravimhzn.amp.ui.theme.BaseAmpTheme

@Composable
fun MainViewController(
    navHostController: NavHostController,
    activity: MainActivity,
    viewModel: MainViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        
    }
    MainView()
}

@Composable
fun MainView() {
    Column(
        modifier = Modifier
            .background(Color.Blue)
            .fillMaxSize()
    ) {
        Text(text = "Hello AMP")
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    BaseAmpTheme {
        MainView()
    }
}