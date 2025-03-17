package com.example.caresync.datasource

import android.net.Uri
import com.example.caresync.model.EmergencyContact
import com.example.caresync.model.UserProfile

object UserDataSource {
    val sampleEmergencyContacts = listOf(
        EmergencyContact("Father", "John Smith", "+65 1234-1234"),
        EmergencyContact("Mother", "Jane Doe", "+65 5678-5678"),
        EmergencyContact("Sibling", "Jake Doe", "+60 12-123-4568")
    )

    val sampleUserProfile = UserProfile(
        photoUri = null,
        name = "Zhe Tao",
        dateOfBirth = "1995-07-20",
        primaryLanguage = "English",
        organDonation = true,
        pregnancy = false,
        allergies = "Peanuts, Pollen",
        conditions = "Asthma",
        height = 175.5,
        weight = 66.5,
        bloodType = "O+",
        emergencyContacts = sampleEmergencyContacts
    )
}
