package com.kzcse.tfliteconcept.feature.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kzcse.tfliteconcept.core.ui.SpacerVertical
import com.kzcse.tfliteconcept.feature._core.presentation.ScreenStrategy
import com.uitest.feature._core.ui.ButtonView
import com.uitest.feature._core.ui.TextDescription
import com.uitest.feature._core.ui.TextPoint
import com.uitest.feature._core.ui.TextHeading2
import com.uitest.feature._core.ui.TextHeading1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    onAppInfoRequest: () -> Unit,
    bottomBar: @Composable () -> Unit,
    navRail: @Composable () -> Unit,
    fab: @Composable () -> Unit
) {


    ScreenStrategy(
        bottomBar = bottomBar,
        fab = fab,
        navRail = navRail,
        title = {
            TextHeading1(text = "Hilsha Fish Detector")
        }
    ) {
        Column(
            modifier = it
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            SpacerVertical(16)
            TextDescription(text = "Detect Hilsha fish with ease! Our AI model identifies four different Hilsha classes accurately.")
            SpacerVertical(24)
            FeatureList()
            SpacerVertical(16)
        }
    }


}





@Composable
fun FeatureList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.width(IntrinsicSize.Max)
    ) {
        FeaturesSection(
            title = "Supported Features", features = listOf(
                "Detects 4 Hilsha classes: Chondona, Gurta, Healthy-Ilish, Jhatka-Ilish",
                "Capture images directly from the camera",
                "Select images from your device's gallery",
                "Use images from the app’s integrated gallery"
            )
        )
    }
}

@Composable
private fun FeaturesSection(title: String, features: List<String>) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextHeading2(
            text = title,
            modifier =  Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(16.dp))
        features.forEach { feature ->
            TextPoint(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                text = feature
            )
        }
    }
}



@Composable
private fun CallToActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ButtonView(
        modifier = modifier,
        label = "More Info",
        icon = Icons.Outlined.Info,
        onClick = onClick
    )

}
