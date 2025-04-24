package com.farimarwat.speedtest.presentation.status

sealed class OverallTestStatus {
    object Idle : OverallTestStatus()
    object Running : OverallTestStatus()
    data class Finished(val error:Exception? = null) : OverallTestStatus()
}