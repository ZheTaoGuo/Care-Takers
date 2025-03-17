package com.example.caresync.model

import android.icu.util.Calendar
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class Frequency{
    ONCE,
    TWICE,
    THRICE
};

data class MedicationUIState(
    val medications: List<Medication> = emptyList(),
    val selectedMedication: Medication? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@Entity(tableName = "medications")
data class Medication (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val frequency: Frequency,
    val amtPerDosage: Int,
    val totalDosage: Int,
    val startDate: Date,
    val type: MedicationType
) {
    fun getMeasurementType(): String {
        return type.displayName
    }
}

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