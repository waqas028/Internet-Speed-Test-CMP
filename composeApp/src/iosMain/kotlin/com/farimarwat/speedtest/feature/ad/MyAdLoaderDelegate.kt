package com.farimarwat.speedtest.feature.ad

import cocoapods.Google_Mobile_Ads_SDK.GADAdLoader
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAd
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAdLoaderDelegateProtocol
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
class MyAdLoaderDelegate(
    private val onAdLoaded: (GADNativeAd) -> Unit = {}
) : NSObject(), GADNativeAdLoaderDelegateProtocol {
    @ExperimentalForeignApi
    override fun adLoader(adLoader: GADAdLoader, didFailToReceiveAdWithError: NSError) {
        println(
            """
        Ad failed to load!
        Error domain: ${didFailToReceiveAdWithError.domain}
        Error code: ${didFailToReceiveAdWithError.code}
        Error description: ${didFailToReceiveAdWithError.localizedDescription}
        """.trimIndent()
        )
    }

    @ExperimentalForeignApi
    override fun adLoaderDidFinishLoading(adLoader: GADAdLoader) {
        println("Ad loader finished loading.")
    }

    override fun adLoader(adLoader: GADAdLoader, didReceiveNativeAd: GADNativeAd) {
        println("Ad Loader: ${didReceiveNativeAd}")
        onAdLoaded(didReceiveNativeAd)
    }


}