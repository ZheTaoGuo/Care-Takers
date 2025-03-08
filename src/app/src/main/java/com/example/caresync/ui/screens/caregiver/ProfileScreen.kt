package com.example.caresync.ui.screens.caregiver

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
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
fun ProfileScreen(navController: NavHostController) {
    val viewModel: PatientViewModel = viewModel()
    val quicksand = CustomFont.Quicksand()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { AppTitle() },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (viewModel.isLinked) {
                // Show Patient Profile
                PatientProfile(viewModel, quicksand)
            } else {
                // Show Link Account Prompt
                Text(
                    text = "Please link your account with your patient!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W300,
                    fontFamily = quicksand,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                // QR Code Scanner Button
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFF26767F), shape = CircleShape)
                        .clickable { viewModel.linkPatientProfile() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Scan QR Code",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PatientProfile(viewModel: PatientViewModel, quicksand: androidx.compose.ui.text.font.FontFamily) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Full-width Profile Image
        Image(
            painter = painterResource(id =viewModel.profilePicture),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
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
            color = Color.Black
        )

        // Patient Info Card
        ProfileInfoCard(
            email = viewModel.email,
            dob = viewModel.dob,
            address = viewModel.address,
            fontFamily = quicksand
        )
    }
}

