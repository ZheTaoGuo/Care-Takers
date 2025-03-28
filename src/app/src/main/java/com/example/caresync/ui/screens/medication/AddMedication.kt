package com.example.caresync.ui.screens.medication
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.caresync.model.Frequency
import com.example.caresync.model.MedicationType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.caresync.R
import com.example.caresync.model.Medication
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

fun Frequency.getCount(): Int {
    return when (this) {
        Frequency.ONCE -> 1
        Frequency.TWICE -> 2
        Frequency.THRICE -> 3
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationScreen(
    viewModel: MedicationViewModel = viewModel(factory = MedicationViewModel.factory),
    onNavigateBack: () -> Unit
) {
    var medicationName by remember { mutableStateOf("") }
    var selectedFrequency by remember { mutableStateOf(Frequency.ONCE) }
    System.out.println(selectedFrequency.javaClass.kotlin)
    var amtPerDosage by remember { mutableStateOf("") }
    val totalDosage = (amtPerDosage.toIntOrNull() ?: 0) * selectedFrequency.getCount()
    System.out.println(totalDosage.javaClass.kotlin)
    var medicationType by remember { mutableStateOf(MedicationType.TABLET) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    val selectedDate = datePickerState.selectedDateMillis?.let { java.util.Date(it) } ?: java.util.Date()
    val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    var expandFrequencyDropdown by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(showSuccessMessage) {
        Log.d("AddMedicationScreen", "LaunchedEffect started, waiting for medicationSavedEvent")
        if(showSuccessMessage) {
            Log.d("AddMedicationScreen", "Collected medicationSavedEvent")
            Toast.makeText(
                context.applicationContext,
                "Medication added successfully",
                Toast.LENGTH_SHORT
            ).show()

            Log.d("AddMedicationScreen", "About to call onNavigateBack")
            onNavigateBack()
            showSuccessMessage = false
            Log.d("AddMedicationScreen", "Called onNavigateBack")

        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add Medication")},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding).padding(16.dp).verticalScroll(rememberScrollState())
        ) {

            Text("Medication", fontWeight = FontWeight.Medium)
            OutlinedTextField(
                value = medicationName,
                onValueChange = { medicationName = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(4.dp))

            Column {
                Text("Frequency", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = selectedFrequency.toString(),
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expandFrequencyDropdown = true }) {
                            Icon(Icons.Default.ArrowDropDown, "Select frequency")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expandFrequencyDropdown,
                    onDismissRequest = { expandFrequencyDropdown = false }
                ) {
                    Frequency.entries.forEach { frequency ->
                        DropdownMenuItem(
                            text = { Text(frequency.name) },
                            onClick = {
                                selectedFrequency = frequency
                                expandFrequencyDropdown = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))

            Text("Dosage", fontWeight = FontWeight.Medium)
            OutlinedTextField(
                value = amtPerDosage,
                onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) amtPerDosage = it },
                label = { Text("Dosage") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(4.dp))

            Column {
                Text("Start Date", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = dateFormatter.format(selectedDate),
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.DateRange, "Select date")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        Button(onClick = { showDatePicker = false }) {
                            Text("OK")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState, showModeToggle = false)
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))

            Column {
                Text("Frequency", fontWeight = FontWeight.Medium)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth().height(250.dp).padding(horizontal = 0.dp),
                    userScrollEnabled = true
                ) {
                    items(MedicationType.entries) { type ->
                        MedicationTypeBox(
                            type = type,
                            isSelected = type == medicationType,
                            onSelect = { medicationType = type }
                        )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val newMedication = Medication(
                        name = medicationName,
                        frequency = selectedFrequency,
                        amtPerDosage = amtPerDosage.toInt(),
                        totalDosage = totalDosage,
                        startDate = selectedDate,
                        type = medicationType,
                    )
                    try {
                        coroutineScope.launch {
                            viewModel.addMedication(newMedication)
                            showSuccessMessage = true
                        }
                    } catch(e: Exception) {
                        Log.e("AddMedication", "Error adding medication", e)
                    }
                },
                enabled = medicationName.isNotBlank() && amtPerDosage.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Medication")
            }
        }
        }
    }
}

@Composable
private fun MedicationTypeBox(
    type: MedicationType,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surface
            )
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onSelect)
            .padding(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(
                    when (type) {
                        MedicationType.TABLET -> R.drawable.ic_tablet
                        MedicationType.CAPSULE -> R.drawable.ic_capsule
                        MedicationType.SYRUP -> R.drawable.ic_syrup
                        MedicationType.DROPS -> R.drawable.ic_drops
                        MedicationType.SPRAY -> R.drawable.ic_spray
                        MedicationType.GEL -> R.drawable.ic_gel
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(
                    when (type) {
                        MedicationType.TABLET -> R.string.tablet
                        MedicationType.CAPSULE -> R.string.capsule
                        MedicationType.SYRUP -> R.string.type_syrup
                        MedicationType.DROPS -> R.string.drops
                        MedicationType.SPRAY -> R.string.spray
                        MedicationType.GEL -> R.string.gel
                    }
                ),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddMedicationScreenPreview() {
    AddMedicationScreen(onNavigateBack = {})
}