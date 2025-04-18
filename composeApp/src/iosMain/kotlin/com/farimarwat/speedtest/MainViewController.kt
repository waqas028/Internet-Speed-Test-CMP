package com.farimarwat.speedtest

import androidx.compose.ui.window.ComposeUIViewController
import com.farimarwat.speedtest.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }