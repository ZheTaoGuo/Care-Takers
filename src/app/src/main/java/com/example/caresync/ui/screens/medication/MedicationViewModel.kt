package com.example.caresync.ui.screens.medication

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.caresync.CareSyncApplication
import com.example.caresync.model.Frequency
import com.example.caresync.model.MedicationUIState
import com.example.caresync.model.Medication
import com.example.caresync.model.MedicationType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import com.example.caresync.model.MedicationDao
import com.example.caresync.model.MedicationRepository
import com.example.caresync.utils.NotificationHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class MedicationViewModel(private val medicationDao: MedicationDao, private val notificationHandler: NotificationHandler): ViewModel() {
    private val _MedicationUIState = MutableStateFlow(MedicationUIState())
    val MedicationUIState: StateFlow<MedicationUIState> = _MedicationUIState.asStateFlow()
    val medicationRepository = MedicationRepository(medicationDao)

    fun addMedication(medication: Medication) {
        viewModelScope.launch(Dispatchers.IO) { // Run in IO thread
            try {
                medicationRepository.insertMedicationWithDosages(medication, notificationHandler)
                _MedicationUIState.update { currentState ->
                    currentState.copy(medications = currentState.medications + medication)
                }
                Log.d("MedicationViewModel", "Successfully add medication")

            } catch (e: Exception) {
                // Optionally log error
                Log.e("MedicationViewModel", "Error adding medication", e)
            }
        }
    }

    fun getAllMedications(): Flow<List<Medication>> {
        return medicationDao.getAllMedications()
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CareSyncApplication)
                MedicationViewModel(application.medicationDatabase.medicationDao(), application.notificationHandler)
            }
        }
    }

}