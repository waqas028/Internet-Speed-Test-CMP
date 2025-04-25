package com.farimarwat.speedtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farimarwat.speedtest.domain.model.SpeedTest
import com.farimarwat.speedtest.domain.usecase.GetAllTestSpeedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryScreenViewModel(
    private val getAllTestSpeedUseCase: GetAllTestSpeedUseCase
):ViewModel() {

    private var _speedTests:MutableStateFlow<List<SpeedTest>> = MutableStateFlow(emptyList())
    val speedTests = _speedTests.asStateFlow()

    fun listSpeedTests() = viewModelScope.launch(Dispatchers.IO) {
        getAllTestSpeedUseCase()
            .collect{ list ->
                _speedTests.value = list
                println(list)
            }
    }
}