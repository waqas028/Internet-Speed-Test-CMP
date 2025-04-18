package com.farimarwat.speedtest

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform