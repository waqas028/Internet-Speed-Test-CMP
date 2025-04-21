package com.farimarwat.speedtest.utils

import java.net.URLDecoder
import java.net.URLEncoder

actual fun String.encodeUrl(): String {
    return URLEncoder.encode(this, "UTF-8")
}

actual fun String.decodeUrl(): String {
    return URLDecoder.decode(this, "UTF-8")
}