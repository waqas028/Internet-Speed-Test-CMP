package com.farimarwat.speedtest.domain.usecase

import com.farimarwat.speedtest.domain.model.SpeedTest
import com.farimarwat.speedtest.domain.repository.SpeedTestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetAllTestSpeedUseCase(private val repository: SpeedTestRepository) {
     operator fun invoke():Flow<List<SpeedTest>> =
        repository.getAllSpeedTests()
}