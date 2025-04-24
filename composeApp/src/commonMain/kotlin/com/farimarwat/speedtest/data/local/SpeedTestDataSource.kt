package com.farimarwat.speedtest.data.local

import app.cash.sqldelight.db.SqlDriver
import com.farimarwat.speedtest.data.model.SpeedTest
import com.farimarwat.speedtest.domain.model.STProvider
import com.farimarwat.speedtest.domain.model.STServer
import com.speedtest.SpeedTestDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class SpeedTestDataSource(
    private val driver:SqlDriver
) {

    private val database = SpeedTestDatabase(driver)
    private val queries = database.speedTestEntityQueries

    suspend fun insert(stProvider: STProvider, stServer: STServer, time:Instant = Clock.System.now()){
       withContext(Dispatchers.IO){
           queries.transaction {
               queries.insertSpeedTest(
                   providerName = stProvider.isp.toString(),
                   serverName = stServer.name.toString(),
                   providerLat = stProvider.lat.toString(),
                   providerLon = stProvider.lon.toString(),
                   serverLat = stServer.lat.toString(),
                   serverLon = stServer.lon.toString(),
                   performedAt = time.toEpochMilliseconds()
               )
           }
       }
    }

    fun getAllSpeedTests():Flow<List<SpeedTest>> = flow {
        val items = queries.getAllSpeedTests()
            .executeAsList()
            .map { item ->
                SpeedTest.fromEntity(item)
            }
        emit(items)
    }

}