package com.kzcse.tfliteconcept.feature._core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kzcse.tfliteconcept.core.ui.SpacerHorizontal
import com.kzcse.tfliteconcept.core.ui.VoidComposable
import com.kzcse.tfliteconcept.feature._core.logic.LoadingAndFeedbackController
import com.uitest.feature._core.ui.LoadingView
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenStrategy(
    modifier: Modifier = Modifier,
    title: VoidComposable? = null,
    navigationIcon: VoidComposable? = null,
    controller: LoadingAndFeedbackController? = null,
    fab: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit,
    navRail: (@Composable () -> Unit)? = null, // new optional param
    content: @Composable BoxScope.(Modifier) -> Unit
) {
    val isLoading = controller?.isLoading?.collectAsState()?.value ?: false
    val hasTopBar = title != null || navigationIcon != null

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isWideScreen = maxWidth > 600.dp

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (hasTopBar) {
                    TopAppBar(
                        title = { title?.invoke() },
                        navigationIcon = { navigationIcon?.invoke() },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(16.dp)
                        )
                    )
                }
            },
            bottomBar = {
                if (!isWideScreen) {
                    Box {
                        bottomBar()
                        if (isLoading) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(Color.Gray.copy(alpha = 0.95f))
                            )
                        }
                    }
                }
            },
            floatingActionButton = { fab() }
        ) { innerPadding ->
            Row(modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
            ) {
                if (isWideScreen) {

                        navRail?.invoke() // show navRail on wide screens
                    SpacerHorizontal(4)
                }
                Content(modifier =Modifier
                    .fillMaxSize()
                    .weight(1f),
                    content=content,
                    isLoading=isLoading)
            }
        }
    }
}

@Composable
fun RowScope.Content(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(Modifier) -> Unit,
    isLoading: Boolean
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        content(Modifier)
        if (isLoading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Gray.copy(alpha = 0.95f)),
                contentAlignment = Alignment.Center
            ) {
                LoadingView(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}



//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ScreenStrategy(
//    modifier: Modifier = Modifier,
//    title: VoidComposable? = null,
//    navigationIcon: VoidComposable? = null,
//    topBarColor: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
//    controller: LoadingAndFeedbackController? = null,
//    fab: @Composable () -> Unit ={},
//    bottomBar: @Composable () -> Unit,
//    content:@Composable  BoxScope.(Modifier) -> Unit
//) {
//    val isLoading = (controller?.isLoading?.collectAsState()?.value) ?: false
//    val hasTopBar = (title != null || navigationIcon != null)
//
//
//
//   Scaffold(
//       modifier=modifier,
//        topBar = {
//            //Empty top bar use extra empty at top space
//            if (hasTopBar) {
//                TopAppBar(
//                    title = {
//                        if (title != null)
//                            title()
//                    },
//                    navigationIcon = {
//                        if (navigationIcon != null)
//                            navigationIcon()
//                    },
//                    colors = topBarColor
//                )
//            }
//
//        },
//        bottomBar = {
//            Box{
//                bottomBar()
//                if (isLoading) {
//                    Box(
//                        modifier = Modifier
//                            .matchParentSize()
//                            .background(Color.Gray.copy(alpha = 0.95f))
//                    )
//                }
//            }
//
//        },
//       floatingActionButton ={
//           fab()
//       }
//    ) {
//        Box(
//            modifier = Modifier
//                .padding(it)
//                .fillMaxSize(),
//            contentAlignment = Alignment.TopCenter
//        ) {
//            content(Modifier)
//            if (isLoading) {
//                Box(
//                    modifier = Modifier
//                        .matchParentSize()
//                        .background(Color.Gray.copy(alpha = 0.95f)),
//                    contentAlignment = Alignment.Center
//                ) {
//                    LoadingView(
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//
//            }
//
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenStrategy(
    title: VoidComposable? = null,
    navigationIcon: VoidComposable? = null,
    topBarColor: TopAppBarColors = TopAppBarDefaults.topAppBarColors(

    ),
    controller: LoadingAndFeedbackController? = null,
    content: @Composable (Modifier) -> Unit
) {
    val isLoading = (controller?.isLoading?.collectAsState()?.value) ?: false
    val hasTopBar = (title != null || navigationIcon != null)

    Scaffold(
        topBar = {
            //Empty top bar use extra empty at top space
            if (hasTopBar) {
                TopAppBar(
                    title = {
                        if (title != null)
                            title()
                    },
                    navigationIcon = {
                        if (navigationIcon != null)
                            navigationIcon()
                    },
                    colors = topBarColor
                )
            }

        },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            content(Modifier)
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Gray.copy(alpha = 0.95f)),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingView(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

            }

        }
    }
}