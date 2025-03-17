package com.example.caresync.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// TODO(RAYNER): This should all be converted to Flow<>
@Dao
interface MedicationDao {
    @Query("SELECT * FROM medications")
    fun getAllMedications(): List<Medication>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(medications: List<Medication>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedication(medication: Medication): Long



    // MedicationDosage-related functions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDosage(medicationDosage: MedicationDosage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDosages(dosages: List<MedicationDosage>)

    @Query("SELECT * FROM medicationDosages WHERE medicationId = :medicationId")
    fun getDosagesForMedication(medicationId: Long): List<MedicationDosage>

    @Query("DELETE FROM medicationDosages WHERE medicationId = :medicationId")
    fun deleteDosagesForMedication(medicationId: Long)

    @Query("SELECT * FROM medicationDosages")
    fun getAllDosages(): List<MedicationDosage>

    @Query("UPDATE medicationDosages SET isDosageTaken = :isTaken WHERE id = :dosageId")
    fun updateDosageStatus(dosageId: Long, isTaken: Boolean)

    @Query("SELECT * FROM medicationDosages WHERE scheduledDatetime BETWEEN :startOfDay AND :endOfDay")
    fun getDosagesForDate(startOfDay: Long, endOfDay: Long): Flow<List<MedicationDosage>>

    @Query("SELECT COUNT(*) FROM medications")
    fun getMedicationCount(): Int
}
