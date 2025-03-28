package com.example.caresync.datasource

import com.example.caresync.model.Frequency
import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import com.example.caresync.model.Medication
import com.example.caresync.model.MedicationDosage
import com.example.caresync.model.MedicationType
import com.example.caresync.utils.getTodayWithSpecifiedTime
import java.util.UUID

object MedicationDataSource {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
    val sampleMedications = listOf(
        Medication(1, "Aspirin", Frequency.ONCE, 2, 10, dateFormatter.parse("2025-03-31"), type=MedicationType.TABLET),
        Medication(2, "Prospan Cough Syrup", Frequency.TWICE, 15,100, dateFormatter.parse("2025-03-31"), type= MedicationType.SYRUP),
        Medication(3, "Lisinopril", Frequency.THRICE,2, 14, dateFormatter.parse("2025-03-18"), type= MedicationType.TABLET),
        Medication(4, "Amoxicillin", Frequency.THRICE, 1, 30, dateFormatter.parse("2025-03-10"), type= MedicationType.CAPSULE),
        Medication(5, "Vitamin D Drops", Frequency.THRICE, 2, 30, dateFormatter.parse("2025-03-05"), type= MedicationType.DROPS),
        Medication(6, "Salbutamol Inhaler", Frequency.THRICE, 1, 200, dateFormatter.parse("2025-03-01"), type= MedicationType.SPRAY)
        )
    val sampleMedicationDosages = listOf(
        MedicationDosage( 1, medicationId = 1, isDosageTaken = false, scheduledDatetime = getTodayWithSpecifiedTime(8, 30), isRescheduled = false),
        MedicationDosage( 2, medicationId = 1, isDosageTaken = false, scheduledDatetime = getTodayWithSpecifiedTime(13, 0), isRescheduled = false),
        MedicationDosage( 3, medicationId = 2, isDosageTaken = false, scheduledDatetime = getTodayWithSpecifiedTime(13, 0), isRescheduled = false),
        MedicationDosage( 4, medicationId = 3, isDosageTaken = false, scheduledDatetime = getTodayWithSpecifiedTime(20, 45), isRescheduled = false),
        MedicationDosage( 5, medicationId = 3, isDosageTaken = false, scheduledDatetime = getTodayWithSpecifiedTime(20, 45), isRescheduled = false),
        MedicationDosage( 6, medicationId = 3, isDosageTaken = false, scheduledDatetime = getTodayWithSpecifiedTime(20, 45), isRescheduled = false),
    )

    // Function to get medication name by ID
    fun getMedicationNameById(medicationId: Long): String? {
        return sampleMedications.find { it.id == medicationId }?.name
    }
}