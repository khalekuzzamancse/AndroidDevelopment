package com.uitest.feature.history

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import paymentsave.terminalapp.core.language.Logger
import java.lang.System.currentTimeMillis
import kotlin.collections.plus

interface HistoryController{
    val filterController:FilterViewController
    val dateRangeController:DateRangeController
}
/**
 * - Each Division has its own Districts
 * - Each District has its own Sub-Districts
 */
class HistoryViewModel : HistoryController, ViewModel(), FilterViewController {

    val tag = "HistoryScreeTestViewModel"
    val districtMap = mapOf(
        "Rangpur" to listOf("Rangpur", "Kurigram", "Nilphamari", "Bogra"),
        "Dhaka" to listOf("Dhaka", "Gazipur", "Narayanganj"),
        "Rajshahi" to listOf("Rajshahi", "Naogaon", "Pabna"),
        "Khulna" to listOf("Khulna", "Bagerhat", "Satkhira"),
        "Chittagong" to listOf("Chittagong", "Cox’s Bazar", "Rangamati"),
        "Sylhet" to listOf("Sylhet", "Moulvibazar", "Habiganj")
    )

    val subDistrictMap = mapOf(
        "Rangpur" to listOf("Kaunia", "Pirganj", "Mithapukur"),
        "Bogra" to listOf("Sadar", "Sherpur", "Shibganj"),
        "Dhaka" to listOf("Dhanmondi", "Mirpur", "Uttara"),
        "Khulna" to listOf("Khalishpur", "Sonadanga"),
    )


    val divisionList = districtMap.keys.toList()



    val divisionOptions = FilterBottomSheetViewOption(
        groupName = "Division",
        options = divisionList
    )

    // --- Build filter groups for UI tests ---
    val initialOptions = listOf(
        divisionOptions
    )

    // --- Controllers ---
    override val filterController = this
    override val dateRangeController = DateRangeController(
        startDate = currentTimeMillis(),
        startTime = 0 to 30,
        endDate = currentTimeMillis(),
        endTime = 12 to 40,
    )

    fun onDivisionSelected(name: String) {
        val options = listOf(
            divisionOptions.copy(selected = name),
            FilterBottomSheetViewOption(
                groupName = "District",
                options = districtMap[name] ?: listOf("All")
            )
        )
        filterController.update(options)
    }

    fun onDistrictChange(name: String) {
        //Keep the Division, District group unchanged with selection
        val existing = filterController.groups.value + FilterBottomSheetViewOption(
            groupName = "Sub-District",
            options = subDistrictMap[name] ?: listOf("All")
        )
        filterController.update(existing)
    }

    private var undoOptions = initialOptions

    // Backing state for options
    override val groups = MutableStateFlow(initialOptions)
    override val selectedByGroup = MutableStateFlow<Map<String, String>>(emptyMap())
    override fun update(groups: List<FilterBottomSheetViewOption>) {
        this.groups.update { groups }
    }

    override fun selectGroup(groupName: String, option: String) {
        Logger.on(tag, "selected:", "($groupName,$option)")
        if (groupName == "Division") onDivisionSelected(option)
        if (groupName == "District") onDistrictChange(option)
        groups.update { groups ->
            groups.map { group ->
                if (group.groupName == groupName)
                    group.copy(selected = option)
                else
                    group
            }
        }
        selectedByGroup.update { groups ->
            groups.toMutableMap().apply {
                put(groupName, option)
            }
        }
    }

    override fun reset() {
        groups.update { initialOptions }
    }

    override fun saveUndoOption() {
        undoOptions = groups.value
    }

    override fun undo() {
        groups.update { undoOptions }
    }
}

