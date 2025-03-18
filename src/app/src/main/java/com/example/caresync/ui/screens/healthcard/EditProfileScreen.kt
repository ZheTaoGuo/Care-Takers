package com.example.caresync.ui.screens.healthcard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EditProfileScreen(
    section: String,
    viewModel: HealthCardViewModel = viewModel(factory = HealthCardViewModel.factory),
    onBack: () -> Unit
) {

    val userProfileState by viewModel.getUserProfile().collectAsState(initial = null)
    if (userProfileState == null) {
        Text("Loading...", modifier = Modifier.fillMaxSize())
        return
    }

    val userProfile = userProfileState!!

    val emergencyContacts by viewModel.getEmergencyContacts().collectAsState(initial = emptyList())


    LaunchedEffect(userProfile) {
        println("DEBUG: Received UserProfile: $userProfile")
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Edit $section", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        when (section) {
            "Personal Information" -> {
                var name by remember { mutableStateOf(userProfile.name) }
                var dob by remember { mutableStateOf(userProfile.dateOfBirth) }
                var language by remember { mutableStateOf(userProfile.primaryLanguage) }
//                var photoUri by remember { mutableStateOf(userProfile.photoUri) }
                var organDonation by remember { mutableStateOf(userProfile.organDonation) }

//                val imagePickerLauncher = rememberLauncherForActivityResult(
//                    contract = ActivityResultContracts.GetContent()
//                ) { uri: Uri? -> photoUri = uri }

                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = dob, onValueChange = { dob = it }, label = { Text("DOB") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = language, onValueChange = { language = it }, label = { Text("Language") }, modifier = Modifier.fillMaxWidth())

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Checkbox(checked = organDonation, onCheckedChange = { organDonation = it })
                    Text("Organ Donor")
                }

//                Button(onClick = { imagePickerLauncher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
//                    Text("Change Profile Photo")
//                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = onBack, modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            viewModel.updateUserProfileInfo(name, dob, language, organDonation)
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

            "Emergency Contacts" -> {
                if (emergencyContacts.isEmpty()) {
                    Text(
                        text = "No contacts added",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        itemsIndexed(emergencyContacts) { index, contact ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${contact.relationship}: ${contact.name} (${contact.phoneNumber})",
                                        modifier = Modifier.weight(1f),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    IconButton(onClick = { viewModel.removeEmergencyContact(contact) }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Remove Contact",
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.size(8.dp))

                var relationship by remember { mutableStateOf("") }
                var contactName by remember { mutableStateOf("") }
                var phoneNumber by remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = relationship,
                        onValueChange = { relationship = it },
                        label = { Text("Relationship") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = contactName,
                        onValueChange = { contactName = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(
                            onClick = {
                                if (relationship.isNotBlank() && contactName.isNotBlank() && phoneNumber.isNotBlank()) {
                                    viewModel.addEmergencyContact(
                                        relationship,
                                        contactName,
                                        phoneNumber
                                    )
                                }
                            },
                            modifier = Modifier.weight(1f).padding(end = 8.dp)
                        ) {
                            Text("Add Contact")
                        }
                        Button(onClick = onBack, modifier = Modifier.weight(1f)) {
                        Text("Save")
                        }
                    }
            }
        }
    }
}
