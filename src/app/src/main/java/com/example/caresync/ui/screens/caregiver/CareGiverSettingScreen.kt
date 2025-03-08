package com.example.caresync.ui.screens.caregiver

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.caresync.R
import com.example.caresync.ui.components.AppTitle
import com.example.caresync.ui.theme.CustomFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CareGiverSettingsScreen(navController: NavHostController, onLogout: () -> Unit) {
    val viewModel: ProfileViewModel = viewModel()
    val quicksand = CustomFont.Quicksand() // Use Quicksand font

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTitle()

        // Full-width Profile Image
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clickable { /* Future Profile Picture Change */ }
        )

        // User Name & Role
        Text(
            text = viewModel.userName,
            fontSize = 22.sp,
            fontWeight = FontWeight.W300,
            fontFamily = quicksand,
            color = Color.Black
        )
        Text(
            text = viewModel.userRole,
            fontSize = 16.sp,
            fontWeight = FontWeight.W300,
            fontFamily = quicksand,
            color = Color.Gray
        )

        // Edit Profile Button (Right-Aligned)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { viewModel.showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF26767F)),
                modifier = Modifier.height(40.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Edit Profile",
                    fontSize = 14.sp,
                    color = Color.White,
                    fontFamily = quicksand,
                    fontWeight = FontWeight.W300
                )
            }
        }

        // Editable User Info
        ProfileInfoCard(
            email = viewModel.email,
            dob = viewModel.dob,
            address = viewModel.address,
            fontFamily = quicksand
        )

        // Logout Button
        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF26767F)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Log Out",
                fontSize = 16.sp,
                color = Color.White,
                fontFamily = quicksand,
                fontWeight = FontWeight.W300
            )
        }

        if (viewModel.showDialog) {
            EditProfileDialog(viewModel, quicksand)
        }
    }
}


@Composable
fun ProfileInfoCard(email: String, dob: String, address: String, fontFamily: androidx.compose.ui.text.font.FontFamily) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Email", fontSize = 14.sp, color = Color.Black, fontFamily = fontFamily, fontWeight = FontWeight.W300)
            Text(text = email, fontSize = 16.sp, color = Color(0xFF26767F), fontFamily = fontFamily, fontWeight = FontWeight.W300)

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Date of Birth", fontSize = 14.sp, color = Color.Black, fontFamily = fontFamily, fontWeight = FontWeight.W300)
            Text(text = dob, fontSize = 16.sp, color = Color.Gray, fontFamily = fontFamily, fontWeight = FontWeight.W300)

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Address", fontSize = 14.sp, color = Color.Black, fontFamily = fontFamily, fontWeight = FontWeight.W300)
            Text(text = address, fontSize = 16.sp, color = Color(0xFF26767F), fontFamily = fontFamily, fontWeight = FontWeight.W300)
        }
    }
}

@Composable
fun EditProfileDialog(viewModel: ProfileViewModel, fontFamily: androidx.compose.ui.text.font.FontFamily) {
    AlertDialog(
        onDismissRequest = { viewModel.showDialog = false },
        title = { Text(text = "Edit Profile", fontFamily = fontFamily, fontWeight = FontWeight.W300) },
        text = {
            Column {
                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    label = { Text("Email", fontFamily = fontFamily, fontWeight = FontWeight.W300) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = viewModel.dob,
                    onValueChange = { viewModel.dob = it },
                    label = { Text("Date of Birth", fontFamily = fontFamily, fontWeight = FontWeight.W300) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = viewModel.address,
                    onValueChange = { viewModel.address = it },
                    label = { Text("Address", fontFamily = fontFamily, fontWeight = FontWeight.W300) }
                )
            }
        },
        confirmButton = {
            Button(onClick = { viewModel.showDialog = false }) {
                Text("Save", fontFamily = fontFamily, fontWeight = FontWeight.W300)
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.showDialog = false }) {
                Text("Cancel", fontFamily = fontFamily, fontWeight = FontWeight.W300)
            }
        }
    )
}


