package com.farimarwat.speedtest.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NetworkMetricItem(
    title: String,
    value: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.labelMedium.copy(
        fontWeight = FontWeight.Bold,
        color = Color.White
    ),
    valueStyle: TextStyle = MaterialTheme.typography.labelMedium.copy(
        fontWeight = FontWeight.Bold,
        color = Color.White
    ),
    unitStyle: TextStyle = MaterialTheme.typography.labelSmall.copy(
        color = Color.Gray
    ),
    spacing: Dp = 8.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Icon
        icon()

        // Title with unit
        Text(
            text = title,
            style = titleStyle,
            modifier = Modifier.alignByBaseline()
        )

        Text(
            text = "ms",
            style = unitStyle,
            modifier = Modifier.alignByBaseline()
        )

        // Value
        Text(
            text = value,
            style = valueStyle
        )
    }
}