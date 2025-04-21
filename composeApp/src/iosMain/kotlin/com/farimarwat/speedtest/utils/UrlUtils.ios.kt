package com.farimarwat.speedtest.utils

import platform.Foundation.NSCharacterSet
import platform.Foundation.NSString
import platform.Foundation.URLQueryAllowedCharacterSet
import platform.Foundation.stringByAddingPercentEncodingWithAllowedCharacters
import platform.Foundation.stringByRemovingPercentEncoding

actual fun String.encodeUrl(): String {
    return (this as NSString).stringByAddingPercentEncodingWithAllowedCharacters(
        NSCharacterSet.URLQueryAllowedCharacterSet
    ) ?: this
}

actual fun String.decodeUrl(): String {
    return (this as NSString).stringByRemovingPercentEncoding ?: this
}