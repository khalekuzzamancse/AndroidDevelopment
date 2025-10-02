package com.uitest.feature._core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import paymentsave.terminalapp.core.ui.SpacerVertical

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    CircularProgressIndicator(Modifier.size(64.dp))
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = ColorFactory.colors.pageColor), contentAlignment = Alignment.Center){
        LoadingView()
    }
}

@Composable
fun NoDataView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
//            Image(
//                painter = painterResource(id = R.drawable.nodata),
//                contentDescription = "App Logo",
//                modifier = Modifier.size(200.dp)
//            )
            SpacerVertical(16)
            Text(text = "No Data Found")

        }

    }


}

@Composable
fun LoadingMoreDataView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Text("Loading More Data...", color = Color(0xFF3569D6))
    }
}


@Preview
@Composable
private fun LoadingViewPreview() {
    LoadingView()
}