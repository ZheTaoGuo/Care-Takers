package com.example.caresync.ui.screens.healthcard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.caresync.ui.screens.BottomNavItem
import com.example.caresync.ui.screens.CareSyncPatientAppScreens
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import com.example.caresync.model.UserProfile
import com.example.caresync.ui.theme.*

@Composable
fun HealthCardScreen(
    viewModel: HealthCardViewModel = viewModel(factory = HealthCardViewModel.factory)
) {
    var editSection by remember { mutableStateOf<String?>(null) }
    val userProfile by viewModel.getUserProfile().collectAsState(initial = UserProfile(
        id = 0,
        name = "",
        dateOfBirth = "",
        primaryLanguage = "",
        organDonation = false,
        pregnancy = false,
        allergies = "",
        conditions = "",
        height = 0.0,
        weight = 0.0,
        bloodType = "",
    ))
    val emergencyContacts by viewModel.getEmergencyContacts().collectAsState(initial = emptyList())

    if (editSection != null) {
        EditProfileScreen(
            section = editSection!!,
            viewModel = viewModel,
            onBack = { editSection = null }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LazyColumn( Modifier.fillMaxSize().padding(16.dp)) {
                item {
                    ProfileSection(
                        title = "Personal Information",
                        content = null,
                        onEdit = { editSection = "Personal Information" }
                    )
                    ProfileContent(userProfile = userProfile)
                }

                item {
                    ProfileSection(
                        title = "Pregnancy",
                        content = if (userProfile.pregnancy) "Yes" else "No",
                        onEdit = { editSection = "Pregnancy" }
                    )
                }

                item {
                    ProfileSection(
                        title = "Allergies",
                        content = userProfile.allergies,
                        onEdit = { editSection = "Allergies" }
                    )
                }

                item {
                    ProfileSection(
                        title = "Medical Conditions",
                        content = userProfile.conditions,
                        onEdit = { editSection = "Conditions" }
                    )
                }


                item {
                    ProfileSection(
                        title = "Emergency Contacts",
                        content = if (emergencyContacts.isEmpty()) "No contacts added"
                        else emergencyContacts.joinToString("\n") { "${it.relationship}: ${it.name} (${it.phoneNumber})" },
                        onEdit = { editSection = "Emergency Contacts" }
                    )
                }

                item {
                    ProfileSection(
                        title = "Additional Information",
                        content = "Height: ${userProfile.height} cm\nWeight: ${userProfile.weight} kg\n" +
                                "Blood Type: ${userProfile.bloodType} ",
                        onEdit = { editSection = "Additional Information" }
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Medication",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Purple40
                        )
                    }
                }

                item {
                    MedicationContent(viewModel = viewModel)
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun HealthCardScreenPreview() {
    CareSyncPatientAppScreens({}, BottomNavItem.HealthCard.route)
}