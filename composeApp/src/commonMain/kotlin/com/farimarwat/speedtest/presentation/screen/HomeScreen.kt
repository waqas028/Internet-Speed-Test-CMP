package com.farimarwat.speedtest.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.farimarwat.speedtest.data.remote.ApiStatus
import com.farimarwat.speedtest.presentation.component.CurrentServerStatus
import com.farimarwat.speedtest.presentation.component.GoButton
import com.farimarwat.speedtest.presentation.component.Loading
import com.farimarwat.speedtest.presentation.component.NetworkErrorMessageBox
import com.farimarwat.speedtest.presentation.component.ServerItem
import com.farimarwat.speedtest.presentation.component.ServerList
import com.farimarwat.speedtest.presentation.navigation.Screen
import com.farimarwat.speedtest.presentation.viewmodel.HomeViewModel
import com.farimarwat.speedtest.presentation.viewmodel.TestViewModel
import com.farimarwat.speedtest.utils.encodeUrl
import io.ktor.http.encodeURLParameter
import io.ktor.http.encodeURLPath
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        viewModel.fetchServers()
    }

    var showServers by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        val fetchServersStatus by viewModel.fetchServerStatus.collectAsStateWithLifecycle()
        val provider by viewModel.selectedProvider.collectAsStateWithLifecycle()
        val server by viewModel.selectedServer.collectAsStateWithLifecycle()
        val serversList by viewModel.servers.collectAsStateWithLifecycle()
        when (fetchServersStatus) {
            ApiStatus.Empty -> {
                Loading()
            }

            is ApiStatus.Error -> {
                NetworkErrorMessageBox(
                    message = (fetchServersStatus as ApiStatus.Error).message,
                    modifier = Modifier,
                    onRetry = {
                        viewModel.fetchServers()
                    },
                )
            }

            ApiStatus.Loading -> {
                Loading()
            }

            is ApiStatus.Success<*> -> {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    var showServersDetails by remember { mutableStateOf(false) }
                    var showGoButton by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit){
                        delay(200)
                        showGoButton = true
                        delay(1000)
                        showServersDetails = true
                    }
                    AnimatedVisibility(
                        visible = showGoButton,
                        enter =  scaleIn(
                            animationSpec = tween(
                                durationMillis = 1000,
                            )
                        )
                    ){
                        GoButton("Go") {
                            navController.navigate(Screen.Test.route)
                        }

                    }
                    AnimatedVisibility(
                        visible = showServersDetails,
                        enter = fadeIn() + slideInHorizontally(
                            animationSpec = tween(1000)
                        )
                    ){
                        CurrentServerStatus(
                            provider = provider,
                            server = server,
                            onChangeServerClick = {
                                showServers = true
                            }
                        )
                    }
                }
            }
        }

        //servers list
        if(showServers){
            ServerList(
                list = serversList,
                onDismiss = {
                    showServers = false
                },
                onItemSelected = {
                    viewModel.changeSelectedServer(it)
                    showServers = false
                }
            )
        }
    }
}