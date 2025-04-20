package com.farimarwat.speedtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farimarwat.speedtest.utils.LatencyResult
import com.farimarwat.speedtest.utils.NetworkLatencyMeasurer
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class TestViewModel(
    private val networkLatencyMeasurer: NetworkLatencyMeasurer
):ViewModel() {
    private var _latencyResult:MutableStateFlow<LatencyResult> =
        MutableStateFlow(LatencyResult(0,0,0.0f))
    val latencyResult = _latencyResult.asStateFlow()

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
        _latencyResult.value = networkLatencyMeasurer
            .measure("http://httpbin.org/get",5,2000)
    }
}