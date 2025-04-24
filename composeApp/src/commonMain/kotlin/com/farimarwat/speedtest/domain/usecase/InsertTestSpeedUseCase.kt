package com.farimarwat.speedtest.domain.usecase

import com.farimarwat.speedtest.domain.model.SpeedTest
import com.farimarwat.speedtest.domain.repository.SpeedTestRepository

class InsertTestSpeedUseCase(private val repository: SpeedTestRepository) {
    operator suspend fun invoke(speedTest: SpeedTest){
        repository.insertSpeedTestEntity(speedTest)
    }
}