package com.farimarwat.speedtest.domain.model

import com.speedtest.SpeedTestEntity
import kotlinx.datetime.Instant

data class SpeedTest(
    val id: Long = 0,
    val providerName: String,
    val serverName: String,
    val providerLat: Double,
    val providerLon: Double,
    val serverLat: Double,
    val serverLon: Double,
    val downSpeed:Double,
    val upSpeed:Double,
    val performedAt: Instant
)
