package com.example.caresync.ui.screens.map

import com.google.android.gms.maps.model.LatLng

data class Clinic(
    val name: String,          // Clinic or Pharmacy Name
    val address: String,       // Full Address
    val location: LatLng,      // Geolocation (Lat/Lng)
    val availableMedications: List<String>, // List of stocked medications
    val status: ClinicStatus,  // OPEN / CLOSED / 24HRS
    val stockStatus: StockStatus, // Available / Limited / Out of Stock
    val phoneNumber: String,   // Contact Number
    val website: String?,      // Optional: Website for more details
    val isPharmacy: Boolean    // True if it's a Pharmacy, False if Clinic
)

// Enum to track clinic opening status
enum class ClinicStatus {
    OPEN, CLOSED, TWENTY_FOUR_HOURS
}

// Enum to track medication stock availability
enum class StockStatus {
    AVAILABLE, LIMITED, OUT_OF_STOCK
}

enum class StockFilter(val label: String) {
    ALL("All"),
    AVAILABLE("Available"),
    LIMITED("Limited"),
    OUT_OF_STOCK("Out of Stock")
}
