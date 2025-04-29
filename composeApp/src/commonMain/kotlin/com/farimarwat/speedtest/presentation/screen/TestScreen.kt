package com.farimarwat.speedtest.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.farimarwat.speedtest.feature.ad.rememberNativeAdController
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
    var showSpeedMeter by remember { mutableStateOf(true) }
    var showAd by remember { mutableStateOf(false) }

    val adController = rememberNativeAdController("ca-app-pub-3940256099942544/2247696110")
    LaunchedEffect(Unit) {
        adController.loadAd()
    }

    LaunchedEffect(overallTestStatus) {
        scope.launch {
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
                            showAd = true
                            delay(500)
                            showSpeedMeter = false
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
                    showAd = false
                    delay(500)
                    showSpeedMeter = true
                }
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
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
                        .fillMaxSize()
                        .animateContentSize(),
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

                    AnimatedVisibility(
                        visible = showSpeedMeter
                    ) {
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
                    }

                    AnimatedVisibility(
                        visible = showAd
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .padding(horizontal = 4.dp)
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .fillMaxWidth(),
                                onClick = {
                                    scope.launch {
                                        val url = homeViewModel.selectedServer.value?.url
                                        url?.let {
                                            testViewModel.startTest(url)
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Text(
                                    text = "ReTest"
                                )
                            }

                            SquareNativeAd(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                nativeAdController = adController,
                                adColors = AdColors(
                                    adBackground = MaterialTheme.colorScheme.background,
                                    headlineText = MaterialTheme.colorScheme.primary,
                                    bodyText = MaterialTheme.colorScheme.secondary,
                                    badgeText = MaterialTheme.colorScheme.onPrimary,
                                    badgeBackground = MaterialTheme.colorScheme.primary,
                                    buttonBackground = MaterialTheme.colorScheme.secondary,
                                    buttonText = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )
                        }
                    }
                }
            }
        }

    }
}