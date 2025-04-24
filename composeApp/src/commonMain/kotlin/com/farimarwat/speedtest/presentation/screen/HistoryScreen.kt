package com.farimarwat.speedtest.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.farimarwat.speedtest.presentation.viewmodel.HistoryScreenViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    historyScreenViewModel: HistoryScreenViewModel
){
    Box(modifier = Modifier
        .then(modifier)){
        Text("History")
    }
}