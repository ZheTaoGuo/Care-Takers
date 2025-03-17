package com.example.caresync.model

import android.net.Uri

data class UserProfile(
    val photoUri: Uri?,
    val name: String,
    val dateOfBirth: String,
    val primaryLanguage: String ,
    val organDonation: Boolean,
    val pregnancy: Boolean,
    val allergies: String,
    val conditions: String,
    val height: Double ,
    val weight: Double ,
    val bloodType: String,
    val emergencyContacts: List<EmergencyContact> = emptyList()
)

data class EmergencyContact(
    val relationship: String,
    val name: String,
    val phoneNumber: String
)