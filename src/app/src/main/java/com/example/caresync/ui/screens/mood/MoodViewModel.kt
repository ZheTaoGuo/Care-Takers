package com.example.caresync.ui.screens.mood

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class MoodViewModel : ViewModel() {
    // Simple UI state
    private val _uiState = MutableStateFlow(MoodUiState())
    val uiState: StateFlow<MoodUiState> = _uiState.asStateFlow()

    // Mood history stored in memory
    private val _moodEntries = MutableStateFlow<List<MoodEntry>>(emptyList())
    val moodEntries: StateFlow<List<MoodEntry>> = _moodEntries.asStateFlow()

    // UI Events
    fun onEvent(event: MoodEvent) {
        when (event) {
            is MoodEvent.ShowDialog -> {
                _uiState.update { it.copy(showDialog = true) }
            }
            is MoodEvent.DismissDialog -> {
                _uiState.update { it.copy(showDialog = false) }
            }
            is MoodEvent.SelectMood -> {
                // Create a new mood entry and add it to the list
                val newEntry = MoodEntry(
                    date = System.currentTimeMillis(),
                    mood = event.mood
                )

                _moodEntries.update { currentEntries ->
                    currentEntries + newEntry
                }

                _uiState.update { it.copy(
                    lastSelectedMood = event.mood,
                    showDialog = false
                )}
            }
            is MoodEvent.ClearHistory -> {
                _moodEntries.update { emptyList() }
            }
            is MoodEvent.ClearError -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }
}

// UI State
data class MoodUiState(
    val showDialog: Boolean = false,
    val lastSelectedMood: MoodState? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

// Events
sealed class MoodEvent {
    object ShowDialog : MoodEvent()
    object DismissDialog : MoodEvent()
    data class SelectMood(val mood: MoodState) : MoodEvent()
    object ClearHistory : MoodEvent()
    object ClearError : MoodEvent()
}