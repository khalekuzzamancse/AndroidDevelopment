package com.kzcse.tfliteconcept.feature.info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kzcse.tfliteconcept.core.ui.SpacerVertical
import com.kzcse.tfliteconcept.feature._core.presentation.ScreenStrategy
import com.uitest.feature._core.ui.TextDescription
import com.uitest.feature._core.ui.TextHeading1
import com.uitest.feature._core.ui.TextHeading3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserManualScreen(
    bottomBar: @Composable () -> Unit,
    navRail: @Composable () -> Unit,
    fab: @Composable () -> Unit
) {
    ScreenStrategy(
        bottomBar = bottomBar,
        fab = fab,
        navRail = navRail,
        title = {
            TextHeading1(text =" Usage Guide")
        }
    ) {
        Column(
            modifier = it
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerVertical(16)
            InstructionStep(
                stepNumber = 1,
                title = "Pick an Image",
                description = "You can select an image directly from your device’s camera or gallery."
            )
            SpacerVertical(16)
            InstructionStep(
                stepNumber = 2,
                title = "AI Detection",
                description = "The AI will analyze the image and detect the type among these four classes:\n" +
                        "✔ Chondona\n✔ Gurta\n✔ Jhatka-Ilish\n✔ Helath-Ilish"
            )
            SpacerVertical(16)
            InstructionStep(
                stepNumber = 3,
                title = "Results & Information",
                description = "The app will display the detected type along with additional information about that class."
            )


        }
    }
}



@Composable
fun InstructionStep(
    stepNumber: Int,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            TextHeading3(text="Step $stepNumber: $title",)
            Spacer(modifier = Modifier.height(2.dp))
            TextDescription(
                text = description,
            )
        }
    }
}

