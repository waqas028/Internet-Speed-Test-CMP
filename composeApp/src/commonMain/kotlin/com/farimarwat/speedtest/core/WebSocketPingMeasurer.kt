package com.farimarwat.speedtest.core

import com.farimarwat.speedtest.core.Urls.URL02
import io.ktor.client.HttpClient
import io.ktor.client.plugins.timeout
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.request
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import kotlin.math.abs
import kotlin.time.TimeSource

class WebSocketPingMeasurer(private val client: HttpClient) {
    private val timeSource = TimeSource.Monotonic

    @Throws(Exception::class)
    suspend fun measurePing(
        host: String = URL02,
        count: Int = 4,
        timeout: Long = 2000L,
        onError:(Exception)->Unit = {}
    ): PingResult {
        val timings = mutableListOf<Long>()
        var successCount = 0

        repeat(count) {
            val duration = measureSinglePing(host, timeout)
            timings.add(duration)
            successCount++
            if (it < count - 1) delay(300) // No delay after last attempt
        }

        return calculateResults(timings, count, successCount)
    }

    private suspend fun measureSinglePing(url: String, timeout: Long): Long {
        var elapsed: Long = -1

        val pingPayload = "ping-me"

        client.webSocket(urlString = url) {
            request {
                timeout {
                    socketTimeoutMillis = timeout
                    connectTimeoutMillis = timeout
                }
            }

            val start = timeSource.markNow()
            send(Frame.Text(pingPayload))

            val response = withTimeout(timeout) {
                incoming.receive() as? Frame.Text
            }

            if (response?.readText() == pingPayload) {
                elapsed = start.elapsedNow().inWholeMilliseconds
            } else {
                throw Exception("Ping mismatch or no response")
            }
        }

        return elapsed
    }



    private fun calculateResults(
        timings: List<Long>,
        totalAttempts: Int,
        successCount: Int
    ): PingResult {
        return when {
            timings.isEmpty() -> PingResult(-1, -1, 0f)
            timings.size == 1 -> PingResult(
                averagePingMs = timings.first().toInt(),
                jitterMs = 0,
                packetLossPercent = (successCount * 100f / totalAttempts),
                rawOutput = timings.toString()
            )
            else -> PingResult(
                averagePingMs = timings.average().toInt(),
                jitterMs = timings.zipWithNext { a, b -> abs(a - b) }
                    .average().toInt(),
                packetLossPercent = (successCount * 100f / totalAttempts),
                rawOutput = timings.toString()
            )
        }
    }
}
object Urls{
    val URL01 = "wss://echo.websocket.org"
    val URL02 = "wss://ws.postman-echo.com/raw"
}
data class PingResult(
    val averagePingMs: Int,
    val jitterMs: Int,
    val packetLossPercent: Float,
    val rawOutput: String = ""
)