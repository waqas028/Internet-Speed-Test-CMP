package com.farimarwat.speedtest.utils

import com.farimarwat.speedtest.domain.model.LocationCoordinates
import kotlin.math.*

object Utils {
    private const val EARTH_RADIUS_KM = 6371.0

    /**
     * Calculates distance between two coordinates and returns rounded kilometers
     * @param from Starting location coordinates
     * @param to Destination location coordinates
     * @return Distance in whole kilometers (rounded up)
     */
    fun calculateDistanceRounded(from: LocationCoordinates, to: LocationCoordinates): Int {
        val distanceKm = calculatePreciseDistance(from, to)
        return distanceKm.roundToInt() // Rounds to nearest integer
    }

    /**
     * Precise distance calculation (returns full Double with decimals)
     */
    fun calculatePreciseDistance(from: LocationCoordinates, to: LocationCoordinates): Double {
        val dLat = (to.latitude - from.latitude).toRadians()
        val dLon = (to.longitude - from.longitude).toRadians()

        val a = sin(dLat / 2).pow(2) +
                cos(from.latitude.toRadians()) * cos(to.latitude.toRadians()) *
                sin(dLon / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return EARTH_RADIUS_KM * c
    }

    private fun Double.toRadians() = this * PI / 180
}

fun Double.roundToDecimals(decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    return round(this * factor) / factor
}

