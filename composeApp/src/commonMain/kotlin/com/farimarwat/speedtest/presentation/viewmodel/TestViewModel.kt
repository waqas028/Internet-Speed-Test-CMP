package com.farimarwat.speedtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TestViewModel:ViewModel() {
    private var _ping: MutableStateFlow<Int> = MutableStateFlow(0)
    val ping = _ping.asStateFlow()
    private var _jitter: MutableStateFlow<Int> = MutableStateFlow(0)
    val jitter = _jitter.asStateFlow()
}