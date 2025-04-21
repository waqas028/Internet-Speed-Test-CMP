package com.farimarwat.speedtest.utils

import platform.Foundation.NSCharacterSet
import platform.Foundation.NSMutableCharacterSet
import platform.Foundation.NSString
import platform.Foundation.URLQueryAllowedCharacterSet
import platform.Foundation.stringByAddingPercentEncodingWithAllowedCharacters
import platform.Foundation.stringByRemovingPercentEncoding

actual fun String.encodeUrl(): String {
    val allowed = NSMutableCharacterSet.alphanumericCharacterSet()
    allowed.addCharactersInString("-._~") // Same as Android's URLEncoder
    return (this as NSString).stringByAddingPercentEncodingWithAllowedCharacters(allowed) ?: this
}

actual fun String.decodeUrl(): String {
    return (this as NSString).stringByRemovingPercentEncoding ?: this
}