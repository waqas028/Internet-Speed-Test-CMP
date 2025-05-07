package com.farimarwat.speedtest.feature.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMarker
import cocoapods.GoogleMaps.GMSMutablePath
import cocoapods.GoogleMaps.GMSPolyline
import com.farimarwat.speedtest.domain.model.SpeedTest
import com.farimarwat.speedtest.feature.ad.toUIColor
import kotlinx.cinterop.ExperimentalForeignApi
import org.jetbrains.compose.resources.painterResource
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.UIKit.UIColor
import platform.UIKit.UIGraphicsImageRenderer
import platform.UIKit.UIImage
import platform.UIKit.systemBlueColor
import speedtest.composeapp.generated.resources.Res
import speedtest.composeapp.generated.resources.wifi


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun TestLocations(test: SpeedTest) {
    val map = remember { GMSMapView() }

    val providerPosition = CLLocationCoordinate2DMake(
        latitude = test.providerLat,
        longitude = test.providerLon
    )
    val serverPosition = CLLocationCoordinate2DMake(
        latitude = test.serverLat,
        longitude = test.serverLon
    )

    val cameraPosition = GMSCameraPosition.cameraWithLatitude(
        latitude = test.providerLat,
        longitude = test.providerLon,
        zoom = 10f
    )
    val cameraUpdate = GMSCameraUpdate.setCamera(cameraPosition)
    map.moveCamera(cameraUpdate)

    GMSMarker().apply {
        position = providerPosition
        title = "Provider: ${test.providerName}"
        snippet = test.providerName
        icon = UIImage.imageNamed("wifi")
        tracksInfoWindowChanges = true
        setMap(map)
    }
    GMSMarker().apply {
        position = serverPosition
        title = "Server: ${test.serverName}"
        icon = UIImage.imageNamed("server")
        setMap(map)
    }
    val path = GMSMutablePath().apply {
        addCoordinate(providerPosition)
        addCoordinate(serverPosition)
    }

    GMSPolyline().apply {
        this.path = path
        strokeColor = MaterialTheme.colorScheme.background.toUIColor()
        strokeWidth = 10.0
        setMap(map)
    }
    UIKitView(
        factory = { map },
        modifier = Modifier.fillMaxSize()
    )
}