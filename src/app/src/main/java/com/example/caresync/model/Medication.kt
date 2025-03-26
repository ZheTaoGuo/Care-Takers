package com.example.caresync.model

import android.icu.util.Calendar
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.caresync.R
import java.util.Date

enum class Frequency(val stringResId: Int, val frequency: Int){
    ONCE(R.string.once, 1),
    TWICE(R.string.twice, 2),
    THRICE(R.string.thrice, 3)
};

// NOTE(RAYNER): Hey this should be maybe in the UI class instead? So don't confuse with models.
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

@Entity(
    tableName = "medicationDosages",
    foreignKeys = [ForeignKey(
        entity = Medication::class,
        parentColumns = ["id"],
        childColumns = ["medicationId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["medicationId"])] // Indexing for performance
)
data class MedicationDosage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val medicationId: Long,  // References Medication
    val isDosageTaken: Boolean,
    val scheduledDatetime: Date,
    val isRescheduled: Boolean
) {
    val hour: Int
        get() = Calendar.getInstance().apply { time = scheduledDatetime }.get(Calendar.HOUR_OF_DAY)

    val minute: Int
        get() = Calendar.getInstance().apply { time = scheduledDatetime }.get(Calendar.MINUTE)
}
