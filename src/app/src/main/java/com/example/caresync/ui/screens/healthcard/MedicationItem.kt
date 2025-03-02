package com.example.caresync.ui.screens.healthcard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MedicationItem(medication: Medication) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = medication.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Dosage: ${medication.dosage}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Frequency: ${medication.frequency}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}