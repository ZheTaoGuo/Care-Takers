package com.example.caresync.model

import android.icu.util.Calendar
import com.example.caresync.utils.NotificationHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.attribute.DosFileAttributes
import java.util.Date

class MedicationRepository(private val medicationDao: MedicationDao) {
    // TODO(RAYNER): @ZheTao you need to be calling this when we add in new medications.
    suspend fun insertMedicationWithDosages(medication: Medication, notificationHandler: NotificationHandler) = withContext(Dispatchers.IO) {
        val medicationId = medicationDao.insertMedication(medication)
        val dosages = generateDosages(medication, medicationId)
        medicationDao.insertAllDosages(dosages)

        // TODO(RAYNER): Set up all notifications here. But will need to pass it in.
        for (dosage in dosages) {
            val scheduledNotifDate = dosage.scheduledDatetime
            val notifTitle = "Eat Your Meds!"
            val notifMessage = "${medication.name} to be taken now"
            notificationHandler.scheduleNotification(scheduledNotifDate, title = notifTitle, message = notifMessage)
        }
    }

    private fun generateDosages(medication: Medication, medicationId: Long): List<MedicationDosage> {
        val dosages = mutableListOf<MedicationDosage>()
        val calendar = Calendar.getInstance().apply { time = medication.startDate }
        var remainingDosage = medication.totalDosage

        while (remainingDosage > 0) {
            val dosesForTheDay = when (medication.frequency) {
                Frequency.ONCE -> listOf(12 to 0)  // 12:00 PM
                Frequency.TWICE -> listOf(8 to 0, 20 to 0)  // 8:00 AM, 8:00 PM
                Frequency.THRICE -> listOf(8 to 0, 14 to 0, 20 to 0)  // 8:00 AM, 2:00 PM, 8:00 PM
            }

            for ((hour, minute) in dosesForTheDay) {
                if (remainingDosage <= 0) break
                dosages.add(createDosage(medicationId, calendar.time, hour, minute))
                remainingDosage--
            }

            calendar.add(Calendar.DATE, 1)  // Move to next day
        }
        return dosages
    }

    private fun createDosage(medicationId: Long, date: Date, hour: Int, minute: Int): MedicationDosage {
        val scheduledTime = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }.time

        return MedicationDosage(
            medicationId = medicationId,
            isDosageTaken = false,
            scheduledDatetime = scheduledTime,
            isRescheduled = false
        )
    }

    fun findNextAvailableSlot(
        missedDosage: MedicationDosage,
        medication: Medication,
        existingDosages: List<MedicationDosage>
    ): Date {
        val calendar = Calendar.getInstance().apply { time = missedDosage.scheduledDatetime }
        val dosesForTheDay = when (medication.frequency) {
            Frequency.ONCE -> listOf(12 to 0)  // 12:00 PM
            Frequency.TWICE -> listOf(8 to 0, 20 to 0)  // 8:00 AM, 8:00 PM
            Frequency.THRICE -> listOf(8 to 0, 14 to 0, 20 to 0)  // 8:00 AM, 2:00 PM, 8:00 PM
        }

        while (true) {
            for ((hour, minute) in dosesForTheDay) {
                val candidateTime = Calendar.getInstance().apply {
                    time = calendar.time
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                }.time

                if (candidateTime.after(missedDosage.scheduledDatetime) &&
                    existingDosages.none { it.scheduledDatetime == candidateTime }) {
                    return candidateTime
                }
            }
            calendar.add(Calendar.DATE, 1)  // Move to the next day
        }
    }

    suspend fun postponeDosage(missedDosage: MedicationDosage, newDate: Date, notificationHandler: NotificationHandler) {
        // TODO(RAYNER): Value checking here

        // NOTE(RAYNER): Update old dosage to say postponed.
        medicationDao.updateDosageRescheduledStatus(missedDosage.id, true)

        // NOTE(RAYNER): Create and add in new dosage
        val newPostponedDosage = MedicationDosage(
            medicationId = missedDosage.medicationId,
            isDosageTaken = false,
            scheduledDatetime = newDate,
            isRescheduled = false
        )
        medicationDao.insertDosage(newPostponedDosage)

        val medication = medicationDao.getMedicationById(newPostponedDosage.medicationId)
        val medName = medication?.name ?: "NULL"

        // NOTE(RAYNER): Schedule notification for the new one
        val scheduledNotifDate = newPostponedDosage.scheduledDatetime
        val notifTitle = "Eat Your Meds!"
        val notifMessage = "$medName to be taken now"
        notificationHandler.scheduleNotification(scheduledNotifDate, title = notifTitle, message = notifMessage)
    }
}
