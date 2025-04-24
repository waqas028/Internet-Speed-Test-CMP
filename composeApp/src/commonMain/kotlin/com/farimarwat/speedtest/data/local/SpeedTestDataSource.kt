package com.farimarwat.speedtest.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.farimarwat.speedtest.data.mappers.SpeedTestMapper
import com.farimarwat.speedtest.data.mappers.SpeedTestMapper.toDomain
import com.farimarwat.speedtest.domain.model.SpeedTest
import com.farimarwat.speedtest.domain.model.STProvider
import com.farimarwat.speedtest.domain.model.STServer
import com.speedtest.SpeedTestDatabase
import com.speedtest.SpeedTestEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class SpeedTestDataSource(
    private val driver:SqlDriver
) {
    private val database = SpeedTestDatabase(driver)
    private val queries = database.speedTestEntityQueries

    suspend fun insert(entity:SpeedTestEntity){
       withContext(Dispatchers.IO){
           queries.transaction {
               queries.insertSpeedTest(
                   providerName = entity.providerName,
                   serverName = entity.serverName,
                   providerLat = entity.providerLat,
                   providerLon = entity.providerLon,
                   serverLat = entity.serverLat,
                   serverLon = entity.serverLon,
                   downSpeed = entity.downSpeed,
                   upSpeed = entity.upSpeed,
                   performedAt = entity.performedAt
               )
           }
       }
    }

    fun getAllSpeedTests():Flow<List<SpeedTestEntity>> =
        queries.getAllSpeedTests()
            .asFlow()
            .mapToList(
                context = Dispatchers.IO
            )
}