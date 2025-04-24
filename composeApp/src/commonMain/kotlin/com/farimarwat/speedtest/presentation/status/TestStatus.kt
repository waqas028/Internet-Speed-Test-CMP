package com.farimarwat.speedtest.presentation.status

data class TestStatus(
    var isTestRunning:Boolean = false,
    var isTestCompleted:Boolean = false,
    var speed:Double = 0.0,
)