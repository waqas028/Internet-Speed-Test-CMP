package com.farimarwat.speedtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.farimarwat.speedtest.utils.PingResult
import com.farimarwat.speedtest.utils.WebSocketPingMeasurer
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class TestViewModel(
    private val webSocketPingMeasurer: WebSocketPingMeasurer
):ViewModel() {
    private var _pingResult:MutableStateFlow<PingResult> =
        MutableStateFlow(PingResult(0,0,0.0f))
    val pingResult = _pingResult.asStateFlow()

    var testDownloadJob:Job? = null
    var testUploadJob:Job? = null
    var testLatencyMeasureJob:Job? = null

    fun startTest() = viewModelScope.launch{
        //NetworkMeasure
        supervisorScope {
            testLatencyMeasureJob = launch {
                testLatency()
            }
        }
    }

    private suspend fun testLatency(){
        _pingResult.value = webSocketPingMeasurer
            .measurePing()
        println("Timings: ${pingResult.value.rawOutput}")
    }
}