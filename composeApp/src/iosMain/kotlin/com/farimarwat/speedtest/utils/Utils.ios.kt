package com.farimarwat.speedtest.utils

import platform.Foundation.NSData
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle

actual fun Float.to2DecimalString(): String {
    val formatter = NSNumberFormatter()
    formatter.minimumFractionDigits = 2u
    formatter.maximumFractionDigits = 2u
    formatter.numberStyle = NSNumberFormatterDecimalStyle

    return formatter.stringFromNumber(NSNumber(this)) ?: this.toString()
}