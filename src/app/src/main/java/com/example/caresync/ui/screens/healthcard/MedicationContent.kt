package com.example.caresync.ui.screens.healthcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.caresync.R

@Composable
fun MedicationContent(viewModel: HealthCardViewModel = viewModel()) {
    val medications by viewModel.medications.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedMedication by remember { mutableStateOf<Medication?>(null) }

    if (medications.isEmpty()) {
        Text("No medications added yet.", style = MaterialTheme.typography.bodyMedium)
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(medications) { medication ->
                    MedicationItem(
                        medication = medication,
                        onDelete = {
                            selectedMedication = medication
                            showDeleteDialog = true
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Share QR Code with Clinic/Caregiver:")
            QRCodeGenerator(content = medications.joinToString { "${it.name}: ${it.dosage}, ${it.frequency}" })
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to remove this medication?") },
            confirmButton = {
                Button(
                    onClick = {
                        selectedMedication?.let { viewModel.removeMedication(it) }
                        showDeleteDialog = false
                    }
                ) {
                    Text("YES")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("NO")
                }
            }
        )
    }
}

@Composable
fun MedicationItem(
    medication: Medication,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image (if available) or default
            val imagePainter = if (medication.imageUri != null) {
                rememberAsyncImagePainter(medication.imageUri)
            } else {
                painterResource(id = R.drawable.default_medication) // Default image
            }
            Image(
                painter = imagePainter,
                contentDescription = medication.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = medication.name, style = MaterialTheme.typography.headlineSmall)
                Text(text = "Dosage: ${medication.dosage}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Frequency: ${medication.frequency}", style = MaterialTheme.typography.bodyMedium)
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Medication")
            }
        }
    }
}