package com.example.caresync.datasource

import java.text.SimpleDateFormat
import java.util.Date
import com.example.caresync.model.Medication
import com.example.caresync.model.MedicationType

object MedicationDataSource {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
    val timeFormatter = SimpleDateFormat("HH:mm")
    val sampleMedications = listOf(
        Medication(1, "Aspirin", 81, "Daily", dateFormatter.parse("2025-03-31"), dateFormatter.parse("2025-03-31"), medicationTaken = true, medicationTime = timeFormatter.parse("08:00"), type= MedicationType.getDefault()),
        Medication(2, "Metformin", 500, "Daily", dateFormatter.parse("2025-05-31"), dateFormatter.parse("2025-07-31"), medicationTaken = true, medicationTime = timeFormatter.parse("13:00"), type= MedicationType.GEL),
        Medication(3, "Lisinopril", 800, "Twice daily", dateFormatter.parse("2025-12-01"), dateFormatter.parse("2025-12-31"), medicationTaken = true, medicationTime = timeFormatter.parse("12:00"), type= MedicationType.SYRUP),
        )
}