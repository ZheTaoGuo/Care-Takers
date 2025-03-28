package com.example.caresync.ui.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.caresync.CareSyncApplication
import com.example.caresync.model.CalendarUiState
import com.example.caresync.model.Medication
import com.example.caresync.model.MedicationDao
import com.example.caresync.model.MedicationDosage
import com.example.caresync.model.MedicationRepository
import com.example.caresync.ui.screens.currentRoute
import com.example.caresync.utils.Clamp
import com.example.caresync.utils.addOneDay
import com.example.caresync.utils.getDayBounds
import com.example.caresync.utils.minusOneDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import java.util.Calendar
import java.util.Date

class CalendarViewModel(private val medicationDao: MedicationDao) : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUiState(currentDate = Calendar.getInstance().time))
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()
    private val _isSheetVisible = MutableStateFlow(false)
    val isSheetVisible: StateFlow<Boolean> = _isSheetVisible.asStateFlow()

    fun updateStartHour(startHour: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                startHour = startHour
            )
        }
    }

    fun updateEndHour(endHour: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                endHour = endHour
            )
        }
    }

    fun updateMinuteHeight(minuteHeight: Float) {
        val clampedMinuteHeight = Clamp(minuteHeight, 0.5f, 3.0f)
        _uiState.update { currentState ->
            currentState.copy(
                minuteHeight = clampedMinuteHeight
            )
        }
    }

    fun getDosagesForDate(date: Date): Flow<List<MedicationDosage>> {
        val (start, end) = getDayBounds(date)
        return medicationDao.getDosagesForDate(start, end)
    }

    suspend fun getMedicationName(medicationId: Long): String {
        val medication = medicationDao.getMedicationById(medicationId)
        return medication?.name ?: "NULL"
    }

    suspend fun updateDosageTaken(dosageId: Long, isDosageTaken: Boolean) {
        medicationDao.updateDosageStatus(dosageId, isDosageTaken)
    }

    fun navigateToNextDay() {
        _uiState.update { currentState ->
            val nextDayDate = addOneDay(currentState.currentDate)
            currentState.copy(
                currentDate = nextDayDate
            )
        }
    }

    fun navigateToPrevDay() {
        _uiState.update { currentState ->
            val nextDayDate = minusOneDay(currentState.currentDate)
            currentState.copy(
                currentDate = nextDayDate
            )
        }
    }

    fun showBtmSheet() {
        _isSheetVisible.value = true
    }

    fun dismissBtmSheet() {
        _isSheetVisible.value = false
    }

    fun setDosageToEdit(dosageToEdit: MedicationDosage, medicationName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                dosageToEdit = dosageToEdit,
                dosageToEditName = medicationName
            )
        }
    }

    fun setProposedPostponementDate(newDate: Date) {
        _uiState.update { currentState ->
            currentState.copy(
                dosageProposedPostponedDate = newDate
            )
        }
    }

    suspend fun computeDosagePostponementDate(missedDosage: MedicationDosage): Date {
        val medication: Medication? = medicationDao.getMedicationById(missedDosage.medicationId)
        if (medication != null) {
            val dosages: List<MedicationDosage> = medicationDao.getDosagesForMedication(medication.id).first()
            val repository: MedicationRepository = MedicationRepository(medicationDao)
            val postponedDate: Date = repository.findNextAvailableSlot(
                missedDosage = missedDosage,
                medication = medication,
                existingDosages = dosages
            )

            setProposedPostponementDate(postponedDate)
            return postponedDate
        }

        return missedDosage.scheduledDatetime
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CareSyncApplication)
                CalendarViewModel(application.medicationDatabase.medicationDao())
            }
        }
    }
}

