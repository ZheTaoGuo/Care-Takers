package com.example.caresync.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.caresync.utils.Converters

@Database(entities = [UserProfile::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UserProfileDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        private var Instance: UserProfileDatabase? = null

        fun getDatabase(context: Context): UserProfileDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UserProfileDatabase::class.java, "user_profile_db")
                    .createFromAsset("database/user_profile.db")
                    .build()
                    .also { Instance = it }
            }
        }    }
}

@Database(entities = [EmergencyContact::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EmergencyContactDatabase : RoomDatabase() {
    abstract fun emergencyContactDao(): EmergencyContactDao

    companion object {
        @Volatile
        private var Instance: EmergencyContactDatabase? = null

        fun getDatabase(context: Context): EmergencyContactDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, EmergencyContactDatabase::class.java, "contact_db")
                    .createFromAsset("database/contact.db")
                    .build()
                    .also { Instance = it }
            }
        }    }
}

