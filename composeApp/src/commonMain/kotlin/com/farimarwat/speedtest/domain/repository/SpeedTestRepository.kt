package com.farimarwat.speedtest.domain.repository

import com.farimarwat.speedtest.domain.model.SpeedTest
import com.speedtest.SpeedTestEntity
import kotlinx.coroutines.flow.Flow

interface SpeedTestRepository {
    suspend fun insertSpeedTestEntity(entity: SpeedTest)
    fun getAllSpeedTests():Flow<List<SpeedTest>>
}