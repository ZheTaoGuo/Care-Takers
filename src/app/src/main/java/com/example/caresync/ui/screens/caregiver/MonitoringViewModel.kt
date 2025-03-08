package com.example.caresync.ui.screens.caregiver

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import kotlin.random.Random

class MonitoringViewModel : ViewModel() {
    var selectedView by mutableStateOf("Weekly")

    val adherenceStats = listOf(
        "To Do" to 28,
        "In Progress" to 12,
        "Done" to 6
    )

    val totalTasks = adherenceStats.sumOf { it.second }

    var moodStats by mutableStateOf(
        mapOf(
            "Happy" to 10,
            "Okay" to 5,
            "Bad" to 3
        )
    )
}
