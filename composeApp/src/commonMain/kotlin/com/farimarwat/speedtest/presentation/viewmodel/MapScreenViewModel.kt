package com.farimarwat.speedtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.farimarwat.speedtest.domain.model.SpeedTest
import com.farimarwat.speedtest.domain.repository.SpeedTestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapScreenViewModel:ViewModel() {
    private var _speedTest:MutableStateFlow<SpeedTest?> = MutableStateFlow(null)
    val speedTest = _speedTest.asStateFlow()
    fun setSpeedTest(test:SpeedTest){
        _speedTest.value = test
        println(test)
    }
}