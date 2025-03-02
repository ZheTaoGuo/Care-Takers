package com.example.caresync.ui.screens.healthcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HealthCardViewModel : ViewModel() {
    private val repository = MedicationRepository()
    val medications: StateFlow<List<Medication>> = repository.medications

    fun addMedication(name: String, dosage: String, frequency: String) {
        val newMedication = Medication(
            id = medications.value.size + 1,
            name = name,
            dosage = dosage,
            frequency = frequency
        )
        viewModelScope.launch {
            repository.addMedication(newMedication)
        }
    }
}