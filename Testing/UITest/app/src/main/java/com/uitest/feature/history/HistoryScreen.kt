@file:Suppress("ComposableNaming")

package com.uitest.feature.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uitest.core.ui.ButtonView
import com.uitest.core.ui.DividerHorizontal
import com.uitest.core.ui.SpacerFillAvailable
import com.uitest.core.ui.SpacerHorizontal
import com.uitest.core.ui.SpacerVertical
import com.uitest.core.ui.VoidComposable
import com.uitest.core.ui.contentColor
import com.uitest.feature._core.ui.ColorFactory
import com.uitest.feature._core.ui.ScreenStrategy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import paymentsave.terminalapp.core.language.DateTimeUtils
import java.lang.System.currentTimeMillis

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    bottomBar: VoidComposable,
) {
    val viewModel = remember { HistoryViewModel() }
    _HistoryScreen(
        modifier = modifier,
        bottomBar = bottomBar,
        viewModel = viewModel
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun _HistoryScreen(
    modifier: Modifier = Modifier,
    bottomBar: VoidComposable,
    viewModel: HistoryController,
) {
    ScreenStrategy(
        modifier = modifier.semantics {
            contentDescription = "History Screen"
        },
        bottomBar = bottomBar,
        fab = {},
    ) { modifier ->

        val filtered=viewModel.filterController.selectedByGroup.collectAsState().value
        Column(
            modifier = modifier.align(Alignment.Center)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonView(
                    label = "Apply"
                ) {

                }
                SpacerHorizontal(8)
                Text("Applied:${filtered}")
            }

            FilterView(
                modifier = Modifier,
                controller = viewModel.filterController,
                onResetRequest = {}
            )
            SpacerVertical(16)
            DateRangePicker(
                modifier = Modifier,
                controller = viewModel.dateRangeController,
                onChange = {}
            )
        }

    }

}


@Composable

fun FilterView(
    modifier: Modifier = Modifier,
    controller: FilterViewController,
    onResetRequest: () -> Unit
) {
    val contentPadding = 8
    val groups = controller.groups.collectAsState().value
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Filter",
                fontSize = 22.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(start = 16.dp)
            )
            SpacerFillAvailable()
            TextButton(
                onClick = onResetRequest,
            ) {
                Text(
                    text = "Reset All",
                    color = ColorFactory.colors.primary
                )
            }

        }
        SpacerVertical(8)
        DividerHorizontal()
        SpacerVertical(8)
        groups.forEachIndexed { index, group ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = group.groupName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(start = 16.dp)
                )
                SpacerVertical(16)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    SpacerHorizontal(contentPadding)
                    group.options.forEach { option ->
                        val selected = group.selected == option
                        val containerColor = if (selected) ColorFactory.colors.primaryLight else
                            ColorFactory.colors.background
                        Surface(
                            modifier = Modifier
                                .clickable {
                                    controller.selectGroup(group.groupName, option)
                                }
                                .semantics {
                                    contentDescription = group.groupName
                                },
                            shape = RoundedCornerShape(16.dp),
                            shadowElevation = 4.dp,
                            color = containerColor
                        ) {
                            Text(
                                text = option,
                                modifier = Modifier.padding(8.dp),
                                color = containerColor.contentColor()
                            )
                        }

                    }
                }
            }
            if (index != groups.lastIndex)
                SpacerVertical(16)
        }
    }


}

interface FilterViewController {
    val groups: StateFlow<List<FilterBottomSheetViewOption>>
    fun selectGroup(groupName: String, option: String)
    val selectedByGroup: StateFlow<Map<String, String>>
    fun update(groups: List<FilterBottomSheetViewOption>)
    fun reset()
    fun saveUndoOption()
    fun undo()

    companion object {
        fun create(initialOptions: List<FilterBottomSheetViewOption>): FilterViewController =
            DefaultFilterViewController(initialOptions)
    }

}

data class FilterBottomSheetViewOption(
    val groupName: String,
    val options: List<String>,
    val selected: String? = null
)

