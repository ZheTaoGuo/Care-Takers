package com.example.caresync.model

import java.util.Date

data class Medication (
    val id: Long = 0,
    val name: String,
    val dosage: Int,
    val frequency: String,
    val startDate: Date,
    val endDate: Date,
    val medicationTaken: Boolean,
    val medicationTime: Date,
    val type: MedicationType = MedicationType.getDefault()
)