package com.farimarwat.speedtest.data.mappers

import com.farimarwat.speedtest.domain.model.SpeedTest
import com.speedtest.SpeedTestEntity
import kotlinx.datetime.Instant

object SpeedTestMapper {
    fun SpeedTest.toEntity(): SpeedTestEntity {
        return SpeedTestEntity(
            id = this.id,
            providerName = this.providerName,
            serverName = this.serverName,
            providerLat = this.providerLat.toString(),
            providerLon = this.providerLon.toString(),
            serverLat = this.serverLat.toString(),
            serverLon = this.serverLon.toString(),
            downSpeed = this.downSpeed.toString(),
            upSpeed = this.upSpeed.toString(),
            performedAt = this.performedAt.toEpochMilliseconds()
        )
    }

    fun SpeedTestEntity.toDomain(): SpeedTest {
        return SpeedTest(
            id = this.id,
            providerName = this.providerName,
            serverName = this.serverName,
            providerLat = this.providerLat.toDouble(),
            providerLon = this.providerLon.toDouble(),
            serverLat = this.serverLat.toDouble(),
            serverLon = this.serverLon.toDouble(),
            downSpeed = this.downSpeed.toDouble(),
            upSpeed = this.upSpeed.toDouble(),
            performedAt = Instant.fromEpochMilliseconds(this.performedAt)
        )
    }
}