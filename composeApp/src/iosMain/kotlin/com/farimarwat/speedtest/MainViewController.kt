package com.farimarwat.speedtest

import androidx.compose.ui.window.ComposeUIViewController
import cocoapods.Google_Mobile_Ads_SDK.GADMobileAds
import com.farimarwat.speedtest.di.initKoin
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
        GADMobileAds.sharedInstance().startWithCompletionHandler(null)
    }
) { App() }