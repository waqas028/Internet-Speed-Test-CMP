package com.farimarwat.speedtest.domain.usecase

import com.farimarwat.speedtest.domain.model.STProvider
import com.farimarwat.speedtest.domain.model.STServer
import com.farimarwat.speedtest.domain.model.SpeedTest
import com.farimarwat.speedtest.domain.repository.SpeedTestRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class InsertTestSpeedUseCase(private val repository: SpeedTestRepository) {
    operator suspend fun invoke(stProvider: STProvider,
                                stServer: STServer,
                                downSpeed:Double,
                                upSpeed:Double){

        repository.insertSpeedTestEntity(
            SpeedTest(
                providerName = stProvider.providerName ?: "Unknown",
                serverName = stServer.sponsor ?: "Unknown",
                providerLat = stProvider.lat?.toDouble() ?: 0.0,
                providerLon = stProvider.lon?.toDouble() ?: 0.0,
                serverLat = stServer.lat?.toDouble() ?: 0.0,
                serverLon = stServer.lon?.toDouble() ?: 0.0,
                downSpeed = downSpeed,
                upSpeed = upSpeed,
                performedAt = Clock.System.now()
            )
        )
    }
}