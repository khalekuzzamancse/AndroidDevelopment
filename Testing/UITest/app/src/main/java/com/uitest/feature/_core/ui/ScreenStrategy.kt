package com.uitest.feature._core.ui

import android.R.attr.navigationIcon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import paymentsave.terminalapp.core.ui.VoidComposable
import com.uitest.feature._core.logic.LoadingAndFeedbackController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenStrategy(
    modifier: Modifier = Modifier,
    title: VoidComposable? = null,
    navigationIcon: VoidComposable? = null,
    topBarColor: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = ColorFactory.colors.pageColor
    ),
    controller: LoadingAndFeedbackController? = null,
    fab: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    content:@Composable  BoxScope.(Modifier) -> Unit
) {
    val isLoading = (controller?.isLoading?.collectAsState()?.value) ?: false
    val hasTopBar = (title != null || navigationIcon != null)



   Scaffold(
       modifier=modifier,
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
        bottomBar = {
            Box{
                bottomBar()
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Gray.copy(alpha = 0.95f))
                    )
                }
            }

        },
    ) {
        Box(
            modifier = Modifier
                .background(color = ColorFactory.colors.pageColor)
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            content(Modifier.background(ColorFactory.colors.pageColor))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenStrategy(
    title: VoidComposable? = null,
    navigationIcon: VoidComposable? = null,
    topBarColor: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = ColorFactory.colors.pageColor
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
                .background(color = ColorFactory.colors.pageColor)
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            content(Modifier.background(ColorFactory.colors.pageColor))
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