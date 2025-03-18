package com.example.caresync.ui.screens.healthcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.caresync.CareSyncApplication
import kotlinx.coroutines.launch
import com.example.caresync.model.Medication
import com.example.caresync.model.EmergencyContact
import com.example.caresync.model.EmergencyContactDao
import com.example.caresync.model.UserProfile
import com.example.caresync.model.MedicationDao
import com.example.caresync.model.UserProfileDao
import kotlinx.coroutines.flow.Flow

class HealthCardViewModel(
    private val userProfileDao: UserProfileDao,
    private val emergencyContactDao: EmergencyContactDao,
    private val medicationDao: MedicationDao)
    : ViewModel() {

    fun getUserProfile(): Flow<UserProfile> {
        return userProfileDao.getUserProfile()
    }

    fun updateUserProfileInfo(name: String, dob: String, language: String, organDonation: Boolean) {
        viewModelScope.launch {
            val profile = userProfileDao.getUserProfileNow()
            userProfileDao.updateUserProfile(
                profile.copy(
                    name = name,
                    dateOfBirth = dob,
                    primaryLanguage = language,
//                    photoUri = photoUri?.toString(),
                    organDonation = organDonation
                )
            )
        }
    }

    fun updatePregnancyStatus(isPregnant: Boolean) {
        viewModelScope.launch {
            val profile = userProfileDao.getUserProfileNow()
            userProfileDao.updateUserProfile(profile.copy(pregnancy = isPregnant))
        }
    }

    fun updateAllergies(allergies: String) {
        viewModelScope.launch {
            val profile = userProfileDao.getUserProfileNow()
            userProfileDao.updateUserProfile(profile.copy(allergies = allergies))
        }
    }

    fun updateMedicalConditions(conditions: String) {
        viewModelScope.launch {
            val profile = userProfileDao.getUserProfileNow()
            userProfileDao.updateUserProfile(profile.copy(conditions = conditions))
        }
    }

    fun updateAdditionalInfo(height: Double, weight: Double, bloodType: String) {
        viewModelScope.launch {
            val profile = userProfileDao.getUserProfileNow()
            userProfileDao.updateUserProfile(
                profile.copy(
                    height = height,
                    weight = weight,
                    bloodType = bloodType
                )
            )
        }
    }

    fun getEmergencyContacts(): Flow<List<EmergencyContact>> {
        return emergencyContactDao.getEmergencyContacts()
    }

    fun addEmergencyContact(relationship: String, name: String, phoneNumber: String) {
        viewModelScope.launch {
            val contact = EmergencyContact(relationship = relationship, name = name, phoneNumber = phoneNumber)
            emergencyContactDao.insertEmergencyContact(contact)
        }
    }

    fun removeEmergencyContact(contact: EmergencyContact) {
        viewModelScope.launch {
            emergencyContactDao.deleteEmergencyContact(contact)
        }
    }

    fun getAllMedications(): Flow<List<Medication>> {
        return medicationDao.getAllMedications()
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CareSyncApplication)
                HealthCardViewModel(
                    application.userProfileDatabase.userProfileDao(),
                    application.emergencyContactDatabase.emergencyContactDao(),
                    application.medicationDatabase.medicationDao()
                )
            }
        }
    }

}