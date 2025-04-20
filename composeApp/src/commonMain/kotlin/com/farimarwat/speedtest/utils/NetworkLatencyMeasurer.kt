package com.farimarwat.speedtest.utils// commonMain
import io.ktor.client.*
import io.ktor.client.plugins.timeout
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeSource

class NetworkLatencyMeasurer(private val client: HttpClient) {
    private val timeSource = TimeSource.Monotonic
    suspend fun measure(
        url: String,
        attempts: Int = 5,
        timeout: Long = 2000L
    ): LatencyResult {
        val timings = mutableListOf<Long>()
        var successCount = 0
        
        repeat(attempts) {
            try {
                val mark = timeSource.markNow()
                client.head(url) {
                    timeout {
                        requestTimeoutMillis = timeout
                        connectTimeoutMillis = timeout
                    }
                }
                val elapsed = mark.elapsedNow()
                timings.add(elapsed.inWholeMilliseconds)
                successCount++
            } catch (e: Exception) {
                // Failed attempt
            }
            delay(500.milliseconds)
        }
        
        return calculateResult(timings, attempts, successCount)
    }
    
    private fun calculateResult(
        timings: List<Long>,
        totalAttempts: Int,
        successCount: Int
    ): LatencyResult {
        return if (timings.isNotEmpty()) {
            println("Timings: $timings")
            val average = timings.average().toInt()
            val jitter = if (timings.size > 1) {
                timings.zipWithNext { a, b -> abs(a - b) }.average().toInt()
            } else 0
            
            LatencyResult(
                averagePingMs = average,
                jitterMs = jitter,
                successRate = successCount * 100f / totalAttempts,
                rawTimings = timings
            )
        } else {
            LatencyResult(-1, -1, 0f)
        }
    }
}

data class LatencyResult(
    val averagePingMs: Int,
    val jitterMs: Int,
    val successRate: Float,
    val rawTimings: List<Long> = emptyList()
)