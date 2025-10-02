@file:Suppress("NewApi")

package com.uitest.feature._navigation
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import paymentsave.terminalapp.core.ui.VoidComposable
import com.uitest.feature._core.ui.BottomBar
import com.uitest.feature._core.ui.BottomBarItem
import com.uitest.feature.history.HistoryNavGraph
import com.uitest.feature.home.HomeNavGraph
import com.uitest.feature.misc.SplashScreen
import com.uitest.feature.profile.ProfileNavGraph
import com.uitest.feature.report.ReportNavGraph

sealed class Route {
    @Serializable
    data object Splash : NavKey

    @Serializable
    data object Home : NavKey

    @Serializable
    data object History : NavKey

    @Serializable
    data object Report : NavKey

    @Serializable
    data object Profile : NavKey

    @Serializable
    data class NotImplemented(val id: Int) : NavKey
}

class NavigationViewModel(
    val backStack: NavBackStack
) : ViewModel() {
    private val _selected = MutableStateFlow(BottomBarItem.Home)
    val selected = _selected.asStateFlow()


    fun onHomeClick() {
        _selected.update { BottomBarItem.Home }
        pushIfNotExist(Route.Home)
    }

    fun onHistoryRequest() {
        _selected.update { BottomBarItem.History }
        pushIfNotExist(Route.History)
    }

    fun onReportClick() {
        _selected.update { BottomBarItem.Report }
        pushIfNotExist(Route.Report)
    }

    fun onProfileClick() {
        _selected.update { BottomBarItem.Profile }
        pushIfNotExist(Route.Profile)
    }

    private fun pushIfNotExist(route: NavKey) {
        if (backStack.lastOrNull() != route) {
            backStack.add(route)
        }
    }

    fun onBack() {
        if (Route.Home !in backStack) {
            backStack.clear()
            backStack.add(Route.Home)
        } else {
            // Pop everything until only Home remains
            while (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        }
        // Always update selected to Home
        _selected.update { BottomBarItem.Home }
    }


}

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(Route.Home)
    val viewModel = viewModel { NavigationViewModel(backStack) }
    val selected = viewModel.selected.collectAsState().value
    var backPressCountOnHome = remember { 0 }
    val context = LocalContext.current
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

    val bottomBar: VoidComposable = remember(selected) {
        {
            BottomBar(
                selectedRoute = selected,
                onHomeClick = viewModel::onHomeClick,
                onHistoryRequest = viewModel::onHistoryRequest,
                onReportClick = viewModel::onReportClick,
                onProfileClick = viewModel::onProfileClick,
            )
        }
    }
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                is Route.Splash -> {
                    NavEntry(key) {
                        SplashScreen()
                    }
                }

                is Route.Home -> {
                    NavEntry(key) {
                        HomeNavGraph(
                            modifier = Modifier,
                            bottomBar = bottomBar
                        )
                    }
                }

                is Route.History -> {
                    NavEntry(key) {
                        HistoryNavGraph(
                            modifier = Modifier,
                            bottomBar = bottomBar
                        )
                    }
                }

                is Route.Report -> {
                    NavEntry(key) {
                        ReportNavGraph(
                            modifier = Modifier,
                            bottomBar = bottomBar
                        )
                    }
                }


                is Route.Profile -> {
                    NavEntry(key) {
                        ProfileNavGraph(
                            modifier = Modifier,
                            bottomBar = bottomBar
                        )
                    }
                }

                else -> throw RuntimeException("Invalid root")
            }
        }
    )

}

