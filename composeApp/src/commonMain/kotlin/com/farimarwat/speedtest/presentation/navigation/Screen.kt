package com.farimarwat.speedtest.presentation.navigation

import org.jetbrains.compose.resources.DrawableResource
import speedtest.composeapp.generated.resources.Res
import speedtest.composeapp.generated.resources.ic_history
import speedtest.composeapp.generated.resources.ic_home
import speedtest.composeapp.generated.resources.ic_settings

sealed class Screen(
    val route:String,
    val title:String = "",
    val icon: DrawableResource? = null,
    val isNavigationBarItem: Boolean = false
    ) {
    object Home:Screen(
        title = "Home",
        route = "home_screen",
        icon = Res.drawable.ic_home,
        isNavigationBarItem = true
    )
    object Test:Screen(
        title = "Test",
        route = "test_screen"
    )
    object History:Screen(
        title = "History",
        route = "history_screen",
        icon = Res.drawable.ic_history,
        isNavigationBarItem = true
    )
    object Settings:Screen(
        title = "Settings",
        route = "settings_screen",
        icon = Res.drawable.ic_settings,
        isNavigationBarItem = true
    )
}