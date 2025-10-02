package com.uitest.feature._core.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun LoaderWithLogoInCircle(
    modifier: Modifier = Modifier.size(64.dp),
    logo:@Composable (Modifier)->Unit = {},
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // Animated arcs behind the logo
        val infiniteTransition = rememberInfiniteTransition(label = "Loader Animation")
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1200, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = "Rotation"
        )
        Canvas(modifier = Modifier.matchParentSize()) {
            val stroke = Stroke(width = size.minDimension / 8f, cap = StrokeCap.Round)
            val colors = listOf(
                ColorFactory.colors.primary,
                ColorFactory.colors.colorPrimaryTwo,
                ColorFactory.colors.primary,
                ColorFactory.colors.colorPrimaryTwo
            )
            val sweepAngle = 70f
            for (i in colors.indices) {
                drawArc(
                    color = colors[i],
                    startAngle = rotation + i * 90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = stroke
                )
            }
        }
        // Logo at the center
        logo(
            Modifier.size(32.dp)
        )
    }
}

