package com.example.caresync.ui.screens.caregiver

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.caresync.R

class PatientViewModel : ViewModel() {
    var isLinked by mutableStateOf(false)
    var userName by mutableStateOf("")
    var userRole by mutableStateOf("")
    var email by mutableStateOf("")
    var dob by mutableStateOf("")
    var address by mutableStateOf("")
    var profilePicture by mutableStateOf(R.drawable.default_profile) // Default profile picture

    // Mock linking process
    fun linkPatientProfile() {
        isLinked = true
        userName = "George Tan"
        userRole = "Grumpy Old Man"
        email = "george.tan@email.com"
        dob = "22 July 1952"
        address = "99 Orchard Towers, Singapore"
        profilePicture = R.drawable.old_man // Replace with actual drawable resource
    }
}
