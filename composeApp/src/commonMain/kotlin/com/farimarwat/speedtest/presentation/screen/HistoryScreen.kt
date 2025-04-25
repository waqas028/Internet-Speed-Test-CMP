package com.farimarwat.speedtest.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farimarwat.speedtest.presentation.component.SpeedTestCard
import com.farimarwat.speedtest.presentation.viewmodel.HistoryScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    historyScreenViewModel: HistoryScreenViewModel
){
    LaunchedEffect(Unit){
        historyScreenViewModel.listSpeedTests()
    }
    val list by historyScreenViewModel.speedTests.collectAsStateWithLifecycle()
    Box(modifier = modifier){
       Column {
           TopAppBar(
               title = { Text("Test History")},
               navigationIcon = {
                   IconButton(onClick = {
                       navController.navigateUp()
                   }) {
                       Icon(
                           imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                           contentDescription = "Back"
                       )
                   }
               }
           )
           LazyColumn {
               items(
                   items = list,
                   key = {
                       it.id
                   }
               ){
                   SpeedTestCard(it)
               }
           }
       }
    }
}