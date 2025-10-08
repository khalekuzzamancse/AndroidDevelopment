package com.kzcse.tfliteconcept.feature.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kzcse.tfliteconcept.R
import com.kzcse.tfliteconcept.core.ui.SpacerVertical
import com.kzcse.tfliteconcept.feature._core.presentation.ScreenStrategy
import com.uitest.feature._core.ui.TextDescription
import com.uitest.feature._core.ui.TextHeading1
import com.uitest.feature._core.ui.TextHeading3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(
    bottomBar: @Composable () -> Unit, fab: @Composable () -> Unit,
    navRail: @Composable () -> Unit
) {
    ScreenStrategy(
        bottomBar = bottomBar,
        fab = fab,
        navRail = navRail,
        title = {
            TextHeading1(text = "About App")
        }
    ) {
        Column(
            modifier = it
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SpacerVertical(16)
            TextDescription(
                text = "This application is designed to detect various types of Hilsha fish using advanced AI models. The supported classes for detection are:",
                modifier = Modifier.align(Alignment.Start),
            )

            Spacer(modifier = Modifier.height(8.dp))
            SupportedClassesSection()


            Spacer(modifier = Modifier.height(12.dp))

            TextHeading3(text = "Technical Details")

            Spacer(modifier = Modifier.height(8.dp))

            TextDescription(
                text = "Our model was trained using TensorFlow and PyTorch frameworks and later converted to TensorFlow Lite for seamless mobile integration. TensorFlow Lite enables efficient inference on mobile devices, ensuring faster detection without compromising accuracy.",
                modifier = Modifier.align(Alignment.Start),
            )

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))

            CopyrightNotice()
            SpacerVertical(16)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SupportedClassesSection() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextHeading3(text = "Supported Hilsha Fish Classes")
        Spacer(modifier = Modifier.height(12.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),

            ) {

            ClassItem(
                imageRes = R.drawable.chondona,
                className = "Chondona"
            )

            ClassItem(
                imageRes = R.drawable.gurta,
                className = "Gurta"
            )

            ClassItem(
                imageRes = R.drawable.healthy_ilish,
                className = "Health-Ilish"
            )

            ClassItem(
                imageRes = R.drawable.jhatka_ilish,
                className = "Jhatka-Ilish"
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }

}

@Composable
fun ClassItem(imageRes: Int, className: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            //.padding(8.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = className,
            modifier = Modifier
                .size(100.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(8.dp)
                ) //Since regardless of theme the image back are always white(we ensured)

        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = className,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
