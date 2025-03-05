package com.example.caresync.ui.screens.healthcard

import android.net.Uri

data class UserProfile(
    val photoUri: Uri? = null,
    val name: String = "John Doe",
    val dateOfBirth: String = "",
    val primaryLanguage: String = "English",
    val organDonation: Boolean = false,
    val pregnancy: Boolean = false,
    val allergies: String = "--",
    val conditions: String = "--",
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val bloodType: String = "--"
)
