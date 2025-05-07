package com.farimarwat.speedtest.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import java.util.Locale
import androidx.core.net.toUri

actual fun Float.to2DecimalString(): String {
    return String.format(Locale.US,"%.2f", this)
}

private lateinit var appContext: Context

fun initShareUtils(context: Context) {
    appContext = context
}

actual fun shareText(message: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        putExtra(Intent.EXTRA_TEXT, "Hey! I found this great app. Check it out: https://play.google.com/store/apps/details?id=${appContext.packageName}")
        type = "text/plain"
    }
    val chooser = Intent.createChooser(sendIntent, "Share via")
    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    appContext.startActivity(chooser)
}

actual fun getAppVersion(): String {
    return try {
        val packageInfo = appContext.packageManager.getPackageInfo(appContext.packageName, 0)
        packageInfo.versionName ?: "Unknown"
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
    }
}

actual fun sendFeedbackEmail(toEmail: String, subject: String, body: String) {

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(Intent.EXTRA_EMAIL, arrayOf(toEmail))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    try {
        appContext.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        appContext.startActivity(Intent.createChooser(intent, "Send feedback via"))
    }
}