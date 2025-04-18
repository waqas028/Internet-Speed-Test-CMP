package com.farimarwat.speedtest.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.farimarwat.speedtest.presentation.viewmodel.TestViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TestScreen(
    viewModel: TestViewModel,
    navController: NavHostController
    ){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
}