class DefaultFilterViewController(
    private val initialOptions: List<FilterBottomSheetViewOption>
) : FilterViewController {
    private var undoOptions = initialOptions

    // Backing state for options
    override val groups = MutableStateFlow(initialOptions)
    override val selectedByGroup = MutableStateFlow<Map<String, String>>(emptyMap())
    override fun update(groups: List<FilterBottomSheetViewOption>) {
        this.groups.update { groups }
    }

    override fun selectGroup(groupName: String, option: String) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker(
    modifier: Modifier = Modifier,
    controller: DateRangeController,
    onChange: () -> Unit,
) {

    Column(
        modifier = modifier
            .background(ColorFactory.colors.pageColor, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text("Date range")
        Spacer(Modifier.height(16.dp))

        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text("From :", style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(4.dp))
                DatePickerView(
                    initial = controller.startDate,
                    onDateSelected = {
                        it?.let { date ->
                            controller.onStartDateChanged(date)
                        }
                        onChange()
                    }
                )

                Spacer(Modifier.height(8.dp))
                TimePickerView(
                    initial = controller.startTime,
                    onPicked = {
                        it?.let { time ->
                            controller.onStartTimeChanged(time)
                        }
                        onChange()
                    }
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("To :", style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(4.dp))
                DatePickerView(
                    initial = controller.endDate,
                    onDateSelected = {
                        it?.let { date ->
                            controller.onEndDateChanged(date)
                        }
                        onChange()
                    }
                )

                Spacer(Modifier.height(8.dp))

                TimePickerView(
                    initial = controller.endTime,
                    onPicked = {
                        it?.let { time ->
                            controller.onEndTimeChanged(time)
                        }
                        onChange()
                    }

                )
            }
        }
    }

}

class DateRangeController(
    startDate: Long = currentTimeMillis(),
    startTime: Pair<Int, Int> = 10 to 30,
    endDate: Long = currentTimeMillis(),
    endTime: Pair<Int, Int> = 12 to 40,
) {
    // private backing fields
    private var _startDate = startDate
    private var _startTime = startTime
    private var _endDate = endDate
    private var _endTime = endTime

    // publicly visible read-only properties
    val startDate: Long get() = _startDate
    val startTime: Pair<Int, Int> get() = _startTime
    val endDate: Long get() = _endDate
    val endTime: Pair<Int, Int> get() = _endTime

    // controlled mutators
    fun onStartDateChanged(value: Long) {
        _startDate = value
    }

    fun onStartTimeChanged(value: Pair<Int, Int>) {
        _startTime = value
    }

    fun onEndDateChanged(value: Long) {
        _endDate = value
    }

    fun onEndTimeChanged(value: Pair<Int, Int>) {
        _endTime = value
    }


    fun copy(
        startDate: Long = this.startDate,
        startTime: Pair<Int, Int> = this.startTime,
        endDate: Long = this.endDate,
        endTime: Pair<Int, Int> = this.endTime
    ): DateRangeController {
        return DateRangeController(
            startDate = startDate,
            startTime = startTime,
            endDate = endDate,
            endTime = endTime
        )
    }

}

@ExperimentalMaterial3Api
@Composable
fun TimePickerView(
    modifier: Modifier = Modifier,
    initial: Pair<Int, Int>,
    onPicked: (Pair<Int, Int>?) -> Unit,
) {
    var time by rememberSaveable(initial) {
        mutableStateOf(DateTimeUtils.toTimeString(initial))
    }
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val state = rememberTimePickerState(
        initialHour = initial.first,
        initialMinute = initial.second,
        is24Hour = true,
    )
    OutlinedTextField(
        modifier = modifier,
        value = time,
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            Icon(
                Icons.Default.AccessTime,
                contentDescription = "Pick time",
                Modifier.clickable { showDialog = true }
            )
        },
    )
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                onPicked(null)
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    onPicked(null)
                }) {
                    Text("Dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    time = DateTimeUtils.toTimeString(state.hour to state.minute)
                    onPicked(state.hour to state.minute)
                    showDialog = false
                }) {
                    Text("OK")
                }
            },
            text = {
                TimePicker(
                    state = state,
                )
            }
        )
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView(
    modifier: Modifier = Modifier,
    initial: Long,
    onDateSelected: (Long?) -> Unit,
) {
    val today = remember { DateTimeUtils.getDateToday() }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initial,
    )
    var time by rememberSaveable(initial) { mutableLongStateOf(initial) }
    var showDialog by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier,
        value = DateTimeUtils.formatDate(time, separator = "/"),
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            Icon(
                Icons.Default.CalendarToday,
                contentDescription = "Pick date",
                Modifier.clickable { showDialog = true }
            )
        },

        )
    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = {
                onDateSelected(null)
                showDialog = false
            },
            confirmButton = {
                TextButton(onClick = {
                    time = datePickerState.selectedDateMillis ?: initial
                    onDateSelected(time)
                    showDialog = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDateSelected(null)
                    showDialog = false
                }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

}