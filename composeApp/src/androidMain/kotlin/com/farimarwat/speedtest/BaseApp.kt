package com.farimarwat.speedtest

import android.app.Application
import com.farimarwat.speedtest.di.initKoin
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext

class BaseApp:Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BaseApp)
        }
        CoroutineScope(Dispatchers.IO).launch{
            MobileAds.initialize(this@BaseApp)
        }
    }
}