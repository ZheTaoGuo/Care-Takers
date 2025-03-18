package com.example.caresync.datasource

import android.net.Uri
import com.example.caresync.model.EmergencyContact
import com.example.caresync.model.UserProfile

object UserProfileDataSource {
    val sampleUserProfile = UserProfile(
//        photoUri = null,
        id = 1,
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
    )

}

object EmergencyContactDataSource {
    val sampleEmergencyContacts = listOf(
        EmergencyContact(1, "Father", "John Smith", "+65 1234-1234"),
        EmergencyContact(2,"Mother", "Jane Doe", "+65 5678-5678"),
        EmergencyContact(3,"Sibling", "Jake Doe", "+60 12-123-4568")
    )
}
