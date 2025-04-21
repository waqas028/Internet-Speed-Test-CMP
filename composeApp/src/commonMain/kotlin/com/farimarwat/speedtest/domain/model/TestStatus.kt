package com.farimarwat.speedtest.domain.model

data class TestStatus(
    var isTestRunning:Boolean = false,
    var isTestCompleted:Boolean = false,
    var speed:Double = 0.0,
)