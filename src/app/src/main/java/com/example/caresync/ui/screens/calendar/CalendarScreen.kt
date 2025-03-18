package com.example.caresync.ui.screens.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.caresync.ui.screens.BottomNavItem
import com.example.caresync.ui.screens.CareSyncPatientAppScreens
import java.util.Calendar

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = viewModel(factory = CalendarViewModel.factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val dosagesForDay by viewModel.getDosagesForDate(Calendar.getInstance().time).collectAsState(initial = emptyList())
    DayCalendarView(
        dosagesForDay = dosagesForDay,
        minuteHeight = uiState.minuteHeight,
        startHour = uiState.startHour,
        endHour = uiState.endHour,
        getMedicationName = { medicationId ->
            viewModel.getMedicationName(medicationId) // Call your ViewModel's function to get the name
        }
    )
}


// TODO(RAYNER): Create fake view model and DAO since factory in preview doesn't work.
@Preview
@Composable
fun CalendarScreenPreview() {
    CareSyncPatientAppScreens({}, BottomNavItem.Calendar.route)
}
