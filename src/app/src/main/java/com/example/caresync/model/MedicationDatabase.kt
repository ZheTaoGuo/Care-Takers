package com.example.caresync.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.caresync.datasource.MedicationDataSource
import com.example.caresync.utils.Converters
import java.util.concurrent.Executors

@Database(entities = [Medication::class, MedicationDosage::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MedicationDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao

    companion object {
        @Volatile
        private var INSTANCE: MedicationDatabase? = null

        fun getInstance(context: Context): MedicationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedicationDatabase::class.java,
                    "medication_db"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadExecutor().execute {
                                INSTANCE?.let { database ->
                                    val dao = database.medicationDao()
                                    if (dao.getMedicationCount() == 0) {
                                        val repository = MedicationRepository(dao)
                                        for (medication in MedicationDataSource.sampleMedications) {
                                            repository.insertMedicationWithDosages(medication)
                                        }
                                    }
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
