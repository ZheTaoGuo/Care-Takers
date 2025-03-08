package com.example.caresync.ui.screens.caregiver

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.caresync.R
import kotlin.random.Random

class InventoryViewModel : ViewModel() {
    // Random store data
    private val storeNames = listOf(
        "MediPlus Pharmacy", "Guardian", "Watsons", "HealthMart", "PharmaCare", "WellnessHub"
    )
    private val distances = listOf("500m away", "1.2km away", "750m away", "2km away")

    var storeName by mutableStateOf(storeNames.random())
    var storeDistance by mutableStateOf(distances.random())

    // Medicine List
    var medicines by mutableStateOf(
        listOf(
            Medicine("Panadol", false),
            Medicine("Cough Syrup", false),
            Medicine("Vitamin C", false),
            Medicine("Antibiotics", false),
            Medicine("Allergy Pills", false),
            Medicine("Eye Drops", false)
        )
    )

    fun toggleMedicineChecked(index: Int) {
        medicines = medicines.toMutableList().apply {
            this[index] = this[index].copy(isChecked = !this[index].isChecked)
        }
    }
}

data class Medicine(val name: String, val isChecked: Boolean)
