package com.example.caresync

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.caresync.model.MedicationDatabase
import com.example.caresync.model.UserProfileDatabase
import com.example.caresync.model.EmergencyContactDatabase
import com.example.caresync.utils.NotificationHandler

class CareSyncApplication: Application() {
    val medicationDatabase: MedicationDatabase by lazy { MedicationDatabase.getInstance(this) }
    val userProfileDatabase: UserProfileDatabase by lazy { UserProfileDatabase.getDatabase(this) }
    val emergencyContactDatabase: EmergencyContactDatabase by lazy { EmergencyContactDatabase.getDatabase(this) }
    lateinit var notificationHandler: NotificationHandler private set

    @RequiresApi(Build.VERSION_CODES.O) // For notifications, need API level 26
    override fun onCreate() {
        super.onCreate()
        // Initialize NotificationHandler to ensure the channel is created
        notificationHandler = NotificationHandler(this)
    }
}