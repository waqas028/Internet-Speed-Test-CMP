package com.farimarwat.speedtest.presentation.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.farimarwat.speedtest.presentation.status.TestStatus
import com.farimarwat.speedtest.utils.to2DecimalString


@Composable
fun SpeedItem(
    label: String,
    icon: Painter,
    iconColor: Color,
    status: TestStatus
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        )
    )
    val idleAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(500)
    )

    val currentAlpha = if (status.isTestRunning) pulseAlpha else idleAlpha

    Column(
        modifier = Modifier
            .padding(8.dp)
            .graphicsLayer { alpha = currentAlpha },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = icon,
                contentDescription = label,
                tint = iconColor.copy(alpha = currentAlpha)
            )
            Text(
                text = label,
                color = if (!status.isTestRunning) Color.White.copy(alpha = currentAlpha)
                else Color.Gray.copy(alpha = currentAlpha),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Mbps",
                color = Color.Gray.copy(alpha = currentAlpha),
                style = MaterialTheme.typography.titleSmall
            )
        }
        Text(
            text = if(!status.isTestCompleted) "_" else status.speed.toFloat().to2DecimalString(),
            color = Color.White.copy(alpha = currentAlpha),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}