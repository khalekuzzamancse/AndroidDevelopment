package paymentsave.terminalapp.core.ui

import android.R.attr.onClick
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import paymentsave.terminalapp.core.language.VoidCallback

@Composable
fun ButtonView(
    modifier: Modifier = Modifier,
    background: Color = Color.Unspecified,
    label: String,
    enabled: Boolean=true,
    labelStyle: TextStyle= TextStyle(),
    onClick: VoidCallback,
    ) {
    Button(
        onClick = {
            if(enabled)
                onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = background
        ),
        modifier = modifier,

    ) {
        Text(text = label, style = labelStyle)
    }
}