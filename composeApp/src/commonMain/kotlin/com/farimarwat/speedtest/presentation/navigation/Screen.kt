package com.farimarwat.speedtest.presentation.navigation

import org.jetbrains.compose.resources.DrawableResource
import speedtest.composeapp.generated.resources.Res
import speedtest.composeapp.generated.resources.ic_history
import speedtest.composeapp.generated.resources.ic_home
import speedtest.composeapp.generated.resources.ic_settings

sealed class Screen(
    val route:String,
    val title:String = "",
    val icon: DrawableResource? = null
    ) {
    object Home:Screen(
        title = "Home",
        route = "home_screen",
        icon = Res.drawable.ic_home
    )
    object Test:Screen(
        title = "Test",
        route = "test_screen"
    )
    object History:Screen(
        title = "History",
        route = "history_screen",
        icon = Res.drawable.ic_history
    )
    object Settings:Screen(
        title = "Settings",
        route = "settings_screen",
        icon = Res.drawable.ic_settings
    )
    object TestMap:Screen(
        title = "Test Map",
        route = "test_map"
    )
}