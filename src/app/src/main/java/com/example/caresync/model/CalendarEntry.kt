package com.example.caresync.model

data class CalendarEntry (
    val title: String,
    val hour: Int,
    val min: Int,
    val details: String,
    val completed: Boolean = false,
)
