package com.farimarwat.speedtest.core

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.DEFAULT_HTTP_BUFFER_SIZE
import io.ktor.utils.io.readAvailable
import kotlinx.coroutines.*
import kotlin.math.pow
import kotlin.math.round
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.utils.io.*
import kotlin.time.*
import kotlin.time.Duration.Companion.milliseconds

class DownloadSpeedTester(private val client: HttpClient) {

    suspend fun testDownloadSpeed(
        fileUrls: List<String>,
        testDuration: Duration = 20.seconds,
        onProgress: (currentSpeedMbps: Double, progress: Float) -> Unit
    ): Double {
        var totalBytes = 0L
        val startTime = TimeSource.Monotonic.markNow()
        var elapsedTime = Duration.ZERO
        var currentUrlIndex = 0

        var lastBytes = 0L
        var lastTimestamp = startTime

        while (elapsedTime < testDuration) {
            val url = fileUrls[currentUrlIndex % fileUrls.size]
            currentUrlIndex++

            try {
                client.prepareGet(url).execute { response ->
                    val channel = response.bodyAsChannel()
                    val buffer = ByteArray(DEFAULT_HTTP_BUFFER_SIZE)
                    var bytesRead: Int

                    while (
                        channel.readAvailable(buffer).also { bytesRead = it } >= 0 &&
                        elapsedTime < testDuration
                    ) {
                        totalBytes += bytesRead
                        elapsedTime = startTime.elapsedNow()

                        // Instantaneous speed calculation
                        val timeSinceLast = lastTimestamp.elapsedNow()
                        if (timeSinceLast >= 500.milliseconds) {
                            val bytesInInterval = totalBytes - lastBytes
                            val speedMbps = (bytesInInterval * 8 / (timeSinceLast.inWholeMilliseconds / 1000.0)) / 1_000_000.0
                            val progress = elapsedTime.inWholeMilliseconds.toFloat() / testDuration.inWholeMilliseconds.toFloat()
                            onProgress(speedMbps.roundToDecimals(2), progress.coerceIn(0f, 1f))

                            lastBytes = totalBytes
                            lastTimestamp = TimeSource.Monotonic.markNow()
                        }
                    }
                }
            } catch (e: Exception) {
                println("Error during download: $e")
                return 0.0
            }
        }

        // Final average Mbps
        elapsedTime = startTime.elapsedNow()
        return if (elapsedTime.inWholeMilliseconds > 0) {
            (totalBytes * 8 / (elapsedTime.inWholeMilliseconds / 1000.0)) / 1_000_000.0
        } else 0.0
    }
}


fun Double.roundToDecimals(decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    return round(this * factor) / factor
}