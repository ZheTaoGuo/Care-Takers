package com.example.caresync.ui.screens.calendar

import androidx.lifecycle.ViewModel
import com.example.caresync.model.CalendarUiState
import com.example.caresync.utils.Clamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalendarViewModel : ViewModel() {
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
}