package com.farimarwat.speedtest.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.farimarwat.speedtest.presentation.viewmodel.HomeViewModel
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import speedtest.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel){
    Box(){
        val composition by rememberLottieComposition{
            LottieCompositionSpec.JsonString(
                Res.readBytes("files/go.json").decodeToString()
            )
        }
        Image(
            painter = rememberLottiePainter(
                composition = composition,
                iterations = Compottie.IterateForever
            ),
            contentDescription = "Go"
        )
    }
}