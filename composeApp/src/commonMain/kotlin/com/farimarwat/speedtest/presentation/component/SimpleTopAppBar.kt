package com.farimarwat.speedtest.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farimarwat.speedtest.presentation.navigation.Screen
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import speedtest.composeapp.generated.resources.Res
import speedtest.composeapp.generated.resources.app_name
import speedtest.composeapp.generated.resources.settings
import speedtest.composeapp.generated.resources.test_history

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
){
    val currentDestination = navController.currentDestination?.route ?: ""
    val appbarTitle = when{
        currentDestination.contains(Screen.History.title, ignoreCase = true) -> stringResource(Res.string.test_history)
        currentDestination.contains(Screen.Settings.title, ignoreCase = true) -> stringResource(Res.string.settings)
        else -> stringResource(Res.string.app_name)
    }
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.Transparent,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceDim
        ),
        title = {
            Text(
                text = appbarTitle,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        actions = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun SimpleTopAppBarPreview() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    SimpleTopAppBar(rememberNavController(), scrollBehavior)
}