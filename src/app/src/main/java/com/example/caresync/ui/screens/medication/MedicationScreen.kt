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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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

@Composable
fun MedicationScreen(viewModel: MedicationViewModel = viewModel()) {
//    val uiState by viewModel.MedicationUIState.collectAsState()
    val medications by viewModel.getAllMedications().collectAsState(initial = emptyList())
    MedicationScreenContent(
        medications = medications,
        onAddMedicationClick = {},
    )
}

@Composable
fun MedicationScreenContent(
    medications: List<Medication>,
    onAddMedicationClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddMedicationClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Medication")
            }
        }
    ) {
            innerPadding -> Column(modifier = Modifier.fillMaxSize().padding(innerPadding)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            for(i in medications.indices) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MedicationCard(medications[i], modifier = Modifier.weight(1f))
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

//    CareSyncPatientAppScreens({}, BottomNavItem.Medication.route)
