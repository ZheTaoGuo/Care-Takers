package com.example.caresync.ui.screens.healthcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.caresync.R
import com.example.caresync.model.Medication

@Composable
fun MedicationContent(viewModel: HealthCardViewModel = viewModel()) {
    val medications by viewModel.medications.collectAsState()

    if (medications.isEmpty()) {
        println("Medications empty: $medications")
        Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Text("No medications added yet.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        println("Medications got something: $medications")
        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                medications.forEach {medication ->
                    MedicationItem(medication)
                }
        }
            Spacer(modifier = Modifier.height(16.dp))

            Text("Share QR Code with Clinic/Caregiver:")
            QRCodeGenerator(content = medications.joinToString { "${it.name}: ${it.dosage}, ${it.frequency}" })
        }
//    }
}

@Composable
fun MedicationItem(
    medication: Medication,
) {
    LaunchedEffect(Unit) {
        println("Medications List: $medication.name")
    }
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
//            Image (if available) or default
            val imagePainter =
                painterResource(id = R.drawable.default_medication) // Default image

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

        }
    }
}