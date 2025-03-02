package com.example.caresync.ui.screens.healthcard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.caresync.BottomNavItem
import com.example.caresync.CareSyncApp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding

@Composable
fun HealthCardScreen(viewModel: HealthCardViewModel = viewModel()) {
    val medications by viewModel.medications.collectAsState()
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Medication Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosage") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = frequency,
                onValueChange = { frequency = it },
                label = { Text("Frequency") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (name.isNotBlank() && dosage.isNotBlank() && frequency.isNotBlank()) {
                        viewModel.addMedication(name, dosage, frequency)
                        name = ""; dosage = ""; frequency = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Medication")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Current Medications:", style = MaterialTheme.typography.titleMedium)
            LazyColumn {
                items(medications) { medication ->
                    MedicationItem(medication)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Share QR Code with Clinic/Caregiver:")
            QRCodeGenerator(content = medications.joinToString { "${it.name}: ${it.dosage}, ${it.frequency}" })
        }    }
}


@Preview
@Composable
fun HealthCardScreenPreview() {
    CareSyncApp({}, BottomNavItem.HealthCard.route)
}