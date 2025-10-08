package com.kzcse.tfliteconcept.feature.classify

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kzcse.tfliteconcept.core.ui.RandomWaveDotGridLoader
import com.kzcse.tfliteconcept.feature._core.presentation.ScreenStrategy


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassificationScreen(
    bitmap: Bitmap,
    navRail: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit
) {
    val context = LocalContext.current
    val viewModel = viewModel { ClassifierViewModel(context) }
    val result = viewModel.result.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val resizedImage = viewModel.resizedImage.collectAsState().value


    LaunchedEffect(bitmap) {
        viewModel.resize(bitmap)
        viewModel.classify(bitmap)
    }
    ScreenStrategy (
        title = { Text("Classification Result") },
        navigationIcon = navigationIcon,
        fab = {},
        bottomBar = {},
        navRail = navRail
    ){
            Column(
                modifier = it
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                if (resizedImage != null) {
                    ImageWithProgress(
                        bitmap = resizedImage,
                        isLoading = isLoading,
                        isSuccess = result != null
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                DisplayResult(result, isLoading)

            }

    }

}

@Composable
fun ImageWithProgress(bitmap: Bitmap, isLoading: Boolean, isSuccess: Boolean) {
    val borderColor = when {
        isLoading -> MaterialTheme.colorScheme.surfaceVariant // Neutral color during loading
        isSuccess -> MaterialTheme.colorScheme.primary         // Green border on success
        else -> MaterialTheme.colorScheme.error                // Red border on failure
    }

    Box(
        modifier = Modifier
            .size(200.dp)
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(12.dp))
            .border(3.dp, borderColor, RoundedCornerShape(12.dp))
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Classified Image",
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        )

        if (isLoading) {
           RandomWaveDotGridLoader(
                modifier = Modifier
                    .matchParentSize()
                    .height(10.dp)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun DisplayResult(result: String?, isLoading: Boolean) {
    when {
        isLoading -> Text(
            text = "Classifying the image...",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        result == null -> Text(
            text = "Prediction Failed",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        else -> Text(
            text = "Result: $result",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
