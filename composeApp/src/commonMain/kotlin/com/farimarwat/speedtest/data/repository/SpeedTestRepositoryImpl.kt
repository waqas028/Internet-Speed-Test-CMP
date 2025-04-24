package com.farimarwat.speedtest.data.repository

import com.farimarwat.speedtest.data.local.SpeedTestDataSource
import com.farimarwat.speedtest.data.mappers.SpeedTestMapper.toDomain
import com.farimarwat.speedtest.data.mappers.SpeedTestMapper.toEntity
import com.farimarwat.speedtest.domain.model.SpeedTest
import com.farimarwat.speedtest.domain.repository.SpeedTestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class SpeedTestRepositoryImpl(
    private val dataSource: SpeedTestDataSource
):SpeedTestRepository {
    override suspend fun insertSpeedTestEntity(entity: SpeedTest) {
        dataSource.insert(entity.toEntity())
    }

    override  fun getAllSpeedTests(): Flow<List<SpeedTest>> {
        return dataSource.getAllSpeedTests()
            .map { list ->
                list.map { entity -> entity.toDomain() }
            }
    }
}