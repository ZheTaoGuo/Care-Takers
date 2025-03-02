package com.example.caresync.ui.screens.healthcard

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HealthCardViewModel : ViewModel() {
    private val _medications = MutableStateFlow<List<Medication>>(emptyList())
    val medications: StateFlow<List<Medication>> = _medications

    fun addMedication(name: String, dosage: String, frequency: String, imageUri: Uri?) {
        val newMedication = Medication(
            id = medications.value.size + 1,
            name = name,
            dosage = dosage,
            frequency = frequency,
            imageUri = imageUri
        )
        viewModelScope.launch {
            _medications.value += newMedication
        }
    }

    fun removeMedication(medication: Medication) {
        viewModelScope.launch {
            _medications.value = _medications.value.filterNot { it.id == medication.id }
        }
    }
}