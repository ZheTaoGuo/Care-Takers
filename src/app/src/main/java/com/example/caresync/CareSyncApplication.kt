package com.example.caresync

import android.app.Application
import com.example.caresync.model.MedicationDatabase

class CareSyncApplication: Application() {
    val medicationDatabase: MedicationDatabase by lazy { MedicationDatabase.getInstance(this) }
}