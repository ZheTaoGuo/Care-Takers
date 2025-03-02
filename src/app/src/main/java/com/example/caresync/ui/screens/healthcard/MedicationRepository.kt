package com.example.caresync.ui.screens.healthcard

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MedicationRepository {
    private val _medications = MutableStateFlow<List<Medication>>(emptyList())
    val medications: StateFlow<List<Medication>> = _medications

    fun addMedication(medication: Medication) {
        _medications.value = _medications.value + medication
    }
}