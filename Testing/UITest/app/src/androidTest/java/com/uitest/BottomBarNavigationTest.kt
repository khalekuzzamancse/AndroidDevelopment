package com.uitest
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.uitest.coretest.Semantic
import com.uitest.coretest.Sleeper
import com.uitest.coretest.backPress
import com.uitest.coretest.navigateTo
import com.uitest.coretest.waitFoScreen
import com.uitest.feature._navigation.NavigationRoot
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BottomBarNavigationTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun init() {
        rule.setContent { NavigationRoot() }
        rule.waitForIdle()
        Thread.sleep(3_000)
    }


    @Test
    fun navigation() {
        rule.navigateTo(navItem = Semantic.NAV_ITEM_HOME, screen = Semantic.HOME_SCREEN)
        rule.navigateTo(navItem = Semantic.NAV_ITEM_HISTORY, screen = Semantic.HISTORY_SCREEN)
        rule.navigateTo(navItem = Semantic.NAV_ITEM_REPORT, screen = Semantic.REPORT_SCREEN)
        rule.navigateTo(navItem = Semantic.NAV_ITEM_PROFILE, screen = Semantic.PROFILE_SCREEN)
        rule.backPress()
        rule.waitFoScreen(Semantic.HOME_SCREEN)
        Sleeper.sleep3sec()
    }

    @Test
    fun single_back_press() {
        rule.backPress()
        rule.waitFoScreen(Semantic.HOME_SCREEN)
        rule.waitUntil(timeoutMillis = 5_000) {
            var finished = false
            rule.activityRule.scenario.onActivity { finished = it.isFinishing }
            !finished
        }

    }

    @Test
    fun double_back_press() {
        rule.backPress()
        rule.waitFoScreen(Semantic.HOME_SCREEN)
        rule.backPress()
        rule.waitUntil(timeoutMillis = 5_000) {
            var finished = false
            rule.activityRule.scenario.onActivity { finished = it.isFinishing }
            finished
        }

    }


}