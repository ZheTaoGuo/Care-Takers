package com.example.caresync.ui.screens.medication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePickerDefaults.dateFormatter
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.caresync.R
import com.example.caresync.datasource.MedicationDataSource.dateFormatter
import com.example.caresync.model.Frequency
import com.example.caresync.model.MedicationUIState
import com.example.caresync.model.Medication
import com.example.caresync.model.MedicationType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.Date
import com.example.caresync.model.MedicationDao



class MedicationViewModel(
    private val medicationDao: MedicationDao
): ViewModel() {
    private val _MedicationUIState = MutableStateFlow(MedicationUIState())
    val MedicationUIState: StateFlow<MedicationUIState> = _MedicationUIState.asStateFlow()

    var medicationName by mutableStateOf("")
    var frequency by mutableStateOf(Frequency.ONCE)
    var amtPerDosage by mutableIntStateOf(0)
    var totalDosage by mutableIntStateOf(0)
    var showDatePicker by mutableStateOf(false)
    var startDate by mutableStateOf(Date())
    var medicationType by mutableStateOf(MedicationType.TABLET)
    var medications by mutableStateOf<List<Medication>>(emptyList())

    fun medicationMockData() {
        medications = listOf(
            Medication(
            name = "Aspirin",
            frequency = Frequency.ONCE,
            amtPerDosage = 5,
            totalDosage = 20,
            startDate = Date(),
            type = MedicationType.TABLET
        )
        )
    }

    fun addMedication(medication: Medication) {
        _MedicationUIState.update { currentState ->
            currentState.copy(medications = currentState.medications + medication)
        }
    }
    fun getAllMedications(): Flow<List<Medication>> {
        return medicationDao.getAllMedications()
    }

}