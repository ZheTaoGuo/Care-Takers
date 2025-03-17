package com.example.caresync.ui.screens.healthcard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.caresync.model.Medication

@Composable
fun MedicationContent(viewModel: HealthCardViewModel = viewModel()) {
    val medications by viewModel.medications.collectAsState()

    if (medications.isEmpty()) {
        println("Medications empty: $medications")
        Box(modifier = Modifier.fillMaxSize().padding(8.dp), contentAlignment = Alignment.Center) {
            Text("No medications added yet.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        println("Medications got something: $medications")

        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            for (i in medications.indices step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MedicationItem(medications[i], modifier = Modifier.weight(1f))

                    if (i + 1 < medications.size) {
                        MedicationItem(medications[i + 1], modifier = Modifier.weight(1f))
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

        }
    }
}

@Composable
fun MedicationItem(medication: Medication, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        modifier = modifier.padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = medication.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text(text = "Dosage: ${medication.amtPerDosage}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Frequency: ${medication.frequency}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}