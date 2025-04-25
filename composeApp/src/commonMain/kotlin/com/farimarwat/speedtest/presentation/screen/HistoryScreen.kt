package com.farimarwat.speedtest.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farimarwat.speedtest.presentation.component.SpeedTestCard
import com.farimarwat.speedtest.presentation.viewmodel.HistoryScreenViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    historyScreenViewModel: HistoryScreenViewModel
){
    LaunchedEffect(Unit){
        historyScreenViewModel.listSpeedTests()
    }
    val list by historyScreenViewModel.speedTests.collectAsStateWithLifecycle()
    Box(modifier = modifier){
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