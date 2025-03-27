package com.example.caresync.model

import java.util.Date

data class CalendarUiState (
    val startHour: Int = 6,
    val endHour: Int = 23,
    val minuteHeight: Float = 1f,
    val currentDate: Date
)