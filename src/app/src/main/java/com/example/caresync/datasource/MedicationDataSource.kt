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

        for (med in sampleMedications) {
            val calendar = Calendar.getInstance().apply { time = med.startDate }
            var remainingDosage = med.totalDosage

            while (remainingDosage > 0) {
                val dosesForTheDay = when (med.frequency) {
                    Frequency.ONCE -> listOf(12 to 0)  // 12:00 PM
                    Frequency.TWICE -> listOf(8 to 0, 20 to 0)  // 8:00 AM, 8:00 PM
                    Frequency.THRICE -> listOf(8 to 0, 14 to 0, 20 to 0)  // 8:00 AM, 2:00 PM, 8:00 PM
                }

                for ((hour, minute) in dosesForTheDay) {
                    if (remainingDosage <= 0) break
                    dosages.add(createDosage(med, calendar.time, hour, minute))
                    remainingDosage--
                }

                calendar.add(Calendar.DATE, 1)  // Move to next day
            }
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