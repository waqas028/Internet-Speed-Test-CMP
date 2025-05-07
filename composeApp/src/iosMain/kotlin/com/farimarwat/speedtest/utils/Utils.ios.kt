package com.farimarwat.speedtest.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import platform.Foundation.NSBundle
import platform.Foundation.NSURL

actual fun Float.to2DecimalString(): String {
    val formatter = NSNumberFormatter()
    formatter.minimumFractionDigits = 2u
    formatter.maximumFractionDigits = 2u
    formatter.numberStyle = NSNumberFormatterDecimalStyle

    return formatter.stringFromNumber(NSNumber(this)) ?: this.toString()
}

actual fun shareText(message: String) {
    val rootViewController: UIViewController? = UIApplication.sharedApplication.keyWindow?.rootViewController
    if (rootViewController == null) {
        println("No root view controller found to present share sheet")
        return
    }
    val appStoreLink = "https://apps.apple.com/app/your-app-id"
    val fullMessage = "$message\nHey! I found this great app. Check it out: $appStoreLink"

    val activityItems = listOf(fullMessage)
    val activityViewController = UIActivityViewController(activityItems = activityItems, applicationActivities = null)

    rootViewController.presentViewController(
        viewControllerToPresent = activityViewController,
        animated = true,
        completion = null
    )
}

actual fun getAppVersion(): String {
    val version = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String
    return version ?: "Unknown"
}

actual fun sendFeedbackEmail(toEmail: String, subject: String, body: String) {
    val encodedSubject = subject
        .replace(" ", "%20")
        .replace("&", "%26")
    val encodedBody = body
        .replace(" ", "%20")
        .replace("\n", "%0A")
        .replace("&", "%26")

    val mailtoUrlString = "mailto:$toEmail?subject=$encodedSubject&body=$encodedBody"
    val mailtoUrl = NSURL.URLWithString(mailtoUrlString)

    if (mailtoUrl != null && UIApplication.sharedApplication.canOpenURL(mailtoUrl)) {
        UIApplication.sharedApplication.openURL(mailtoUrl)
    } else {
        println("Failed to open email client with URL: $mailtoUrlString")
    }
}