package com.farimarwat.speedtest.feature.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.farimarwat.speedtest.domain.model.SpeedTest
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.core.graphics.createBitmap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import speedtest.composeapp.generated.resources.Res
import speedtest.composeapp.generated.resources.server
import speedtest.composeapp.generated.resources.wifi


@Composable
actual fun TestLocations(test: SpeedTest) {
    val context = LocalContext.current
    val providerPosition = LatLng(test.providerLat, test.providerLon)
    val testServerPosition = LatLng(test.serverLat, test.serverLon)
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(providerPosition, 10f)
    }
    GoogleMap(
        cameraPositionState = cameraPosition,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CustomMarker(
            providerPosition,
            test.providerName,
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.wifi),
                    contentDescription = test.providerName,
                    modifier = Modifier.size(24.dp)
                )
            }
        )
        CustomMarker(
            testServerPosition,
            test.serverName,
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.server),
                    contentDescription = test.serverName,
                    modifier = Modifier.size(24.dp)
                )
            }
        )
        Polyline(
            points = listOf(providerPosition, testServerPosition),
            color = MaterialTheme.colorScheme.background,
            width = 10f,
            jointType = JointType.ROUND,
            startCap = RoundCap(),
            endCap = RoundCap()
        )
    }

}

@Composable
fun CustomMarker(
    position: LatLng,
    name: String,
    icon: @Composable () -> Unit = {}
) {
    MarkerComposable(
        state = rememberMarkerState(position = position),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .shadow(
                        8.dp,
                        shape = CircleShape,
                        spotColor = MaterialTheme.colorScheme.primary
                    )
                    .background(MaterialTheme.colorScheme.surface, CircleShape)
                    .padding(8.dp)
            ) {
                icon()
            }
            Text(
                text = name,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}