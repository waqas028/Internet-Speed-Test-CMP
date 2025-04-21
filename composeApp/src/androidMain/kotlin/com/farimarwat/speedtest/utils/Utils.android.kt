package com.farimarwat.speedtest.utils

import java.util.Locale

actual fun Float.to2DecimalString(): String {
    return String.format(Locale.US,"%.2f", this)
}