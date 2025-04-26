package com.farimarwat.speedtest.feature.ad

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.farimarwat.speedtest.R
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
actual fun SquareNativeAd(modifier: Modifier,
                          adUnitId: String,
                          adColors: AdColors?
) {
    val adController = rememberNativeAdController(adUnitId)

    LaunchedEffect(Unit) {
        adController.loadAd()
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val adView = LayoutInflater.from(ctx)
                .inflate(R.layout.native_ad_square,null) as NativeAdView

            adView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            adColors?.let{colors ->
                adView.apply {
                    findViewById<NativeAdView>(R.id.nativeAdContainer)
                        .setBackgroundColor(colors.adBackground.toArgb())
                    findViewById<TextView>(R.id.ad_headline)
                        .setTextColor(adColors.headlineText.toArgb())
                    findViewById<TextView>(R.id.ad_body)
                        .setTextColor(colors.bodyText.toArgb())
                    findViewById<TextView>(R.id.adBadge)
                        .setTextColor(colors.badgeText.toArgb())
                }
            }

            adController.showAd(adView) // Show the ad immediately
            adView
        },
        update = { view ->
            // Optional: If needed, re-bind ad here when recomposed
            adController.showAd(view)
        }
    )
}