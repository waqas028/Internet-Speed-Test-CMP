package com.farimarwat.speedtest

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farimarwat.speedtest.presentation.navigation.Screen
import com.farimarwat.speedtest.presentation.ui.HomeScreen
import com.farimarwat.speedtest.presentation.ui.TestScreen
import com.farimarwat.speedtest.presentation.ui.getColorScheme
import com.farimarwat.speedtest.presentation.viewmodel.HomeViewModel
import com.farimarwat.speedtest.presentation.viewmodel.TestViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
    homeViewModel: HomeViewModel = koinViewModel(),
    testViewModel: TestViewModel = koinViewModel()
) {
    MaterialTheme(
        colorScheme = getColorScheme()
    ) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ){
            composable(
                route = Screen.Home.route
            ){
                HomeScreen(
                    viewModel = homeViewModel,
                    navController = navController
                )
            }
            composable(
                route = Screen.Test.route
            ){
                TestScreen(
                    viewModel = testViewModel,
                    navController = navController
                )
            }
        }
    }
}