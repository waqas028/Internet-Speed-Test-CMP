package com.farimarwat.speedtest.core

import com.farimarwat.speedtest.utils.roundToDecimals
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.DEFAULT_HTTP_BUFFER_SIZE
import io.ktor.http.content.OutgoingContent
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.withTimeout
import kotlin.math.pow
import kotlin.math.round
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource


class UploadSpeedTester(private val client: HttpClient) {

    suspend fun testUploadSpeed(
        uploadUrl: String,
        testDuration: Duration = 20.seconds,
        onProgress: (currentSpeedMbps: Double) -> Unit
    ): Double {
        var totalBytesSent = 0L
        val startTime = TimeSource.Monotonic.markNow()
        var elapsedTime = Duration.ZERO

        var lastBytesSent = 0L
        var lastTimestamp = startTime

        val payload = ByteArray(32 * 1024) { 1 }

        while (elapsedTime < testDuration) {
            client.post(uploadUrl) {
                setBody(payload)
            }
            totalBytesSent += payload.size
            elapsedTime = startTime.elapsedNow()

            // Instantaneous speed calculation
            val timeSinceLast = lastTimestamp.elapsedNow()
            if (timeSinceLast >= 100.milliseconds) {
                val bytesInInterval = totalBytesSent - lastBytesSent
                val speedMbps = (bytesInInterval * 8 / (timeSinceLast.inWholeMilliseconds / 1000.0)) / 1_000_000.0
                onProgress(speedMbps)
                lastBytesSent = totalBytesSent
                lastTimestamp = TimeSource.Monotonic.markNow()
            }
        }

        // Final average Mbps
        elapsedTime = startTime.elapsedNow()
        val finalSpeed = if (elapsedTime.inWholeMilliseconds > 0) {
            (totalBytesSent * 8 / (elapsedTime.inWholeMilliseconds / 1000.0)) / 1_000_000.0
        } else 0.0

        return finalSpeed
    }
}
