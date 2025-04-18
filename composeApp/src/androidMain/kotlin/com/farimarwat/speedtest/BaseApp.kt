package com.farimarwat.speedtest

import android.app.Application
import com.farimarwat.speedtest.di.initKoin
import org.koin.android.ext.koin.androidContext

class BaseApp:Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BaseApp)
        }
    }
}