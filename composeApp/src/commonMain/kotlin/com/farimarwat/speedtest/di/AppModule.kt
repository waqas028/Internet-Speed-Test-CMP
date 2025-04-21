package com.farimarwat.speedtest.di

import com.farimarwat.speedtest.core.DownloadSpeedTester
import com.farimarwat.speedtest.core.UploadSpeedTester
import com.farimarwat.speedtest.data.remote.ServersApi
import com.farimarwat.speedtest.data.repository.ServersRepositoryImpl
import com.farimarwat.speedtest.domain.repository.ServersRepository
import com.farimarwat.speedtest.domain.usecase.FetchServersUseCase
import com.farimarwat.speedtest.presentation.viewmodel.HomeViewModel
import com.farimarwat.speedtest.presentation.viewmodel.TestViewModel
import com.farimarwat.speedtest.core.WebSocketPingMeasurer
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::ServersApi)
    singleOf(::ServersRepositoryImpl).bind<ServersRepository>()
    singleOf(::FetchServersUseCase)
    singleOf(::WebSocketPingMeasurer)
    singleOf(::DownloadSpeedTester)
    singleOf(::UploadSpeedTester)
    viewModelOf(::HomeViewModel)
    viewModelOf(::TestViewModel)
}

expect val httpClient:HttpClient
expect val platFormModule:Module