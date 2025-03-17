package com.example.caresync.ui.screens.calendar

import android.text.format.DateUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.caresync.CareSyncApplication
import com.example.caresync.model.CalendarUiState
import com.example.caresync.model.Medication
import com.example.caresync.model.MedicationDao
import com.example.caresync.model.MedicationDosage
import com.example.caresync.utils.Clamp
import com.example.caresync.utils.getDayBounds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class CalendarViewModel(private val medicationDao: MedicationDao) : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    fun updateStartHour(startHour: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                startHour = startHour
            )
        }
    }

    fun updateEndHour(endHour: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                endHour = endHour
            )
        }
    }

    fun updateMinuteHeight(minuteHeight: Float) {
        val clampedMinuteHeight = Clamp(minuteHeight, 0.5f, 3.0f)
        _uiState.update { currentState ->
            currentState.copy(
                minuteHeight = clampedMinuteHeight
            )
        }
    }

    fun getDosagesForDate(curDate: Date): Flow<List<MedicationDosage>> {
        val (start, end) = getDayBounds(Date())
        return medicationDao.getDosagesForDate(start, end)
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CareSyncApplication)
                CalendarViewModel(application.medicationDatabase.medicationDao())
            }
        }
    }
}