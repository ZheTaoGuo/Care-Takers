package com.example.caresync.model

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val photoUri: String?,
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
)

@Entity(
    tableName = "emergency_contacts"
)
data class EmergencyContact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val relationship: String,
    val name: String,
    val phoneNumber: String
)