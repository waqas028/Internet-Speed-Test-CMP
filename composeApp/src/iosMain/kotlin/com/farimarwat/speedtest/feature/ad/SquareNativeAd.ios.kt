package com.farimarwat.speedtest.feature.ad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.Google_Mobile_Ads_SDK.GADMediaView
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAdView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.NSTextAlignmentCenter
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
import platform.UIKit.UILayoutConstraintAxisHorizontal
import platform.UIKit.UILayoutConstraintAxisVertical
import platform.UIKit.UILayoutPriorityDefaultHigh
import platform.UIKit.UIScreen
import platform.UIKit.UIStackView
import platform.UIKit.UIStackViewAlignmentFill
import platform.UIKit.UIStackViewAlignmentLeading
import platform.UIKit.UIStackViewAlignmentTop
import platform.UIKit.UIStackViewDistributionFill

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
                translatesAutoresizingMaskIntoConstraints = false
                val screenWidth = UIScreen.mainScreen.bounds.useContents { size.width }

                widthAnchor.constraintEqualToConstant(screenWidth).active = true
                heightAnchor.constraintEqualToConstant(300.0).active = true
                val mainStack = UIStackView().apply {
                    axis = UILayoutConstraintAxisVertical
                    spacing = 8.0
                    alignment = UIStackViewAlignmentFill
                    distribution = UIStackViewDistributionFill
                    translatesAutoresizingMaskIntoConstraints = false
                }

                // --- Top row: icon + headline/body ---
                val topRow = UIStackView().apply {
                    axis = UILayoutConstraintAxisHorizontal
                    spacing = 8.0
                    alignment = UIStackViewAlignmentTop
                    distribution = UIStackViewDistributionFill
                    translatesAutoresizingMaskIntoConstraints = false
                }

                val iconView = UIImageView().apply {
                    widthAnchor.constraintEqualToConstant(48.0).active = true
                    heightAnchor.constraintEqualToConstant(48.0).active = true
                    contentMode = UIViewContentMode.UIViewContentModeScaleAspectFit
                }

                val textStack = UIStackView().apply {
                    axis = UILayoutConstraintAxisVertical
                    spacing = 4.0
                    alignment = UIStackViewAlignmentLeading
                    distribution = UIStackViewDistributionFill
                }

                val headlineLabel = UILabel().apply {
                    font = UIFont.boldSystemFontOfSize(18.0)
                    textColor = adColors?.headlineText?.toUIColor() ?: UIColor.blackColor()
                    numberOfLines = 0
                }

                val bodyLabel = UILabel().apply {
                    font = UIFont.systemFontOfSize(14.0)
                    textColor = adColors?.bodyText?.toUIColor() ?: UIColor.darkGrayColor()
                    numberOfLines = 2
                }

                textStack.addArrangedSubview(headlineLabel)
                textStack.addArrangedSubview(bodyLabel)
                topRow.addArrangedSubview(iconView)
                topRow.addArrangedSubview(textStack)

                // --- Media container ---
                val mediaContainer = UIView().apply {
                    translatesAutoresizingMaskIntoConstraints = false
                    heightAnchor.constraintEqualToConstant(150.0).active = true
                }

                val mediaView = GADMediaView().apply {
                    contentMode = UIViewContentMode.UIViewContentModeScaleAspectFit
                    translatesAutoresizingMaskIntoConstraints = false
                }

                mediaContainer.addSubview(mediaView)
                NSLayoutConstraint.activateConstraints(listOf(
                    mediaView.leadingAnchor.constraintEqualToAnchor(mediaContainer.leadingAnchor),
                    mediaView.trailingAnchor.constraintEqualToAnchor(mediaContainer.trailingAnchor),
                    mediaView.topAnchor.constraintEqualToAnchor(mediaContainer.topAnchor),
                    mediaView.bottomAnchor.constraintEqualToAnchor(mediaContainer.bottomAnchor)
                ))

                // --- CTA button ---
                val ctaButton = UIButton.buttonWithType(UIButtonTypeSystem).apply {
                    setTitleColor(adColors?.buttonText?.toUIColor() ?: UIColor.whiteColor(), forState = UIControlStateNormal)
                    backgroundColor = adColors?.buttonBackground?.toUIColor() ?: UIColor.blackColor // Approx #FFC107
                    layer.cornerRadius = 6.0
                    titleLabel?.font = UIFont.boldSystemFontOfSize(16.0)
                    heightAnchor.constraintGreaterThanOrEqualToConstant(40.0).active = true
                }

                // --- Badge ---
                val adBadge = UILabel().apply {
                    text = "  ad  " // fake padding
                    font = UIFont.systemFontOfSize(12.0)
                    textAlignment = NSTextAlignmentCenter
                    backgroundColor = adColors?.badgeBackground?.toUIColor()
                    textColor = adColors?.badgeText?.toUIColor() ?: UIColor.whiteColor()
                    layer.cornerRadius = 4.0
                    clipsToBounds = true
                    setContentHuggingPriority(UILayoutPriorityDefaultHigh, forAxis = UILayoutConstraintAxisHorizontal)
                    translatesAutoresizingMaskIntoConstraints = false
                    heightAnchor.constraintEqualToConstant(24.0).active = true
                }


                // --- Arrange views ---
                mainStack.addArrangedSubview(topRow)
                mainStack.addArrangedSubview(mediaContainer)
                mainStack.addArrangedSubview(ctaButton)

                addSubview(mainStack)
                addSubview(adBadge)

                NSLayoutConstraint.activateConstraints(listOf(
                    mainStack.leadingAnchor.constraintEqualToAnchor(leadingAnchor, constant = 8.0),
                    mainStack.trailingAnchor.constraintEqualToAnchor(trailingAnchor, constant = -8.0),
                    mainStack.topAnchor.constraintEqualToAnchor(topAnchor, constant = 8.0),
                    mainStack.bottomAnchor.constraintEqualToAnchor(bottomAnchor, constant = -8.0),

                    adBadge.topAnchor.constraintEqualToAnchor(topAnchor, constant = 4.0),
                    adBadge.trailingAnchor.constraintEqualToAnchor(trailingAnchor, constant = -8.0)
                ))

                // Register views
                this.headlineView = headlineLabel
                this.bodyView = bodyLabel
                this.callToActionView = ctaButton
                this.iconView = iconView
                this.mediaView = mediaView
                backgroundColor = adColors?.adBackground?.toUIColor() ?: UIColor.whiteColor()
            }
        },
        modifier = modifier
            .background(adColors?.adBackground ?: MaterialTheme.colorScheme.background)
        .height(300.dp),
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