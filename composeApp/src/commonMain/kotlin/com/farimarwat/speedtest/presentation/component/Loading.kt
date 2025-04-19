package com.farimarwat.speedtest.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import speedtest.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Loading(){
    Box(
        modifier = Modifier
            .size(200.dp)
    ){
        val composition by rememberLottieComposition{
            LottieCompositionSpec.JsonString(
                Res.readBytes("files/loading.json").decodeToString()
            )
        }
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = rememberLottiePainter(
                composition = composition,
                iterations = Compottie.IterateForever
            ),
            contentDescription = "Go",
            contentScale = ContentScale.Fit
        )
    }
}