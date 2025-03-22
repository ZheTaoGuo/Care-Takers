package com.example.caresync.model

import com.example.caresync.ui.screens.mood.MoodState


// UI State
data class MoodUiState(
    val showDialog: Boolean = false,
    val lastSelectedMood: MoodState? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

// UI Events
sealed class MoodEvent {
    data object ShowDialog : MoodEvent()
    data object DismissDialog : MoodEvent()
    data class SelectMood(val mood: MoodState) : MoodEvent()
    data object ClearError : MoodEvent()
    data object ClearHistory : MoodEvent()
}
