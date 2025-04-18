package com.farimarwat.speedtest.presentation.component


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import kotlin.math.*

@Composable
fun SpeedMeter(
    modifier: Modifier,
    backgroundColor: Color = Color.DarkGray,
    progressWidth: Float,
    progress: Float,
    labelPadding: Int = -10,
    labelColor: Color = Color.White,
    needleColors: List<Color> = listOf(Color.Black, Color.White),
    needleKnobColors: List<Color> = listOf(Color.Black, Color.White),
    needleKnobSize: Float = 10f,
    progressColors: List<Color> = listOf(Color.Red, Color.Yellow),
    unitText: String = "MB"
) {
    val startAngle = 150f
    val sweepAngleMax = 240f
    val counterMax = 100
    val sweepAngleAnimatable = remember { Animatable(0f) }
    val rotationAngleAnimatable = remember { Animatable(0f) }

    val textMeasurer = rememberTextMeasurer()

    LaunchedEffect(key1 = progress) {
        var prog = progress
        if (prog > 100) {
            prog = 100f
        }
        val progressAngle = (prog / 100f) * sweepAngleMax
        sweepAngleAnimatable.animateTo(
            targetValue = progressAngle,
            animationSpec = tween(500)
        )
    }

    LaunchedEffect(key1 = progress) {
        var prog = progress
        if (prog > 100) {
            prog = 100f
        }
        rotationAngleAnimatable.animateTo(
            targetValue = (prog / 100f) * sweepAngleMax + startAngle - 90f,
            animationSpec = tween(500)
        )
    }

    Canvas(modifier = modifier) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val archSize = size.width * 0.8f
        val x = (size.width / 2f) - (archSize / 2f)
        val y = (size.height / 2f) - (archSize / 2f)

        drawRect(color = backgroundColor, topLeft = Offset.Zero)

        drawArc(
            brush = Brush.linearGradient(colors = listOf(Color.Gray, Color.Black)),
            style = Stroke(progressWidth),
            topLeft = Offset(x, y),
            startAngle = startAngle,
            sweepAngle = sweepAngleMax,
            useCenter = false,
            size = Size(archSize, archSize)
        )

        drawArc(
            brush = Brush.linearGradient(colors = progressColors),
            style = Stroke(progressWidth),
            topLeft = Offset(x, y),
            startAngle = startAngle,
            sweepAngle = sweepAngleAnimatable.value,
            useCenter = false,
            size = Size(archSize, archSize)
        )

        // Draw text around the arc
        val textAngleRadians = sweepAngleMax.toDouble().degreesToRadians()
        val textAngleDegrees = textAngleRadians.radiansToDegrees().toFloat()
        val labelRadius = (archSize / 2f) - (progressWidth / 2f) + labelPadding.dp.toPx()
        val textRange = 0..counterMax step 10

        textRange.forEach { value ->
            val currentColor = if (value > progress) Color.Gray else labelColor
            val valueRadians = (startAngle + (value / counterMax.toFloat()) * textAngleDegrees).toDouble().degreesToRadians()
            val digitx = (centerX + labelRadius * cos(valueRadians)).toFloat()
            val digity = (centerY + labelRadius * sin(valueRadians)).toFloat()

            val textLayoutResult = textMeasurer.measure(
                text = value.toString(),
                style = TextStyle(
                    color = currentColor,
                    fontSize = archSize.times(0.02f).sp,
                    textAlign = TextAlign.Center
                )
            )

            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    digitx - textLayoutResult.size.width / 2f,
                    digity - textLayoutResult.size.height / 2f
                )
            )
        }

        // Drawing progress value
        val progressText = textMeasurer.measure(
            text = progress.toString(),
            style = TextStyle(
                color = labelColor,
                fontSize = archSize.times(0.06f).sp,
                textAlign = TextAlign.Center
            )
        )

        val up_x = centerX
        val up_y = centerY + (archSize / 3f)
        drawText(
            textLayoutResult = progressText,
            topLeft = Offset(
                up_x - progressText.size.width / 2f,
                up_y - progressText.size.height / 2f
            )
        )

        // Drawing unit text
        val unitTextLayout = textMeasurer.measure(
            text = unitText,
            style = TextStyle(
                color = labelColor,
                fontSize = archSize.times(0.04f).sp,
                textAlign = TextAlign.Center
            )
        )

        val unit_y = up_y + progressText.size.height
        drawText(
            textLayoutResult = unitTextLayout,
            topLeft = Offset(
                up_x - unitTextLayout.size.width / 2f,
                unit_y - unitTextLayout.size.height / 2f
            )
        )

        // Draw needle
        val needleWidth = progressWidth * 0.6f
        val needleHeight = archSize / 3f
        val needleBottomY = centerY + needleHeight
        val needleTopY = needleBottomY - needleHeight
        val needleLeftX = centerX - needleWidth / 2f
        val needleGradient = Brush.linearGradient(
            colors = needleColors,
            start = Offset(centerX - needleWidth / 2f, needleTopY),
            end = Offset(centerX + needleWidth / 2f, needleBottomY)
        )

        withTransform({
            rotate(rotationAngleAnimatable.value, Offset(centerX, centerY))
        }) {
            drawRect(
                brush = needleGradient,
                size = Size(needleWidth, needleHeight),
                topLeft = Offset(needleLeftX, needleTopY)
            )
        }

        // Drawing needle knob
        val needleKnobGradient = Brush.radialGradient(
            colors = needleKnobColors,
            center = Offset(centerX, centerY),
            radius = needleKnobSize
        )
        drawCircle(
            brush = needleKnobGradient,
            center = Offset(centerX, centerY),
            radius = needleKnobSize,
            style = Stroke(24.dp.toPx())
        )
    }
}

// Extension functions for degree/radian conversion
fun Double.degreesToRadians() = this * PI / 180.0
fun Double.radiansToDegrees() = this * 180.0 / PI