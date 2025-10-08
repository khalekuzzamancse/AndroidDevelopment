package com.uitest.feature.history

import java.lang.System.currentTimeMillis
interface HistoryController{
    val filterController:FilterViewController
    val dateRangeController:DateRangeController
}
class HistoryScreenViewModel: HistoryController {
    override val filterController= FilterViewController.create(
            listOf(
                FilterBottomSheetViewOption(
                    groupName = "Division",
                    options = listOf("Rangpur", "Dhaka", "Rajshahi", "Khulna", "Chittagong", "Sylhet"),
                ),
                FilterBottomSheetViewOption(
                    groupName = "District",
                    options = listOf("Rangpur", "Bogra")
                ),
                FilterBottomSheetViewOption(
                    groupName = "Sub District",
                    options = listOf("All")
                ),

                )
        )
    override val dateRangeController =
        DateRangeController(
            startDate = currentTimeMillis(),
            startTime = 0 to 30,
            endDate = currentTimeMillis(),
            endTime = 12 to 40,
        )
}