package com.example.caresync.ui.screens.healthcard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.caresync.BottomNavItem
import com.example.caresync.CareSyncApp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

@Composable
fun HealthCardScreen(viewModel: HealthCardViewModel = viewModel()) {
    var showAddScreen by remember { mutableStateOf(false) }

    if (showAddScreen) {
        AddMedicationScreen(
            onMedicationAdded = { name, dosage, frequency, imageUri ->
                viewModel.addMedication(name, dosage, frequency, imageUri)
                showAddScreen = false
            },
            onBack = { showAddScreen = false }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Health Card",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    IconButton(onClick = { showAddScreen = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Medication")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                MedicationContent(viewModel = viewModel)
            }
        }
    }
}
@Preview
@Composable
fun HealthCardScreenPreview() {
    CareSyncApp({}, BottomNavItem.HealthCard.route)
}