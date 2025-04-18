package com.farimarwat.speedtest.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.farimarwat.speedtest.presentation.component.GoButton
import com.farimarwat.speedtest.presentation.navigation.Screen
import com.farimarwat.speedtest.presentation.viewmodel.HomeViewModel
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import speedtest.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController:NavHostController
    ){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){

       GoButton("Go") {
           navController.navigate(Screen.Test.route)
       }
    }
}