package paymentsave.terminalapp.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

typealias  VoidComposable= @Composable ()->Unit
fun Color.contentColor(): Color{
   return if(this.luminance()>06f) Color.White else Color.Black
}

