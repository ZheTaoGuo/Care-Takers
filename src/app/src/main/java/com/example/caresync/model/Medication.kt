package com.example.caresync.model

import android.icu.util.Calendar
import java.util.Date

data class Medication (
    val id: Long = 0,
    val name: String,
    val dosage: Int,
    val frequency: String, // NOTE(RAYNER): This should REALLY be an enum. Also needs to factor in medications with before/after meals.
                           // NOTE(RAYNER): When this is changed, will have to affect MedicationDosage generation as well!!
    val startDate: Date,
    val endDate: Date,
    val medicationTaken: Boolean,
    val medicationTime: Date,
    val type: MedicationType = MedicationType.getDefault()
)

data class MedicationDosage(
    val id: Long = 0,
    val srcMedication: Medication,
    val isDosageTaken: Boolean,
    val scheduledDatetime: Date,
    val isRescheduled: Boolean,
) {
    val hour: Int
        get() = Calendar.getInstance().apply { time = scheduledDatetime }.get(Calendar.HOUR_OF_DAY)

    val minute: Int
        get() = Calendar.getInstance().apply { time = scheduledDatetime }.get(Calendar.MINUTE)
}