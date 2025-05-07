package com.farimarwat.speedtest.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farimarwat.speedtest.presentation.component.SpeedTestCard
import com.farimarwat.speedtest.presentation.navigation.Screen
import com.farimarwat.speedtest.presentation.viewmodel.HistoryScreenViewModel
import com.farimarwat.speedtest.presentation.viewmodel.MapScreenViewModel

@Composable
fun HistoryScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    historyScreenViewModel: HistoryScreenViewModel,
    mapScreenViewModel: MapScreenViewModel
) {
    LaunchedEffect(Unit) {
        historyScreenViewModel.listSpeedTests()
    }
    val list by historyScreenViewModel.speedTests.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 15.dp),
            contentPadding = PaddingValues(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(
                items = list,
                key = {
                    it.id
                }
            ) {speedTest->
                SpeedTestCard(speedTest){
                    mapScreenViewModel.setSpeedTest(speedTest)
                    navController.navigate(Screen.TestMap.route)
                }
            }
        }
    }
}