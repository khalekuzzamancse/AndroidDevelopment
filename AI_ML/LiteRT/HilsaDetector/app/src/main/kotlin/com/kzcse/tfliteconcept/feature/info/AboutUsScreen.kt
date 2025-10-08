@file:Suppress("SpellCheckingInspection")

package com.kzcse.tfliteconcept.feature.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kzcse.tfliteconcept.feature._core.presentation.ScreenStrategy
import  com.kzcse.tfliteconcept.R
import com.kzcse.tfliteconcept.core.ui.SpacerVertical
import com.uitest.feature._core.ui.TextHeading1
import com.uitest.feature._core.ui.TextHeading3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsPage(
    bottomBar: @Composable () -> Unit,fab: @Composable () -> Unit,
    navRail: @Composable () -> Unit
) {
    ScreenStrategy(
        bottomBar = bottomBar,
        navRail=navRail,
        fab = fab,
        title = {
            TextHeading1(text="About Us")
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
            SectionHeading()
            Spacer(modifier = Modifier.height(8.dp))

            // Supervisor Section
            SupervisorSection()
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider()

            Spacer(modifier = Modifier.height(24.dp))

            AIDeveloperSection()
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider()

            Spacer(modifier = Modifier.height(24.dp))

            // Developer Section
            DeveloperSection()
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))

            // Copyright Notice
            CopyrightNotice()
            SpacerVertical(16)
        }
    }
}

@Composable
private fun SectionHeading() {
    TextHeading3(text = "Supervised by")
}


@Composable
private fun SupervisorSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Image(
            res = R.drawable.super_vicer,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sk. Shalauddin Kabir",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Lecturer",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        DeptAndUniversity()

    }
}

@Composable
private fun AIDeveloperSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        TextHeading3(text = "ML Developer")
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            res = R.drawable.ai_developer,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Shahadat Hossian",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )


    }
}
@Composable
private fun DeveloperSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        TextHeading3(text = "App Developer")

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            res = R.drawable.developer,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Md. Khalekuzzman",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Student",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        DeptAndUniversity()

    }
}

@Composable
fun ColumnScope.DeptAndUniversity(modifier: Modifier = Modifier) {
        Text(
            modifier=modifier.align(Alignment.Start) ,
            text = "Department of  CSE at Jashore University of Science and Technology (JUST)",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )


}


@Composable
fun CopyrightNotice() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        // Display the university logo
        Image(
            res = R.drawable.just_logo,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Copyright and University Attribution Text
        Text(
            text = "© 2025, Jashore University of Science and Technology (JUST)",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Hilsha Fish Detector App",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Developed in the Department of Computer Science and Engineering(CSE)",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )
    }
}



@Composable
private fun Image(modifier: Modifier = Modifier, res: Int) {
    Image(
        modifier = modifier,
        painter = painterResource(res),
        contentDescription = null
    )
}
