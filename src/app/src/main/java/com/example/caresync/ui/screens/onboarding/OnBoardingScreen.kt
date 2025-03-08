package com.example.caresync.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OnBoardingScreen(onLoginCareGiver: ()->Unit, onLoginPatient: ()->Unit) {
    var showLogin by remember { mutableStateOf(true) } // Toggles between login & register

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showLogin) {
            LoginScreen(
                onNavigateToRegister = { showLogin = false },
                onLoginCareGiver = onLoginCareGiver,
                onLoginPatient = onLoginPatient
            )
        } else {
            RegisterScreen(
                onNavigateToLogin = { showLogin = true },
                onLoginCareGiver = onLoginCareGiver,
                onLoginPatient = onLoginPatient
            )
        }
    }
}

@Preview
@Composable
fun OnBoardingPreview() {
    OnBoardingScreen({}, {})
}