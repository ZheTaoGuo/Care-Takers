package com.example.caresync.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MedicationDao {
    @Query("SELECT * FROM medications")
    fun getAllMedications(): List<Medication>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(medications: List<Medication>)

    @Query("SELECT COUNT(*) FROM medications")
    fun getMedicationCount(): Int
}
