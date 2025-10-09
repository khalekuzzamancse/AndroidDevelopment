package com.uitest

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.uitest.coretest.Sleeper
import com.uitest.coretest.waitForLabelOnTextView
import com.uitest.feature.history.HistoryViewModel
import com.uitest.feature.history._HistoryScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class HistoryScreenTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private val viewModel = HistoryViewModel()
    private val tag="HistoryScreenTest"
    @Before
    fun init() {
        rule.setContent {
            _HistoryScreen(
                modifier = Modifier,
                bottomBar = {},
                viewModel = viewModel
            )
        }
        rule.waitForIdle()
    }

    @Test
    fun filter_hierarchy_selection() {
        val division = viewModel.divisionList[Random.nextInt(0, viewModel.divisionList.lastIndex)]
        rule.clickDivision(division)
        Sleeper.sleep3sec()
        val districtList = viewModel.districtMap[division] ?: listOf("All")
        val district = districtList[Random.nextInt(0, districtList.lastIndex)]
        rule.clickDistrict(district)
        Sleeper.sleep3sec()
        val subDistrictList = viewModel.subDistrictMap[district]
        val subDistrict = if (subDistrictList == null) "All" else
            subDistrictList[Random.nextInt(0, subDistrictList.lastIndex)]
        rule.clickSubDistrict(subDistrict)
        val expected = mapOf(
            "Division" to division,
            "District" to district,
            "Sub-District" to subDistrict
        )
        rule.waitForLabelOnTextView("$expected", substring = true)
        Sleeper.sleep3sec()

    }


}

fun ComposeContentTestRule.clickSubDistrict(label: String) = clickGroup(label, "Sub-District")
fun ComposeContentTestRule.clickDistrict(label: String) = clickGroup(label, "District")
fun ComposeContentTestRule.clickDivision(label: String) = clickGroup(label, "Division")
fun ComposeContentTestRule.clickGroup(label: String, group: String) {
    onNode(hasText(label) and hasContentDescription(group) and hasClickAction()).performClick()
    waitForIdle()
    Thread.sleep(500)//to show the keypress
}

