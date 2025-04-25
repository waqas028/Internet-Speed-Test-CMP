package com.farimarwat.speedtest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.farimarwat.speedtest.presentation.navigation.Screen
import com.farimarwat.speedtest.presentation.screen.HistoryScreen
import com.farimarwat.speedtest.presentation.screen.HomeScreen
import com.farimarwat.speedtest.presentation.screen.SettingsScreen
import com.farimarwat.speedtest.presentation.screen.TestScreen
import com.farimarwat.speedtest.presentation.ui.getColorScheme
import com.farimarwat.speedtest.presentation.viewmodel.HistoryScreenViewModel
import com.farimarwat.speedtest.presentation.viewmodel.HomeViewModel
import com.farimarwat.speedtest.presentation.viewmodel.TestViewModel
import com.farimarwat.speedtest.utils.decodeUrl
import io.ktor.http.decodeURLPart
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import speedtest.composeapp.generated.resources.Res
import speedtest.composeapp.generated.resources.server

@Composable
@Preview
fun App(
    homeViewModel: HomeViewModel = koinViewModel(),
    testViewModel: TestViewModel = koinViewModel(),
    historyScreenViewModel: HistoryScreenViewModel = koinViewModel()
) {
    MaterialTheme(
        colorScheme = getColorScheme()
    ) {
        var showBottomBar by remember { mutableStateOf(true) }
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val route = currentBackStackEntry?.destination?.route
        LaunchedEffect(route){
            when(route){
                Screen.Home.route, Screen.History.route, Screen.Settings.route ->{
                    showBottomBar = true
                }
                else ->{
                    showBottomBar = false
                }
            }
        }
        val list = listOf(
            Screen.Home,
            Screen.History,
            Screen.Settings
        )
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight }, // Starts from bottom
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                    ),
                    exit = fadeOut() + slideOutVertically(
                        targetOffsetY = { fullHeight -> fullHeight }, // Exits to bottom
                        animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
                    )
                ){
                    NavigationBar {
                        var selected by remember { mutableStateOf(0) }
                        list.forEachIndexed { index,item ->
                            NavigationBarItem(
                                label = { Text(item.title) },
                                icon = {
                                    Icon(
                                        modifier = Modifier
                                            .size(20.dp),
                                        painter = painterResource(item.icon ?: Res.drawable.server),
                                        contentDescription = item.title
                                    )
                                },
                                selected = selected == index,
                                onClick = {
                                    selected = index
                                    navController.navigate(item.route)
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->

            val modifier = Modifier
                .padding(innerPadding)
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
                    route = Screen.Test.route,
                ){
                    TestScreen(
                        testViewModel = testViewModel,
                        homeViewModel = homeViewModel,
                        navController = navController
                    )
                }
                composable(Screen.History.route) {
                    HistoryScreen(
                        historyScreenViewModel = historyScreenViewModel,
                        modifier = modifier
                    )
                }
                composable(Screen.Settings.route) {
                    SettingsScreen()
                }
            }
        }
    }
}