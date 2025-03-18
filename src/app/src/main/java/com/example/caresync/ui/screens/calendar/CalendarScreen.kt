package com.example.caresync.ui.screens.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.caresync.BottomNavItem
import com.example.caresync.CareSyncApp
import com.example.caresync.datasource.MedicationDataSource
import java.util.Calendar
import java.util.Date

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
    )
}


// TODO(RAYNER): Create fake view model and DAO since factory in preview doesn't work.
@Preview
@Composable
fun CalendarScreenPreview() {
    CareSyncApp({}, BottomNavItem.Calendar.route)
}
