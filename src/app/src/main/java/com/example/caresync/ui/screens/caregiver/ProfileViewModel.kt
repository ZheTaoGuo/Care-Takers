package com.example.caresync.ui.screens.caregiver

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class ProfileViewModel:ViewModel() {
    var userName by mutableStateOf("Ryan Chenkie")
    var userRole by mutableStateOf("Teacher")

    var email by mutableStateOf("ryan.chenkie@gmail.com")
    var dob by mutableStateOf("12 December 1990")
    var address by mutableStateOf("Singapore Management University")

    var showDialog by mutableStateOf(false)
}