package com.uitest.feature.home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import paymentsave.terminalapp.core.ui.VoidComposable
import com.uitest.feature._core.ui.ScreenStrategy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavGraph(
    modifier: Modifier = Modifier,
    bottomBar: VoidComposable,
) {
    ScreenStrategy(
        modifier = Modifier.semantics{
            contentDescription="Home Screen"
        },
        bottomBar=bottomBar,
        fab = {},
    ) {modifier ->
        SimpleCalculatorScreen (
            modifier = Modifier,
            onCalculate = {}
        )
    }
}