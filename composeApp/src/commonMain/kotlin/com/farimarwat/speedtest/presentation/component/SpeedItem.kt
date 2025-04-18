package com.farimarwat.speedtest.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun SpeedItem(
    label:String,
    isCompleted:Boolean,
    speed:Float,
    icon: Painter,
    iconColor: Color
){
    Column (
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = icon,
                contentDescription = label,
                tint = iconColor
            )
            Text(
                text = label,
                color = if(isCompleted) Color.White else Color.Gray,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = "Mbps",
                color = Color.Gray,
                style = MaterialTheme.typography.labelMedium
            )
        }
        Text(
            text = "$speed",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
    }
}