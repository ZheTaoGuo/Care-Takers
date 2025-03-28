package com.example.caresync.ui.screens.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.caresync.ui.screens.BottomNavItem
import com.example.caresync.ui.screens.CareSyncPatientAppScreens

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = viewModel(factory = CalendarViewModel.factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val dosagesForDay by viewModel.getDosagesForDate(uiState.currentDate).collectAsState(initial = emptyList())
    val isSheetVisible by viewModel.isSheetVisible.collectAsState()
    DayCalendarView(
        dosagesForDay = dosagesForDay,
        curDate = uiState.currentDate,
        minuteHeight = uiState.minuteHeight,
        startHour = uiState.startHour,
        endHour = uiState.endHour,
        getMedicationName = { medicationId ->
            viewModel.getMedicationName(medicationId) // Call your ViewModel's function to get the name
        },
        updateIsDosageTaken = { dosageId, isChecked ->
            viewModel.updateDosageTaken(dosageId, isChecked)
        },
        navNextDay = { viewModel.navigateToNextDay() },
        navPrevDay = { viewModel.navigateToPrevDay() },
        isSheetVisible = isSheetVisible,
        onBtmSheetShow = { viewModel.showBtmSheet() },
        onBtmSheetDismiss = { viewModel.dismissBtmSheet() },
        setDosageToEdit = { newDosageToEdit, name -> viewModel.setDosageToEdit(newDosageToEdit, name) },
        dosageToEdit = uiState.dosageToEdit,
        dosageToEditName = uiState.dosageToEditName,
        dosageProposedPostponeDate = uiState.dosageProposedPostponedDate,
        computeNextDosageDate = { dosage -> viewModel.computeDosagePostponementDate(dosage) },
        confirmPostponeDosage = { viewModel.postponeMissedDosage() },
    )
}


// TODO(RAYNER): Create fake view model and DAO since factory in preview doesn't work.
@Preview
@Composable
fun CalendarScreenPreview() {
    CareSyncPatientAppScreens({}, BottomNavItem.Calendar.route)
}
