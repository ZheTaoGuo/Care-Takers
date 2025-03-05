package com.example.caresync.ui.screens.map

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    val defaultLocation = LatLng(1.3521, 103.8198) // Singapore

    // Mutable state list to hold clinic locations dynamically
    private val _clinicLocations = mutableStateListOf<Clinic>()
    val clinicLocations: List<Clinic> get() = _clinicLocations

    // Stores the expanded state for each clinic using StateFlow
    private val _expandedStates = MutableStateFlow(mutableMapOf<String, Boolean>())

    init {
        loadClinics()
    }

    // Function to load clinic data
    private fun loadClinics() {
        _clinicLocations.addAll(
            listOf(
                Clinic(
                    name = "Raffles Medical Clinic",
                    address = "585 North Bridge Rd, Singapore 188770",
                    location = LatLng(1.2988, 103.8545),
                    availableMedications = listOf("Paracetamol", "Ibuprofen", "Cough Syrup"),
                    status = ClinicStatus.OPEN,
                    stockStatus = StockStatus.AVAILABLE,
                    phoneNumber = "+65 6311 1111",
                    website = "https://www.rafflesmedicalgroup.com",
                    isPharmacy = false
                ),
                Clinic(
                    name = "Guardian Pharmacy - Orchard",
                    address = "290 Orchard Rd, Paragon, Singapore 238859",
                    location = LatLng(1.3030, 103.8345),
                    availableMedications = listOf("Insulin", "Antibiotics", "Painkillers"),
                    status = ClinicStatus.OPEN,
                    stockStatus = StockStatus.LIMITED,
                    phoneNumber = "+65 6836 3900",
                    website = "https://www.guardian.com.sg",
                    isPharmacy = true
                ),
                Clinic(
                    name = "Parkway Shenton Clinic",
                    address = "9 Scotts Rd, Singapore 228210",
                    location = LatLng(1.3072, 103.8335),
                    availableMedications = listOf("Flu Medication", "Panadol", "Allergy Tablets"),
                    status = ClinicStatus.TWENTY_FOUR_HOURS,
                    stockStatus = StockStatus.OUT_OF_STOCK,
                    phoneNumber = "+65 6235 2366",
                    website = "https://www.parkwayshenton.com",
                    isPharmacy = false
                ),
                Clinic(
                    name = "Watsons Pharmacy - Bugis",
                    address = "200 Victoria St, Bugis Junction, Singapore 188021",
                    location = LatLng(1.2995, 103.8553),
                    availableMedications = listOf("Cough Syrup", "Vitamin C", "Eye Drops"),
                    status = ClinicStatus.OPEN,
                    stockStatus = StockStatus.AVAILABLE,
                    phoneNumber = "+65 6338 6870",
                    website = "https://www.watsons.com.sg",
                    isPharmacy = true
                ),
                Clinic(
                    name = "Unity Pharmacy - Tiong Bahru",
                    address = "302 Tiong Bahru Rd, Singapore 168732",
                    location = LatLng(1.2863, 103.8285),
                    availableMedications = listOf("Antiseptics", "Painkillers", "Prescribed Medications"),
                    status = ClinicStatus.CLOSED,
                    stockStatus = StockStatus.LIMITED,
                    phoneNumber = "+65 6271 0116",
                    website = "https://www.fairprice.com.sg",
                    isPharmacy = true
                )
            )
        )
    }

    fun toggleClinicExpansion(clinic: Clinic) {
        viewModelScope.launch {
            val updatedStates = _expandedStates.value.toMutableMap()
            updatedStates[clinic.name] = !(updatedStates[clinic.name] ?: false)
            _expandedStates.value = updatedStates
        }
    }

    fun isClinicExpanded(clinic: Clinic): Boolean {
        return _expandedStates.value[clinic.name] ?: false
    }
}
