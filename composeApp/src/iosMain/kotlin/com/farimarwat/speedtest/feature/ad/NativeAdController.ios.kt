package com.farimarwat.speedtest.feature.ad

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

class NativeAdControlleriOS:NativeAdController {
     override fun loadAd() {

     }

     override fun showAd(container: Any) {

     }

     override fun dispose() {

     }

 }
@Composable
actual fun rememberNativeAdController(adUnitId:String): NativeAdController = remember {
    NativeAdControlleriOS()
}.also { controller ->
    DisposableEffect(Unit) {
        onDispose {
            controller.dispose()
        }
    }
}
