package com.farimarwat.speedtest.feature.ad

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.farimarwat.speedtest.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

class NativeAdControllerAndroid(
    private var adUnitId: String,
    private var context: Context
) : NativeAdController {
    private var nativeAd by mutableStateOf<NativeAd?>(null)
    override fun loadAd() {
        val adLoader = AdLoader.Builder(context, adUnitId)
            .forNativeAd { ad ->
                nativeAd = ad
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e("AdMob", "Native ad failed: ${adError.message}")
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.e("AdMob", "Add Loaded")
                }
            })
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    override fun showAd(container: Any) {
        val adView = container as NativeAdView
        // Handle media content
        val mediaView = adView.findViewById<MediaView>(R.id.ad_media)
        val imageView = adView.findViewById<ImageView>(R.id.ad_image)
        nativeAd?.let { ad ->
            // Set basic ad views
            adView.headlineView = adView.findViewById(R.id.ad_headline)
            adView.bodyView = adView.findViewById(R.id.ad_body)
            adView.callToActionView = adView.findViewById(R.id.ad_actionbutton)
            adView.iconView = adView.findViewById(R.id.ad_icon)
            adView.mediaView = mediaView

            // Set ad badge
            adView.findViewById<TextView>(R.id.adBadge).visibility = View.VISIBLE

            // Bind text views
            (adView.headlineView as? TextView)?.text = ad.headline
            (adView.bodyView as? TextView)?.text = ad.body
            (adView.callToActionView as? Button)?.text = ad.callToAction ?: "Learn More"

            // Handle icon
            if (ad.icon == null) {
                adView.iconView?.visibility = View.GONE
            } else {
                (adView.iconView as? ImageView)?.setImageDrawable(ad.icon?.drawable)
                adView.iconView?.visibility = View.VISIBLE
            }
            // Hide both initially
            mediaView.visibility = View.GONE
            imageView.visibility = View.GONE
            when {
                ad.mediaContent?.hasVideoContent() == true -> {
                    mediaView.setMediaContent(ad.mediaContent)
                    mediaView.visibility = View.VISIBLE
                }
                ad.images.size > 0 -> {
                    ad.images[0].drawable?.let {
                        imageView.setImageDrawable(it)
                        imageView.visibility = View.VISIBLE
                    }
                }
            }

            // Register the native ad view
            adView.setNativeAd(ad)
        }
    }

    override fun dispose() {
        nativeAd?.destroy()
        nativeAd = null
    }
}

@Composable
actual fun rememberNativeAdController(adUnitId: String): NativeAdController {
    val context = LocalContext.current
    return remember {
        NativeAdControllerAndroid(
            context = context,
            adUnitId = adUnitId
        )
    }
}
