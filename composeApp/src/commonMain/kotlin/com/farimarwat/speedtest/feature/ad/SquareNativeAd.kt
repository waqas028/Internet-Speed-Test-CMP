package com.farimarwat.speedtest.feature.ad

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun  SquareNativeAd(
    modifier:Modifier,
    nativeAdController: NativeAdController,
    adColors: AdColors? = null)