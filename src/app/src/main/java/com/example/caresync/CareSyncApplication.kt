package com.example.caresync

import android.app.Application
import com.example.caresync.model.MedicationDatabase
import com.example.caresync.model.UserProfileDatabase
import com.example.caresync.model.EmergencyContactDatabase

class CareSyncApplication: Application() {
    val medicationDatabase: MedicationDatabase by lazy { MedicationDatabase.getInstance(this) }
    val userProfileDatabase: UserProfileDatabase by lazy { UserProfileDatabase.getDatabase(this) }
    val emergencyContactDatabase: EmergencyContactDatabase by lazy { EmergencyContactDatabase.getDatabase(this) }
}