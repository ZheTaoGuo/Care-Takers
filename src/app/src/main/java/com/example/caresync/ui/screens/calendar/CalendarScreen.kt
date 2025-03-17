package com.example.caresync.ui.screens.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun CalendarScreen() {
    val viewModel: CalendarViewModel = viewModel(factory = CalendarViewModel.factory)
    val currentDate: Date = Calendar.getInstance().time
    DayCalendarView(viewModel = viewModel)
}

// TODO(RAYNER): Create fake view model and DAO since factory in preview doesn't work.
@Preview
@Composable
fun CalendarScreenPreview() {
    CareSyncApp({}, BottomNavItem.Calendar.route)
}
