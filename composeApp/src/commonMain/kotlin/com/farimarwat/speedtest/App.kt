package com.farimarwat.speedtest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.farimarwat.speedtest.presentation.component.SimpleTopAppBar
import com.farimarwat.speedtest.presentation.navigation.Screen
import com.farimarwat.speedtest.presentation.screen.HistoryScreen
import com.farimarwat.speedtest.presentation.screen.HomeScreen
import com.farimarwat.speedtest.presentation.screen.MapScreen
import com.farimarwat.speedtest.presentation.screen.SettingsScreen
import com.farimarwat.speedtest.presentation.screen.TestScreen
import com.farimarwat.speedtest.presentation.ui.getColorScheme
import com.farimarwat.speedtest.presentation.viewmodel.HistoryScreenViewModel
import com.farimarwat.speedtest.presentation.viewmodel.HomeViewModel
import com.farimarwat.speedtest.presentation.viewmodel.MapScreenViewModel
import com.farimarwat.speedtest.presentation.viewmodel.TestViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import speedtest.composeapp.generated.resources.Res
import speedtest.composeapp.generated.resources.server

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(
    homeViewModel: HomeViewModel = koinViewModel(),
    testViewModel: TestViewModel = koinViewModel(),
    historyScreenViewModel: HistoryScreenViewModel = koinViewModel(),
    mapScreenViewModel: MapScreenViewModel = koinViewModel()
) {
    MaterialTheme(
        colorScheme = getColorScheme()
    ) {
        var showBottomBar by remember { mutableStateOf(true) }
        val navController = rememberNavController()
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val route = currentBackStackEntry?.destination?.route
        LaunchedEffect(route){
            showBottomBar = when(route){
                Screen.Home.route, Screen.History.route, Screen.Settings.route -> true
                else -> false
            }
        }
        val list = listOf(
            Screen.Home,
            Screen.History,
            Screen.Settings
        )

        val topAppBarList = listOf(
            Screen.History,
            Screen.Settings
        )

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                if (topAppBarList.any { it.route == route }) SimpleTopAppBar(
                    navController = navController,
                    scrollBehavior = scrollBehavior,
                )
            },
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
                ) {
                    NavigationBar {
                        list.forEach { item ->
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
                                selected = route == item.route,
                                onClick = {
                                    navController.navigate(item.route)
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            val modifier = Modifier.padding(innerPadding)

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
                        navController = navController,
                        modifier = modifier
                    )
                }
                composable(Screen.History.route) {
                    HistoryScreen(
                        navController = navController,
                        historyScreenViewModel = historyScreenViewModel,
                        mapScreenViewModel = mapScreenViewModel,
                        modifier = modifier
                    )
                }
                composable(Screen.Settings.route) {
                    SettingsScreen()
                }

                composable(Screen.TestMap.route){
                    MapScreen(
                        navController = navController,
                        mapScreenViewModel = mapScreenViewModel
                    )
                }

            }
        }
    }
}