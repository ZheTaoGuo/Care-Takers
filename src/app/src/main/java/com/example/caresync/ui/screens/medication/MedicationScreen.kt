package com.example.caresync.ui.screens.medication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.window.Popup
import com.example.caresync.datasource.MedicationDataSource
import com.example.caresync.model.Frequency
import com.example.caresync.model.Medication
import com.example.caresync.model.MedicationType
import com.example.caresync.ui.screens.healthcard.MedicationItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController

@Composable
fun MedicationScreen(viewModel: MedicationViewModel = viewModel(factory = MedicationViewModel.factory),
                     navController: NavController
) {
    val medications by viewModel.getAllMedications().collectAsState(initial = emptyList())
    MedicationScreenContent(
        medications = medications,
        onAddMedicationClick = {
            navController.navigate("addMedication")
        },
    )
}

@Composable
fun MedicationScreenContent(
    medications: List<Medication>,
    onAddMedicationClick: () -> Unit
) {
    Scaffold { innerPadding -> Column(
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(2.dp)
        ) {
            Button(
                onClick = onAddMedicationClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Add Medication")
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(medications) { medication ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MedicationCard(
                        medication = medication,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
    }
}


@Preview(showBackground = true)
@Composable
fun MedicationScreenPreview() {
    val mockMedications = remember {
        mutableStateListOf<Medication>().apply{
            addAll(MedicationDataSource.sampleMedications)
        }
    }
    MedicationScreenContent(
        medications = mockMedications,
        onAddMedicationClick = {}
    )
}

