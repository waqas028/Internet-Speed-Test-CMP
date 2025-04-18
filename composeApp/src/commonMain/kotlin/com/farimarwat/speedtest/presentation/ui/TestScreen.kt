package com.farimarwat.speedtest.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.farimarwat.speedtest.presentation.component.SpeedMeter
import com.farimarwat.speedtest.presentation.viewmodel.TestViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TestScreen(
    viewModel: TestViewModel,
    navController: NavHostController
    ){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        SpeedMeter(
            modifier = Modifier.size(300.dp),
            backgroundColor = MaterialTheme.colorScheme.background,
            progressWidth = 50f,
            progress = 50f ,
            needleColors = listOf(Color.Black,Color.White),
            needleKnobColors = listOf(Color.Black,Color.Gray),
            needleKnobSize = 20f,
            progressColors = listOf(Color.Red, Color.Yellow),
            labelColor = Color.White,
            unitText = "MB"
        )
    }
}