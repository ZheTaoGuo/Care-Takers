package com.example.caresync.ui.screens.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@Composable
fun MapScreen(viewModel: MapViewModel = remember { MapViewModel() }) {
    val context = LocalContext.current

    var selectedClinic by remember { mutableStateOf<Clinic?>(null) }
    var selectedFilter by remember { mutableStateOf(StockFilter.ALL) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.defaultLocation, 12f)
    }

    var locationPermissionGranted by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> locationPermissionGranted = granted }
    )

    LaunchedEffect(Unit) {
        locationPermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!locationPermissionGranted) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    if (!locationPermissionGranted) {
        PermissionDialog(
            onDismiss = {},
            onConfirm = { permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.fillMaxSize()) {
            // Google Map
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(myLocationButtonEnabled = locationPermissionGranted),
                properties = MapProperties(isMyLocationEnabled = locationPermissionGranted)
            ) {
                viewModel.clinicLocations.forEach { clinic ->
                    Marker(
                        state = MarkerState(position = clinic.location),
                        title = clinic.name,
                        snippet = clinic.address
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            ) {
                FilterBar(selectedFilter) {
                    selectedFilter = it
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                ClinicList(viewModel, selectedFilter) { clinic ->
                    selectedClinic = clinic
                }
            }
        }

        // Move camera when a clinic is selected
        LaunchedEffect(selectedClinic) {
            selectedClinic?.let { clinic ->
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(clinic.location, 15f),
                    durationMs = 800
                )
            }
        }
    }
}

@Composable
fun ClinicList(
    viewModel: MapViewModel,
    selectedFilter: StockFilter,
    onClinicClick: (Clinic) -> Unit
) {
    var expandedClinic by remember { mutableStateOf<Clinic?>(null) }

    val filteredClinics = viewModel.clinicLocations.filter { clinic ->
        when (selectedFilter) {
            StockFilter.ALL -> true
            StockFilter.AVAILABLE -> clinic.stockStatus == StockStatus.AVAILABLE
            StockFilter.LIMITED -> clinic.stockStatus == StockStatus.LIMITED
            StockFilter.OUT_OF_STOCK -> clinic.stockStatus == StockStatus.OUT_OF_STOCK
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filteredClinics) { clinic ->
            val isExpanded = expandedClinic == clinic
            val animatedHeight by animateDpAsState(
                targetValue = if (isExpanded) 200.dp else 120.dp,
                label = "cardHeight"
            )

            Box(
                modifier = Modifier
                    .height(animatedHeight)
                    .width(220.dp)
                    .clickable {
                        expandedClinic = if (isExpanded) null else clinic
                        onClinicClick(clinic)
                    }
            ) {
                ClinicItem(
                    clinic = clinic,
                    isExpanded = isExpanded,
                    onToggleExpand = {
                        expandedClinic = if (isExpanded) null else clinic
                        onClinicClick(clinic)
                    }
                )
            }
        }
    }
}
