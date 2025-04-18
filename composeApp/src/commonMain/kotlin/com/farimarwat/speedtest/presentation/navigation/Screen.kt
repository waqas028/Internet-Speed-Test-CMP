package com.farimarwat.speedtest.presentation.navigation

sealed class Screen(val route:String) {
    object Home:Screen("home_screen")
    object Test:Screen("test_screen")
}