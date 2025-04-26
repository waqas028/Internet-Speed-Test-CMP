package com.farimarwat.speedtest.feature.ad

import androidx.compose.runtime.Composable

interface  NativeAdController {
    fun loadAd()
    fun showAd(container: Any)
    fun dispose()
}

@Composable
expect fun rememberNativeAdController(adUnitId:String):NativeAdController



