package com.uitest.feature.history
import android.R.attr.text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import paymentsave.terminalapp.core.ui.VoidComposable
import com.uitest.feature._core.ui.ScreenStrategy
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryNavGraph(
    modifier: Modifier = Modifier,
    bottomBar: VoidComposable,
) {
    ScreenStrategy(
        modifier = Modifier.semantics{
            contentDescription="History Screen"
        },
        bottomBar=bottomBar,
        fab = {},
    ) {modifier ->
        Text( modifier=modifier.align(Alignment.Center),text="History")
    }
}