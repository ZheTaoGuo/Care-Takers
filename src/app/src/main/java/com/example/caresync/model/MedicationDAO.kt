package com.example.caresync.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {
    @Query("SELECT * FROM medications")
    fun getAllMedications(): Flow<List<Medication>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(medications: List<Medication>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: Medication): Long

    @Query("SELECT * FROM medications WHERE id = :medicationId LIMIT 1")
    suspend fun getMedicationById(medicationId: Long): Medication?




    // MedicationDosage-related functions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDosage(medicationDosage: MedicationDosage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDosages(dosages: List<MedicationDosage>)

    @Query("SELECT * FROM medicationDosages WHERE medicationId = :medicationId")
    fun getDosagesForMedication(medicationId: Long): Flow<List<MedicationDosage>>

    @Query("DELETE FROM medicationDosages WHERE medicationId = :medicationId")
    suspend fun deleteDosagesForMedication(medicationId: Long)

    @Query("SELECT * FROM medicationDosages")
    fun getAllDosages(): Flow<List<MedicationDosage>>

    @Query("UPDATE medicationDosages SET isDosageTaken = :isTaken WHERE id = :dosageId")
    suspend fun updateDosageStatus(dosageId: Long, isTaken: Boolean)

    @Query("SELECT * FROM medicationDosages WHERE scheduledDatetime BETWEEN :startOfDay AND :endOfDay")
    fun getDosagesForDate(startOfDay: Long, endOfDay: Long): Flow<List<MedicationDosage>>

    @Query("SELECT COUNT(*) FROM medications")
    suspend fun getMedicationCount(): Int
}
