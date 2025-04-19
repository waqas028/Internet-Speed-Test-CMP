package com.farimarwat.speedtest.utils

import com.farimarwat.speedtest.domain.model.LocationCoordinates
import kotlin.math.*

object Utils{
    fun calculateDistance(from: LocationCoordinates, to: LocationCoordinates): Double {
        val earthRadius = 6371.0 // Earth's radius in kilometers

        val dLat = (to.latitude - from.latitude).toRadians()
        val dLon = (to.longitude - from.longitude).toRadians()

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(from.latitude.toRadians()) * cos(to.latitude.toRadians()) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }
}
fun Double.toRadians() = this * PI / 180
