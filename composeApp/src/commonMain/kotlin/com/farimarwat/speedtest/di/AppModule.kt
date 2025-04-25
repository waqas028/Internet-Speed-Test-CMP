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
import com.farimarwat.speedtest.data.local.SpeedTestDataSource
import com.farimarwat.speedtest.data.repository.SpeedTestRepositoryImpl
import com.farimarwat.speedtest.domain.repository.SpeedTestRepository
import com.farimarwat.speedtest.domain.usecase.GetAllTestSpeedUseCase
import com.farimarwat.speedtest.domain.usecase.InsertTestSpeedUseCase
import com.farimarwat.speedtest.presentation.screen.SettingsScreen
import com.farimarwat.speedtest.presentation.viewmodel.HistoryScreenViewModel
import com.farimarwat.speedtest.presentation.viewmodel.SettingsScreenViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::ServersApi)
    singleOf(::ServersRepositoryImpl).bind<ServersRepository>()
    singleOf(::SpeedTestRepositoryImpl).bind<SpeedTestRepository>()
    singleOf(::WebSocketPingMeasurer)
    singleOf(::DownloadSpeedTester)
    singleOf(::UploadSpeedTester)
    singleOf(::SpeedTestDataSource)

    //usecases
    singleOf(::FetchServersUseCase)
    singleOf(::GetAllTestSpeedUseCase)
    singleOf(::InsertTestSpeedUseCase)
    singleOf(::GetAllTestSpeedUseCase)

    //viewmodels
    viewModelOf(::HomeViewModel)
    viewModelOf(::TestViewModel)
    viewModelOf(::HistoryScreenViewModel)
    viewModelOf(::SettingsScreenViewModel)
}

expect val httpClient:HttpClient
expect val platFormModule:Module