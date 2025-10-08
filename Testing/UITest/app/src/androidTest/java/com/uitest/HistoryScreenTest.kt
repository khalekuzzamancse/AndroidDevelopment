package com.uitest

import android.R.attr.name
import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uitest.coretest.Sleeper
import com.uitest.feature.history.DateRangeController
import com.uitest.feature.history.FilterBottomSheetViewOption
import com.uitest.feature.history.FilterViewController
import com.uitest.feature.history.HistoryController
import com.uitest.feature.history._HistoryScreen
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import paymentsave.terminalapp.core.language.Logger
import java.lang.System.currentTimeMillis

class HistoryScreenTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private val viewModel=HistoryScreeTestViewModel()

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
    fun division_select_test(){
        rule.clickDivision("Rangpur")
        Sleeper.sleep3sec()
        rule.clickDistrict("Bogra")
        Sleeper.sleep3sec()
    }


}
fun ComposeContentTestRule.clickSubDistrict(label: String)=clickGroup(label,"Sub-District")
fun ComposeContentTestRule.clickDistrict(label: String)=clickGroup(label,"District")
fun ComposeContentTestRule.clickDivision(label: String)=clickGroup(label,"Division")
fun ComposeContentTestRule.clickGroup(label: String,group:String){
    onNode(hasText(label) and hasContentDescription(group) and  hasClickAction()).performClick()
    waitForIdle()
    Thread.sleep(500)//to show the keypress
}

/**
 * Test ViewModel to make hierarchical filter testing easier.
 * - Each Division has its own Districts
 * - Each District has its own Sub-Districts
 */
private class HistoryScreeTestViewModel : HistoryController, ViewModel() {

    val tag="HistoryScreeTestViewModel"
    val subDistrictMap = mapOf(
        "Rangpur" to listOf("Kaunia", "Pirganj", "Mithapukur"),
        "Bogra" to listOf("Sadar", "Sherpur", "Shibganj"),
        "Dhaka" to listOf("Dhanmondi", "Mirpur", "Uttara"),
        "Khulna" to listOf("Khalishpur", "Sonadanga"),
    )

    val districtMap = mapOf(
        "Rangpur" to listOf("Rangpur", "Kurigram", "Nilphamari", "Bogra"),
        "Dhaka" to listOf("Dhaka", "Gazipur", "Narayanganj"),
        "Rajshahi" to listOf("Rajshahi", "Naogaon", "Pabna"),
        "Khulna" to listOf("Khulna", "Bagerhat", "Satkhira"),
        "Chittagong" to listOf("Chittagong", "Cox’s Bazar", "Rangamati"),
        "Sylhet" to listOf("Sylhet", "Moulvibazar", "Habiganj")
    )

    val divisionList = districtMap.keys.toList()

    val hierarchyMap: Map<String, Map<String, List<String>>> = buildMap {
        districtMap.forEach { (division, districts) ->
            put(
                division,
                districts.associateWith { district ->
                    subDistrictMap[district] ?: listOf("All")
                }
            )
        }
    }

    fun selectDivision(name: String){
        filterController.selectGroup("District",name)

    }
    init {
        viewModelScope.launch{
            filterController.selectedByGroup.collect {map->
                Logger.off(tag,"selected:","$map")
              map.forEach { (key,value)->
                  Logger.on(tag,"ForEach:","($key,$value)")
                  if(key=="Division") onDivisionSelected(value)
                  if(key=="District") onDistrictChange(value)
                }

            }
        }

    }

    val divisionOptions= FilterBottomSheetViewOption(
        groupName = "Division",
        options = divisionList
    )

    // --- Build filter groups for UI tests ---
    val filterOptionsList = listOf(divisionOptions)

    // --- Controllers ---
    override val filterController = FilterViewController.create(filterOptionsList)
    override val dateRangeController = DateRangeController(
        startDate = currentTimeMillis(),
        startTime = 0 to 30,
        endDate = currentTimeMillis(),
        endTime = 12 to 40,
    )
    fun onDivisionSelected(name: String){
        val options=listOf(
            divisionOptions.copy(selected = name),
            FilterBottomSheetViewOption(
            groupName = "District",
            options =districtMap[name]?:listOf("All"),
                selected = name
        ))
        filterController.update(options)
    }
    fun onDistrictChange(name: String ){
        //Keep the Division, District group unchanged with selection
        val existing= filterController.groups.value+FilterBottomSheetViewOption(
            groupName = "Sub-District",
            options =subDistrictMap[name]?:listOf("All"))
        filterController.update(existing)

    }
}

