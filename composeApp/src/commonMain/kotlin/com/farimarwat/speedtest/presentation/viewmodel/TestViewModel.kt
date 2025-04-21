package com.farimarwat.speedtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farimarwat.speedtest.core.DownloadSpeedTester

import com.farimarwat.speedtest.core.PingResult
import com.farimarwat.speedtest.core.WebSocketPingMeasurer
import com.farimarwat.speedtest.domain.model.STServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class TestViewModel(
    private val webSocketPingMeasurer: WebSocketPingMeasurer,
    private val downloadSpeedTester: DownloadSpeedTester
):ViewModel() {
    var mUrl:String = ""
    private var _pingResult:MutableStateFlow<PingResult> =
        MutableStateFlow(PingResult(0,0,0.0f))
    val pingResult = _pingResult.asStateFlow()

    private var _isTesting:MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isTesting = _isTesting.asStateFlow()
    private var _currentSpeed:MutableStateFlow<Double> = MutableStateFlow(0.0)
    val currentSpeed = _currentSpeed.asStateFlow()

    var testDownloadJob:Job? = null
    var testUploadJob:Job? = null
    var testLatencyMeasureJob:Job? = null

    fun startTest(url:String) = viewModelScope.launch(Dispatchers.IO){
        mUrl = url
        println("Url: $mUrl")
        if (mUrl.isEmpty()) return@launch
        resetTest()

        _isTesting.value = true
        testLatencyMeasureJob?.cancel()
        testDownloadJob?.cancel()
        //NetworkMeasure
        supervisorScope {
            testLatencyMeasureJob = launch {
                testLatency()
            }
            testDownloadJob = launch {
                testDownload()
            }
        }
    }

    private suspend fun testLatency(){
        _pingResult.value = webSocketPingMeasurer
            .measurePing()
    }
    private suspend fun testDownload() {
        val url = mUrl
        val fileUrls = listOf(
            "${url}/random4000x4000.jpg",
            "${url}/random4000x4000.jpg",
            "${url}/random4000x4000.jpg",
            "${url}/random4000x4000.jpg",
            "${url}/random3000x3000.jpg",
            "${url}/random2000x2000.jpg",
            "${url}/random1000x1000.jpg"
        )

        val finalSpeed = downloadSpeedTester
            .testDownloadSpeed(
                fileUrls = fileUrls,
                onProgress = { currentSpeed, progress ->
                    _currentSpeed.value = currentSpeed
                    println("CurrentSpeed: ${currentSpeed}, Progress: $progress")
                }
            )
        println("FinalSpeed: $finalSpeed")

    }
    fun resetTest(){
        _pingResult.value = PingResult(0,0,0.0f)
        _isTesting.value = false
    }
}