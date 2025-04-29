package com.farimarwat.speedtest.feature.ad

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.Google_Mobile_Ads_SDK.GADMediaView
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAdView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIView
import platform.UIKit.UIViewContentMode
import platform.UIKit.UILabel
import platform.UIKit.UIButton
import platform.UIKit.UIButtonType
import platform.UIKit.UIButtonTypeSystem
import platform.UIKit.UIColor
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIFont
import platform.UIKit.UIImageView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun SquareNativeAd(
    modifier: Modifier,
    nativeAdController: NativeAdController,
    adColors: AdColors?
) {
    UIKitView(
        factory = {
            GADNativeAdView().apply {
                // Basic setup
                translatesAutoresizingMaskIntoConstraints = false
                setFrame(CGRectMake(0.0, 0.0, 300.0, 250.0)) // Standard square ad size

                // Create subviews programmatically
                val mediaView = GADMediaView().apply {
                    contentMode = UIViewContentMode.UIViewContentModeScaleAspectFit
                }

                val headlineLabel = UILabel().apply {
                    font = UIFont.boldSystemFontOfSize(18.0)
                    textColor = adColors?.headlineText?.toUIColor() ?: UIColor.blackColor()
                }

                val bodyLabel = UILabel().apply {
                    font = UIFont.systemFontOfSize(14.0)
                    textColor = adColors?.bodyText?.toUIColor() ?: UIColor.darkGrayColor()
                    numberOfLines = 2
                }

                val iconView = UIImageView().apply {
                    contentMode = UIViewContentMode.UIViewContentModeScaleAspectFit
                }

                val ctaButton = UIButton.buttonWithType(UIButtonTypeSystem).apply {
                    setTitleColor(
                        adColors?.buttonText?.toUIColor() ?: UIColor.whiteColor(),
                        forState = UIControlStateNormal
                    )
                    backgroundColor = adColors?.buttonBackground?.toUIColor() ?: UIColor.blueColor()
                }

                // Add to view hierarchy
                addSubview(mediaView)
                addSubview(headlineLabel)
                addSubview(bodyLabel)
                addSubview(iconView)
                addSubview(ctaButton)

                // Set constraints (layout)
                NSLayoutConstraint.activateConstraints(listOf(
                    // Media view constraints
                    mediaView.topAnchor.constraintEqualToAnchor(topAnchor, constant = 8.0),
                    mediaView.leadingAnchor.constraintEqualToAnchor(leadingAnchor, constant = 8.0),
                    mediaView.trailingAnchor.constraintEqualToAnchor(trailingAnchor, constant = -8.0),
                    mediaView.heightAnchor.constraintEqualToConstant(150.0),

                    // Headline constraints
                    headlineLabel.topAnchor.constraintEqualToAnchor(mediaView.bottomAnchor, constant = 8.0),
                    headlineLabel.leadingAnchor.constraintEqualToAnchor(leadingAnchor, constant = 8.0),
                    headlineLabel.trailingAnchor.constraintEqualToAnchor(trailingAnchor, constant = -8.0),

                    // Body constraints
                    bodyLabel.topAnchor.constraintEqualToAnchor(headlineLabel.bottomAnchor, constant = 4.0),
                    bodyLabel.leadingAnchor.constraintEqualToAnchor(leadingAnchor, constant = 8.0),
                    bodyLabel.trailingAnchor.constraintEqualToAnchor(trailingAnchor, constant = -8.0),

                    // CTA button constraints
                    ctaButton.topAnchor.constraintEqualToAnchor(bodyLabel.bottomAnchor, constant = 8.0),
                    ctaButton.centerXAnchor.constraintEqualToAnchor(centerXAnchor),
                    ctaButton.bottomAnchor.constraintEqualToAnchor(bottomAnchor, constant = -8.0),
                    ctaButton.widthAnchor.constraintEqualToConstant(200.0)
                ))

                // Register views
                this.headlineView = headlineLabel
                this.bodyView = bodyLabel
                this.callToActionView = ctaButton
                this.iconView = iconView
                this.mediaView = mediaView

                // Apply colors
                backgroundColor = adColors?.adBackground?.toUIColor() ?: UIColor.whiteColor()
            }
        },
        modifier = modifier,
        update = { view ->
            nativeAdController.showAd(view)
        },
        properties = UIKitInteropProperties(
            isInteractive = true,
            isNativeAccessibilityEnabled = true
        )
    )
}

fun Color.toUIColor(): UIColor {
    return UIColor(
        red = this.red.toDouble(),
        green = this.green.toDouble(),
        blue = this.blue.toDouble(),
        alpha = this.alpha.toDouble()
    )
}