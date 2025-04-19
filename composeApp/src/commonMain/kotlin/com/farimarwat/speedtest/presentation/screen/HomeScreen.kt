package com.farimarwat.speedtest.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.farimarwat.speedtest.presentation.navigation.Screen
import com.farimarwat.speedtest.presentation.viewmodel.HomeViewModel
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

    val sheetState = rememberModalBottomSheetState()
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
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    GoButton("Go") {
                        navController.navigate(Screen.Test.route)
                    }
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

        //servers list
        if(showServers){
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    showServers = false
                }
            ){
                LazyColumn {
                    items(serversList){item ->
                        ServerItem(
                            server = item,
                            onClick = {
                                viewModel.changeSelectedServer(it)
                                showServers = false
                            }
                        )
                    }
                }
            }
        }
    }
}