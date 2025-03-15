package com.example.caresync.ui.screens.medication

import androidx.lifecycle.ViewModel
import com.example.caresync.model.MedicationUIState
import com.example.caresync.model.Medication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MedicationViewModel: ViewModel() {
    private val _MedicationUIState = MutableStateFlow(MedicationUIState())
    val MedicationUIState: StateFlow<MedicationUIState> = _MedicationUIState.asStateFlow()

    fun addMedication(medication: Medication) {
        _MedicationUIState.update { currentState ->
            currentState.copy(medications = currentState.medications + medication)
        }
    }

    fun updateMedication(medication: Medication) {
        _MedicationUIState.update { currentState ->
            currentState.copy(medications = currentState.medications.map { if (it.id == medication.id) medication else it })
        }

    }

    fun deleteMedication(medication: Medication) {
        _MedicationUIState.update { currentState ->
            currentState.copy(medications = currentState.medications.filter { it.id != medication.id })
        }

    }

}