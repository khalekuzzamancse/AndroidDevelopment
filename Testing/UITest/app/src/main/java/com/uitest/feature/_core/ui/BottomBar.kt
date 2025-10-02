@file:Suppress("ComposableNaming","Unused")

package com.uitest.feature._core.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uitest.R
import paymentsave.terminalapp.core.ui.SpacerVertical
import paymentsave.terminalapp.core.ui.TextView

enum class BottomBarItem{
    Home, History, Report, Profile
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selectedRoute: BottomBarItem,
    onHomeClick: () -> Unit,
    onHistoryRequest: () -> Unit,
    onReportClick: () -> Unit,
    onProfileClick: () -> Unit,
) {

    var offsetX by rememberSaveable { mutableStateOf<Int?>(null) }
    var offsetY by rememberSaveable { mutableStateOf<Int?>(null) }
    val indicatorWidth =remember {  90.dp }
    val density=LocalDensity.current
    val itemWidth=remember { 60.dp }
    val shift=remember {
        val diff=indicatorWidth-itemWidth
        val shiftLeft=diff/2
        with(density){shiftLeft.toPx()}.toInt()
    }

    Surface(
        modifier = modifier,
        color = ColorFactory.Companion.colors.pageColor
    ) {
        Box(Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFF4F7FC))
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                _BarItem(
                    resId = R.drawable.ic_home,
                    onClick = onHomeClick,
                    width = itemWidth,
                    selected = selectedRoute == BottomBarItem.Home,
                    label = "Home",
                    onPositioned = {
                        offsetX=it.x
                        offsetY=it.y
                    }
                )
                _BarItem(
                    resId = R.drawable.ic_history,
                    onClick = onHistoryRequest,
                    selected = selectedRoute == BottomBarItem.History,
                    label = "History",
                    width = itemWidth,
                    onPositioned = {
                        offsetX=it.x
                        offsetY=it.y
                    }
                )
                _BarItem(
                    resId = R.drawable.ic_report,
                    onClick = onReportClick,
                    selected = selectedRoute == BottomBarItem.Report,
                    label = "Report",
                    width = itemWidth,
                    onPositioned = {
                        offsetX=it.x
                        offsetY=it.y
                    }
                )
                _BarItem(
                    resId = R.drawable.ic_profile,
                    onClick = onProfileClick,
                    selected = selectedRoute == BottomBarItem.Profile,
                    label = "Profile",
                    width = itemWidth,
                    onPositioned = {
                        offsetX=it.x
                        offsetY=it.y
                    }
                )
            }
        }
        offsetX?.let {x->
            offsetY?.let { y->
                _SelectionIndicator(
                    width = indicatorWidth,
                    offset = IntOffset(x-shift,y)
                )

            }
        }

    }

}

@Composable
private fun _BarItem(
    modifier: Modifier = Modifier,
    label: String,
    resId: Int,
    selected: Boolean,
    width: Dp,
    onClick: () -> Unit,
    onPositioned:(IntOffset)-> Unit
) {
    Column(
        horizontalAlignment =  Alignment.CenterHorizontally,
        modifier = Modifier.width(width)
            .onPlaced{
                if(selected){
                    val position=it.positionInParent()
                    onPositioned(IntOffset(position.x.toInt(),position.y.toInt()))
                }
            }

    ) {
        Icon(
            modifier = modifier
                .size(25.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClick()
                },
            painter = painterResource(resId),
            contentDescription = label,
            tint = if (selected) ColorFactory.Companion.colors.bottomBarItemSelection
            else Color.Black.copy(alpha = 0.35f),
        )
        SpacerVertical(4)
        TextView(
            text = label,
            color = if (selected) ColorFactory.Companion.colors.bottomBarItemSelection else Color.Unspecified,
            fontWeight = if (selected) FontWeight.W500 else FontWeight.W400,
            maxLines = 1,
            fontSize = 13.sp,
            modifier = Modifier
        )
    }


}

@Composable
fun _SelectionIndicator(
    offset: IntOffset,
    width: Dp,
) {
//    AnimatedVisibility(
//        visible = selected,
//        enter = slideInHorizontally(
//            initialOffsetX = { -it }, // slide in from left
//            //    animationSpec = tween(300)
//        ),
//        exit = slideOutHorizontally(
//            targetOffsetX = { it }, // slide out to right
//            // animationSpec = tween(300)
//        )
//    ) {
//    Logger.on("IntOffset", key = "","$offset")
//        Box(
//            Modifier
//                .width(width)
//                .height(4.dp)
//                .offset{
//                    offset
//                }.background(ColorFactory.Companion.colors.bottomBarItemSelection)
//
//        )
//   // }

}