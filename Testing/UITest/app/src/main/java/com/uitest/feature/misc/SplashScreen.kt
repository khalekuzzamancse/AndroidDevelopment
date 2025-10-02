package com.uitest.feature.misc

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import paymentsave.terminalapp.core.language.Logger
import paymentsave.terminalapp.core.ui.DevicePreviewsGroup
import kotlin.math.min

@Composable
fun SplashScreen() {
    // Original design sizes (Figma)
    val originalCanvasWidth = 720f
    val originalCanvasHeight = 1440f
    val originalLogoWidth = 450
    val originalLogoHeight = 100

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground(LocalDensity.current.density),
    ) {
        val canvasWidth = this.maxWidth
        val canvasHeight = this.maxHeight
        val scaleX = canvasWidth / originalCanvasWidth
        val scaleY = canvasHeight / originalCanvasHeight
        val scale = minOf(scaleX, scaleY)
//        ImageView(
//            resourceId = R.drawable.logo_paymentsave,
//            modifier = Modifier
//                .align(Alignment.Center)
//                .width(originalLogoWidth * scale)
//                .height(originalLogoHeight * scale)
//        )
    }

}

@SuppressLint("SuspiciousModifierThen")
private fun Modifier.gradientBackground(density: Float): Modifier = this.then(
    drawBehind {
        val svgWidth = 720f
        val svgHeight = 1440f
        val canvasHeightPx = size.height
        val canvasWidthPx = size.width
        val canvasWidthDp = size.width.toDp()
        val canvasHeightDp = size.height.toDp()

        val aspectRatio = svgWidth / svgHeight

        Logger.on("gradientBackground", "sizeInPx", "($canvasWidthPx,$canvasHeightPx)")
        Logger.on(
            "gradientBackground", "sizeDp", ""
                    + "($canvasWidthDp,$canvasHeightDp)"
        )

        val scale = min(size.width / svgWidth, size.height / svgHeight)
        val scaledWidth = svgWidth * scale
        val scaledHeight = svgHeight * scale
        val offsetX = (size.width - scaledWidth) / 2

        val offsetY = (size.height - scaledHeight) / 2
        val gradientStart = Offset(-152.757f, -375.684f)
        val gradientEnd = Offset(canvasWidthPx, canvasHeightPx)

        val brush = Brush.linearGradient(
            0.46f to Color.White.copy(alpha = 0f),
            0.95f to Color(0xFF00B5FA),
            start = Offset(
                offsetX + gradientStart.x * scale,
                offsetY + gradientStart.y * scale
            ),
            end = Offset(
                offsetX + gradientEnd.x * scale,
                offsetY + gradientEnd.y * scale
            )
        )

        drawRect(
            brush = brush,
            size = size
        )
    }
)


@DevicePreviewsGroup()
@Composable
private fun Preview() {
    SplashScreen()
}
