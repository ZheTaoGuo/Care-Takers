package com.example.caresync.datasource

import com.example.caresync.model.Frequency
import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import com.example.caresync.model.Medication
import com.example.caresync.model.MedicationDosage
import com.example.caresync.model.MedicationType
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


    val generatedDosages: List<MedicationDosage> = generateDosages()

    private fun generateDosages(): List<MedicationDosage> {
        val dosages = mutableListOf<MedicationDosage>()
        val calendar = Calendar.getInstance()

        for (med in sampleMedications) {
            calendar.time = med.startDate

//            while (!calendar.time.after(med.endDate)) {
//                when (med.frequency) {
//                    // TODO(RAYNER): Will need to beef up this, once Zhe Tao changes frequency to an enum.
//                    "Daily" -> dosages.add(createDosage(med, calendar.time, 12, 0)) // Default to 12:00 PM
//                    "Twice daily" -> {
//                        dosages.add(createDosage(med, calendar.time, 8, 0)) // Morning dose at 8:00 AM
//                        dosages.add(createDosage(med, calendar.time, 20, 0)) // Evening dose at 8:00 PM
//                    }
//                }
//                calendar.add(Calendar.DATE, 1) // Move to next day
//            }
        }
        return dosages
    }

    private fun createDosage(med: Medication, date: Date, hour: Int, minute: Int): MedicationDosage {
        val scheduledTime = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }.time

        return MedicationDosage(
            id = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE, // Generate unique ID
            srcMedication = med,
            isDosageTaken = false, // Default to false
            scheduledDatetime = scheduledTime,
            isRescheduled = false
        )
    }

    fun getDosagesForDate(targetDate: Date): List<MedicationDosage> {
        val targetCalendar = Calendar.getInstance().apply {
            time = targetDate
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return generatedDosages.filter {
            val dosageCalendar = Calendar.getInstance().apply {
                time = it.scheduledDatetime
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            dosageCalendar.time == targetCalendar.time
        }
    }
}