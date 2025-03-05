package com.example.caresync.ui.screens.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.caresync.ui.screens.map.ClinicItem
import com.example.caresync.ui.screens.map.PermissionDialog
import com.example.caresync.ui.screens.map.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@Composable
fun MapScreen(viewModel: MapViewModel = remember { MapViewModel() }) {
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.defaultLocation, 12f)
    }

    var locationPermissionGranted by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> locationPermissionGranted = granted }
    )

    // Check location permission when screen is launched
    LaunchedEffect(Unit) {
        locationPermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!locationPermissionGranted) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Show permission popup
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
                // Dynamic clinic markers
                viewModel.clinicLocations.forEach { clinic ->
                    Marker(
                        state = MarkerState(position = clinic.location),
                        title = clinic.name,
                        snippet = clinic.address
                    )
                }
            }

            // Overlay Clinic List at Bottom
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp) // Ensures it's at the bottom
                    .align(Alignment.BottomCenter)
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.Bottom), // Ensures it stays at the bottom
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.clinicLocations) { clinic ->
                        val isExpanded = viewModel.isClinicExpanded(clinic)

                        ClinicItem(
                            clinic = clinic,
                            isExpanded = isExpanded,
                            onToggleExpand = { viewModel.toggleClinicExpansion(clinic) },
                            modifier = Modifier
                                .padding(bottom = if (isExpanded) 80.dp else 0.dp) // Expands upwards
                        )
                    }
                }
            }
        }
    }
}
