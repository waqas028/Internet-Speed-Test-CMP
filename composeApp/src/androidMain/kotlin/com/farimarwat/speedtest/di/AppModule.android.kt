package com.farimarwat.speedtest.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.speedtest.SpeedTestDatabase
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platFormModule = module {
    singleOf(::httpClient)
    single<SqlDriver>{
        AndroidSqliteDriver(
            SpeedTestDatabase.Schema,get(),"speedtest.db"
        )
    }
}
actual val httpClient: HttpClient =
    HttpClient(OkHttp){
        install(ContentNegotiation){
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
        install(WebSockets)
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
            socketTimeoutMillis = 30_000
        }
    }