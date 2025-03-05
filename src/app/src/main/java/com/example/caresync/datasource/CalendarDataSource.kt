package com.example.caresync.datasource

import com.example.caresync.model.CalendarEntry

object CalendarDataSource {
    val sampleEvents = listOf(
        CalendarEntry("Meeting", 9, 30, "Once a day", true),
        CalendarEntry("Workout", 12, 0, "Blah blah blah", true),
        CalendarEntry("Lunch", 13, 45, "Blah blah blah"),
        CalendarEntry("Project Work", 15, 15, "Blah blah blah")
    )
}