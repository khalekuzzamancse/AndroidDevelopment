package paymentsave.terminalapp.core.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource

@Composable
fun ImageView(
    modifier: Modifier = Modifier,
    resourceId: Int,
    contentDescription: String=""
) {
    Image(
        painter = painterResource(id =resourceId),
        contentDescription =contentDescription,
        modifier = modifier
    )
}

