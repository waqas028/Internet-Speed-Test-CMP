package com.farimarwat.speedtest.data.model

import com.speedtest.SpeedTestEntity
import kotlinx.datetime.Instant

// In commonMain/kotlin/your/package/model/SpeedTest.kt
data class SpeedTest(
    val id: Long,
    val providerName: String,
    val serverName: String,
    val providerLat: Double,
    val providerLon: Double,
    val serverLat: Double,
    val serverLon: Double,
    val performedAt: Instant
) {
    companion object {
        fun fromEntity(entity: SpeedTestEntity): SpeedTest {
            return SpeedTest(
                id = entity.id,
                providerName = entity.providerName,
                serverName = entity.serverName,
                providerLat = entity.providerLat.toDouble(),
                providerLon = entity.providerLon.toDouble(),
                serverLat = entity.serverLat.toDouble(),
                serverLon = entity.serverLon.toDouble(),
                performedAt = Instant.fromEpochMilliseconds(entity.performedAt)
            )
        }
    }
}
