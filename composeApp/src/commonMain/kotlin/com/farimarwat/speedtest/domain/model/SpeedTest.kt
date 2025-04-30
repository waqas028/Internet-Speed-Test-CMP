package com.farimarwat.speedtest.domain.model

import com.speedtest.SpeedTestEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.descriptors.SerialDescriptor

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

