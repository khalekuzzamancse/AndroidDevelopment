@file:Suppress("NewApi")

package com.kzcse.tfliteconcept.feature._navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.kzcse.tfliteconcept.core.ui.VoidComposable
import com.kzcse.tfliteconcept.feature._core.presentation.BottomBar
import com.kzcse.tfliteconcept.feature._core.presentation.BottomBarItem
import com.kzcse.tfliteconcept.feature._core.presentation.NavRail
import com.kzcse.tfliteconcept.feature.info.HomeRoute
import com.kzcse.tfliteconcept.feature.info.AboutAppScreen
import com.kzcse.tfliteconcept.feature.info.AboutUsPage
import com.kzcse.tfliteconcept.feature.info.UserManualScreen
import com.kzcse.tfliteconcept.feature.classify.ClassificationScreen
import com.kzcse.tfliteconcept.feature.image_picker.GalleryScreen
import com.uitest.feature._core.ui.BackIcon
import com.uitest.feature._core.ui.ButtonView


@Composable
fun NavigationRootX(
    modifier: Modifier = Modifier
) {

    val viewModel = viewModel { NavigationViewModel() }
    val selected = viewModel.selected.collectAsState().value
    var backPressCountOnHome = remember { 0 }
    val context = LocalContext.current
    val backStack = viewModel.backStack
    BackHandler {
        if (backStack.size == 1) {
            backPressCountOnHome++
        } else {
            backPressCountOnHome = 0//reset
        }
        if (backPressCountOnHome >= 2) {
            (context as? Activity)?.finish()
        }
        viewModel.onBack()


    }

    val navRail: VoidComposable=remember(selected) {
        {
            NavRail(
                modifier = Modifier,
                selectedRoute = selected,
                onHomeClick = {
                    viewModel.onSelect(Route.Home.route)
                },
                onManualRequest = {
                    viewModel.onSelect(Route.UserManual.route)
                },
                onRecognizeRequest = {
                    viewModel.onSelect(Route.Recognize.route)
                },
                onAboutUsRequest = {
                    viewModel.onSelect(Route.AboutUs.route)
                },
                onAboutAppRequest = {
                    viewModel.onSelect(Route.AboutApp.route)
                }
            )
        }
    }
    val bottomBar: VoidComposable = remember(selected) {
        {
            BottomBar(
                selectedRoute = selected,
                onHomeClick = {
                    viewModel.onSelect(Route.Home.route)
                },
                onManualRequest = {
                    viewModel.onSelect(Route.UserManual.route)
                },
                onRecognizeRequest = {
                    viewModel.onSelect(Route.Recognize.route)
                },
                onAboutUsRequest = {
                    viewModel.onSelect(Route.AboutUs.route)
                },
                onAboutAppRequest = {
                    viewModel.onSelect(Route.AboutApp.route)
                }
            )
        }
    }
    val fab: VoidComposable = remember {
        @Composable {

            ButtonView(
                modifier = Modifier,
                label = "Classify",
                icon = Icons.Default.CameraAlt
            ) {
                viewModel.onSelect(Route.Recognize.route)
            }

        }
    }
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {
            viewModel.onBack()
        },
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                is Route.Home -> {
                    NavEntry(key) {
                        HomeRoute(
                            onAppInfoRequest = {
                                viewModel.onSelect(Route.AboutUs.route)
                            },
                            bottomBar = bottomBar,
                            navRail = navRail,
                            fab = fab,
                        )
                    }
                }

                is Route.UserManual -> {
                    NavEntry(key) {
                        UserManualScreen(
                            bottomBar = bottomBar,
                            navRail=navRail,
                            fab = {}
                        )
                    }
                }

                is Route.Recognize -> {
                    NavEntry(key) {
                        GalleryScreen(
                            bottomBar = bottomBar,
                            navRail=navRail,
                            onProcessRequest = {
                                NavigationViewModel.processImage = it
                                viewModel.onSelect(Route.Process.route)
                            },
                            navigationIcon = {
                                BackIcon(
                                    onClick = {
                                        viewModel.pop()
                                    }
                                )
                            }
                        )
                    }
                }

                is Route.Process -> {
                    NavEntry(key) {
                        NavigationViewModel.processImage?.let { bitmap ->
                            ClassificationScreen(
                                bitmap = bitmap,
                                navRail=navRail,
                                navigationIcon = {
                                    BackIcon(
                                        onClick = viewModel::pop
                                    )
                                })
                        }

                    }
                }

                is Route.AboutUs -> {
                    NavEntry(key) {
                        AboutUsPage(
                            bottomBar = bottomBar,
                            navRail=navRail,
                            fab = { }
                        )

                    }
                }

                is Route.AboutApp -> {
                    NavEntry(key) {
                        AboutAppScreen(
                            bottomBar = bottomBar,
                            navRail=navRail,
                            fab = { }
                        )

                    }
                }

                else -> throw kotlin.RuntimeException("Invalid root")
            }
        }
    )

}

