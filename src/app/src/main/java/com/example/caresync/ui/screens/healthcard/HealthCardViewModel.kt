package com.example.caresync.ui.screens.healthcard

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.caresync.CareSyncApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.caresync.model.Medication
import com.example.caresync.datasource.MedicationDataSource
import com.example.caresync.model.EmergencyContact
import com.example.caresync.model.UserProfile
import com.example.caresync.datasource.UserDataSource
import com.example.caresync.model.MedicationDao
import com.example.caresync.model.MedicationDosage
import com.example.caresync.utils.getDayBounds
import kotlinx.coroutines.flow.Flow
import java.util.Date

class HealthCardViewModel(private val medicationDao: MedicationDao) : ViewModel() {
    private val _userProfile = MutableStateFlow(UserDataSource.sampleUserProfile)
    val userProfile: StateFlow<UserProfile> = _userProfile

    fun updateUserProfileInfo(name: String, dob: String, language: String, photoUri: Uri?, organDonation: Boolean) {
        viewModelScope.launch {
            _userProfile.value = _userProfile.value.copy(
                name = name,
                dateOfBirth = dob,
                primaryLanguage = language,
                photoUri = photoUri,
                organDonation = organDonation
            )
        }
    }

    fun updatePregnancyStatus(isPregnant: Boolean) {
        viewModelScope.launch {
            _userProfile.value = _userProfile.value.copy(pregnancy = isPregnant)
        }
    }

    fun updateAllergies(allergies: String) {
        viewModelScope.launch {
            _userProfile.value = _userProfile.value.copy(allergies = allergies)
        }
    }

    fun updateMedicalConditions(conditions: String) {
        viewModelScope.launch {
            _userProfile.value = _userProfile.value.copy(conditions = conditions)
        }
    }

    fun updateAdditionalInfo(height: Double, weight: Double, bloodType: String) {
        viewModelScope.launch {
            _userProfile.value = _userProfile.value.copy(
                height = height,
                weight = weight,
                bloodType = bloodType
            )
        }
    }

    fun addEmergencyContact(relationship: String, name: String, phoneNumber: String) {
        viewModelScope.launch {
            val updatedContacts = _userProfile.value.emergencyContacts + EmergencyContact(relationship, name, phoneNumber)
            _userProfile.value = _userProfile.value.copy(emergencyContacts = updatedContacts)
        }
    }

    fun removeEmergencyContact(index: Int) {
        viewModelScope.launch {
            val updatedContacts = _userProfile.value.emergencyContacts.toMutableList()
            updatedContacts.removeAt(index)
            _userProfile.value = _userProfile.value.copy(emergencyContacts = updatedContacts)
        }
    }

    fun getAllMedications(): Flow<List<Medication>> {
        return medicationDao.getAllMedications()
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CareSyncApplication)
                HealthCardViewModel(application.medicationDatabase.medicationDao())
            }
        }
    }
}