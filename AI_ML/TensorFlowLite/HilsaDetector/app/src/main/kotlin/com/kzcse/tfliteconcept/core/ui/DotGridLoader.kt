package com.kzcse.tfliteconcept.core.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
@Composable
fun RandomWaveDotGridLoader(
    modifier: Modifier = Modifier,
    rows: Int = 10,
    columns: Int = 10,
    dotSize: Dp = 20.dp,
    spacing: Dp = 10.dp,
    animationDuration: Int = 300 // ms for each wave
) {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary
    )

    // Random grid colors
    val gridColors = remember(rows, columns) {
        List(rows * columns) { colors.random() }
    }

    val infiniteTransition = rememberInfiniteTransition()
    val animatedAlpha = List(rows * columns) {
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 0.3f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = animationDuration,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset((0..animationDuration).random())
            )
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (row in 0 until rows) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (col in 0 until columns) {
                    val idx = row * columns + col
                    Box(
                        Modifier
                            .size(dotSize)
                            .padding(spacing / 2)
                            .graphicsLayer { alpha = animatedAlpha[idx].value }
                            .background(
                                color = gridColors[idx].copy(alpha = 0.85f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun DotGridLoader(
    modifier: Modifier = Modifier,
    rows: Int = 10,
    columns: Int = 10,
    dotSize: Dp = 20.dp,
    spacing: Dp = 10.dp,
    animationDuration: Int = 200 // ms
) {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary
    )

    // Generate grid colors with a constraint to avoid immediate neighbor duplicates
    val gridColors = remember(rows, columns) {
        List(rows * columns) { index ->
            val neighbors = mutableListOf<Int>()
            val row = index / columns
            val col = index % columns
            // collect neighbors' indices
            if (row > 0) neighbors.add((row - 1) * columns + col) // above
            if (col > 0) neighbors.add(row * columns + (col - 1))   // left
            colors.random()
        }
    }

    val infiniteTransition = rememberInfiniteTransition()
    val animatedAlpha = List(rows * columns) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration
                    0.3f at 0
                    1f at animationDuration / 2
                    0.3f at animationDuration
                },
                initialStartOffset = StartOffset(index * 40),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (row in 0 until rows) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (col in 0 until columns) {
                    val idx = row * columns + col
                    Box(
                        Modifier
                            .size(dotSize)
                            .padding(spacing / 2)
                            .graphicsLayer {
                                alpha = animatedAlpha[idx].value
                            }
                            .background(
                                color = gridColors[idx].copy(alpha = 0.85f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}


