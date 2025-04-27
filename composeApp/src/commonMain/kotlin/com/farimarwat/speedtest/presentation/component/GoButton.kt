package com.farimarwat.speedtest.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.farimarwat.speedtest.presentation.navigation.Screen
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import speedtest.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GoButton(label:String = "GO", onClick:()->Unit={}){
    Box(
        modifier = Modifier
            .size(200.dp),
        contentAlignment = Alignment.Center
    ){
        val composition by rememberLottieComposition{
            LottieCompositionSpec.JsonString(
                Res.readBytes("files/go.json").decodeToString()
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
        Text(
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                .padding(16.dp),
            text = label,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.Bold
        )
    }
}