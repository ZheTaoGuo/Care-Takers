package com.example.caresync.ui.screens.healthcard

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditProfileScreen(
    section: String,
    viewModel: HealthCardViewModel,
    onBack: () -> Unit
) {
    val userProfile by viewModel.userProfile.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Edit $section", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        when (section) {
            "Photo and Information" -> {
                var name by remember { mutableStateOf(userProfile.name) }
                var dob by remember { mutableStateOf(userProfile.dateOfBirth) }
                var language by remember { mutableStateOf(userProfile.primaryLanguage) }
                var photoUri by remember { mutableStateOf(userProfile.photoUri) }
                var organDonation by remember { mutableStateOf(userProfile.organDonation) }

                val imagePickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? -> photoUri = uri }

                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = dob, onValueChange = { dob = it }, label = { Text("DOB") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = language, onValueChange = { language = it }, label = { Text("Language") }, modifier = Modifier.fillMaxWidth())

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Checkbox(checked = organDonation, onCheckedChange = { organDonation = it })
                    Text("Organ Donor")
                }

                Button(onClick = { imagePickerLauncher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Change Profile Photo")
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = onBack, modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            viewModel.updateUserProfileInfo(name, dob, language, photoUri, organDonation)
                            onBack()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save")
                    }
                }
            }

            "Pregnancy" -> {
                var pregnancy by remember { mutableStateOf(userProfile.pregnancy) }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Checkbox(checked = pregnancy, onCheckedChange = { pregnancy = it })
                    Text("Pregnant")
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = onBack, modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            viewModel.updatePregnancyStatus(pregnancy)
                            onBack()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save")
                    }
                }
            }

            "Allergies" -> {
                var allergies by remember { mutableStateOf(userProfile.allergies) }

                OutlinedTextField(value = allergies, onValueChange = { allergies = it }, label = { Text("Allergies") }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.size(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = onBack, modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            viewModel.updateAllergies(allergies)
                            onBack()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save")
                    }
                }
            }

            "Conditions" -> {
                var conditions by remember { mutableStateOf(userProfile.conditions) }

                OutlinedTextField(value = conditions, onValueChange = { conditions = it }, label = { Text("Medical Conditions") }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.size(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = onBack, modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            viewModel.updateMedicalConditions(conditions)
                            onBack()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save")
                    }
                }
            }

            "Additional Information" -> {
                var height by remember { mutableStateOf(userProfile.height.toString()) }
                var weight by remember { mutableStateOf(userProfile.weight.toString()) }
                var bloodType by remember { mutableStateOf(userProfile.bloodType) }

                OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("Height (cm)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("Weight (kg)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = bloodType, onValueChange = { bloodType = it }, label = { Text("Blood Type") }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.size(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = onBack, modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            viewModel.updateAdditionalInfo(
                                height.toDoubleOrNull() ?: userProfile.height,
                                weight.toDoubleOrNull() ?: userProfile.weight,
                                bloodType
                            )
                            onBack()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}
