package com.farimarwat.speedtest.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.farimarwat.speedtest.presentation.component.NetworkMetricItem
import com.farimarwat.speedtest.presentation.component.SpeedItem
import com.farimarwat.speedtest.presentation.component.SpeedMeter
import com.farimarwat.speedtest.presentation.viewmodel.TestViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import speedtest.composeapp.generated.resources.Res
import speedtest.composeapp.generated.resources.arrow_down_circle
import speedtest.composeapp.generated.resources.arrow_up_circle
import speedtest.composeapp.generated.resources.ic_jitter
import speedtest.composeapp.generated.resources.ic_ping

@Composable
fun TestScreen(
    viewModel: TestViewModel,
    navController: NavHostController
    ){
    LaunchedEffect(Unit){
        delay(500)
        viewModel.startTest()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    SpeedItem(
                        label = "Download",
                        speed = 50f,
                        isTestInProgress = true,
                        isCompleted = false,
                        icon = painterResource(Res.drawable.arrow_down_circle),
                        iconColor = MaterialTheme.colorScheme.secondary
                    )
                    SpeedItem(
                        label = "Upload",
                        speed = 90f,
                        isTestInProgress = false,
                        isCompleted = false,
                        icon = painterResource(Res.drawable.arrow_up_circle),
                        iconColor = MaterialTheme.colorScheme.tertiary
                    )
                }

                Row (
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    val latencyResult by viewModel.pingResult.collectAsStateWithLifecycle()

                    NetworkMetricItem(
                        modifier = Modifier
                            .weight(0.5f),
                        title = "Ping",
                        value = "${latencyResult.averagePingMs}",
                        icon = {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp),
                                painter = painterResource(Res.drawable.ic_ping),
                                contentDescription = "Jitter",
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    )
                    NetworkMetricItem(
                        modifier = Modifier
                            .weight(0.5f),
                        title = "Jitter",
                        value = "${latencyResult.jitterMs}",
                        icon = {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp),
                                painter = painterResource(Res.drawable.ic_jitter),
                                contentDescription = "Jitter",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    )
                }
            }

            SpeedMeter(
                modifier = Modifier.size(300.dp),
                backgroundColor = MaterialTheme.colorScheme.background,
                progressWidth = 50f,
                progress = 20f ,
                needleColors = listOf(Color.Black,Color.White),
                needleKnobColors = listOf(Color.Black,Color.Gray),
                needleKnobSize = 20f,
                progressColors = listOf(Color.Red, Color.Yellow),
                labelColor = Color.White,
                unitText = "MB"
            )
        }
    }
}