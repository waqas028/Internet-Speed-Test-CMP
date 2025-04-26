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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.farimarwat.speedtest.domain.model.STServer
import com.farimarwat.speedtest.feature.ad.AdColors
import com.farimarwat.speedtest.feature.ad.SquareNativeAd
import com.farimarwat.speedtest.presentation.component.ErrorMessageWithRetry
import com.farimarwat.speedtest.presentation.component.NetworkMetricItem
import com.farimarwat.speedtest.presentation.component.SpeedItem
import com.farimarwat.speedtest.presentation.component.SpeedMeter
import com.farimarwat.speedtest.presentation.status.OverallTestStatus
import com.farimarwat.speedtest.presentation.viewmodel.HomeViewModel
import com.farimarwat.speedtest.presentation.viewmodel.TestViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import speedtest.composeapp.generated.resources.Res
import speedtest.composeapp.generated.resources.arrow_down_circle
import speedtest.composeapp.generated.resources.arrow_up_circle
import speedtest.composeapp.generated.resources.ic_jitter
import speedtest.composeapp.generated.resources.ic_ping

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    homeViewModel: HomeViewModel,
    testViewModel: TestViewModel,
    navController: NavHostController,
    modifier: Modifier
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        delay(500)
        val url = homeViewModel.selectedServer.value?.url
        url?.let {
            testViewModel.startTest(url)
        }
    }
    val currentSpeed by testViewModel.currentSpeed.collectAsStateWithLifecycle()
    val downloadTestStatus by testViewModel.downloadTestStatus.collectAsStateWithLifecycle()
    val uploadTestStatus by testViewModel.uploadTestStatus.collectAsStateWithLifecycle()

    val overallTestStatus by testViewModel.overallTestStatus.collectAsStateWithLifecycle()
    var showError by remember { mutableStateOf(false) }
    var testFinished by remember { mutableStateOf(false) }

    LaunchedEffect(overallTestStatus) {
        when (overallTestStatus) {
            is OverallTestStatus.Finished -> {
                testFinished = true

                val error = (overallTestStatus as OverallTestStatus.Finished).error
                if (error != null) {
                    showError = true
                } else {
                    if (homeViewModel.selectedProvider.value != null
                        && homeViewModel.selectedServer.value != null
                    ) {
                        testViewModel
                            .insertSpeedTest(
                                stProvider = homeViewModel.selectedProvider.value!!,
                                stServer = homeViewModel.selectedServer.value!!
                            )
                    }
                }
            }

            OverallTestStatus.Idle -> {

            }

            OverallTestStatus.Running -> {

            }
        }
    }


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column {
            TopAppBar(
                title = { Text("Test") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Back"
                        )
                    }
                }
            )
            //If error
            if (showError) {
                ErrorMessageWithRetry(
                    message = (overallTestStatus as OverallTestStatus.Finished).error?.message.toString(),
                    onRetry = {
                        scope.launch {
                            showError = false
                            val url = homeViewModel.selectedServer.value?.url
                            url?.let {
                                testViewModel.startTest(url)
                            }
                        }
                    }
                )
            }
            //else
            else {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            SpeedItem(
                                label = "Download",
                                icon = painterResource(Res.drawable.arrow_down_circle),
                                iconColor = MaterialTheme.colorScheme.secondary,
                                status = downloadTestStatus
                            )
                            SpeedItem(
                                label = "Upload",
                                icon = painterResource(Res.drawable.arrow_up_circle),
                                iconColor = MaterialTheme.colorScheme.tertiary,
                                status = uploadTestStatus
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val latencyResult by testViewModel.pingResult.collectAsStateWithLifecycle()

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
                        progress = currentSpeed.toFloat(),
                        needleColors = listOf(Color.Black, Color.White),
                        needleKnobColors = listOf(Color.Black, Color.Gray),
                        needleKnobSize = 20f,
                        progressColors = listOf(Color.Red, Color.Yellow),
                        labelColor = Color.White,
                        unitText = "Mbps"
                    )

                    SquareNativeAd(
                        modifier = Modifier.fillMaxSize(),
                        adUnitId = "ca-app-pub-3940256099942544/2247696110",
                        adColors = AdColors(
                            adBackground = MaterialTheme.colorScheme.background,
                            headlineText = MaterialTheme.colorScheme.primary,
                            bodyText = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }
        }

    }
}