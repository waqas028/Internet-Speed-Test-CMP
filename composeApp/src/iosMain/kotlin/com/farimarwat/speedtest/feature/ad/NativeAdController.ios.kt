package com.farimarwat.speedtest.feature.ad

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cocoapods.Google_Mobile_Ads_SDK.GADAdLoader
import cocoapods.Google_Mobile_Ads_SDK.GADAdLoaderAdTypeNative
import cocoapods.Google_Mobile_Ads_SDK.GADAdLoaderOptions
import cocoapods.Google_Mobile_Ads_SDK.GADMediaView
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAd
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAdView
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import platform.UIKit.UIButton
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIImageView
import platform.UIKit.UILabel
import platform.UIKit.UIView
import platform.UIKit.UIViewController


@OptIn(ExperimentalForeignApi::class)
class NativeAdControlleriOS(
    private val adUnitId: String,
    private val rootViewController: UIViewController?
) : NativeAdController {
    var nativeAd by mutableStateOf<GADNativeAd?>(null)
    private val adDelegate = MyAdLoaderDelegate{ad ->
        nativeAd = ad
    }
    private var adLoader: GADAdLoader? = null  // Retain the adLoader

    override fun loadAd() {
        // Ensure rootViewController is available
        if (rootViewController == null) {
            println("Error: rootViewController is null")
            return
        }

        val request = GADRequest.request()
        val adLoaderOptions = GADAdLoaderOptions()

        adLoader = GADAdLoader(
            adUnitID = adUnitId,
            rootViewController = rootViewController,
            adTypes = listOf(GADAdLoaderAdTypeNative),
            options = listOf(adLoaderOptions)
        ).apply {
            delegate = adDelegate
            loadRequest(request)
        }

        println("AdMob: Ad is loading")
    }

    override fun showAd(container: Any) {
        val adView = container as GADNativeAdView
        println("NativeAd: $nativeAd")
        nativeAd?.let { ad ->
            adView.nativeAd = ad
            (adView.headlineView as? UILabel)?.text = ad.headline
            (adView.bodyView as? UILabel)?.text = ad.body
            (adView.callToActionView as? UIButton)?.setTitle(ad.callToAction, forState = UIControlStateNormal)
            (adView.iconView as? UIImageView)?.image = ad.icon?.image
            (adView.mediaView as? GADMediaView)?.mediaContent = ad.mediaContent
        }
    }

    override fun dispose() {
        nativeAd = null
        adLoader = null  // Release the adLoader
    }
}
@Composable
actual fun rememberNativeAdController(adUnitId:String): NativeAdController = remember {
    val root = UIApplication.sharedApplication.keyWindow?.rootViewController
    NativeAdControlleriOS(
        adUnitId = adUnitId,
        rootViewController = root
    )
}.also { controller ->
    DisposableEffect(Unit) {
        onDispose {
            controller.dispose()
        }
    }
}
