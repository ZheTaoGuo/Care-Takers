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

@Database(entities = [Medication::class], version = 1, exportSchema = false)
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
                            // Populate the database on first run
                            Executors.newSingleThreadExecutor().execute {
                                val dao = getInstance(context).medicationDao()
                                if (dao.getMedicationCount() == 0) {
                                    dao.insertAll(MedicationDataSource.sampleMedications)
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
