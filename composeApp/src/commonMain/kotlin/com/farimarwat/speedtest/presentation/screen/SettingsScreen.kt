package com.farimarwat.speedtest.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreen(modifier: Modifier = Modifier){
    Box(
        modifier = Modifier
            .then(modifier)
    ){
        Text("Settings Screen")
    }
}