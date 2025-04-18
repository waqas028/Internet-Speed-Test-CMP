package com.farimarwat.speedtest.di

import com.farimarwat.speedtest.presentation.viewmodel.HomeViewModel
import com.farimarwat.speedtest.presentation.viewmodel.TestViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::TestViewModel)
}

expect val platFormModule:Module