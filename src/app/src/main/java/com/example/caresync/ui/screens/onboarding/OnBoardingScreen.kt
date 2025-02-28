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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OnBoardingScreen(onLoginCareGiver: ()->Unit, onLoginPatient: ()->Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        Spacer(Modifier.weight(1f))

        Text("On\nBoarding\nScreen",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center)

        Spacer(Modifier.weight(2f))

        Button(
            onClick = onLoginCareGiver
        ) {
            Text("Login as Care Giver")
        }

        Button(
            onClick = onLoginPatient
        ) {
            Text("Login as Patient")
        }

        Spacer(Modifier.weight(1f))
    }
}

@Preview
@Composable
fun OnBoardingPreview() {
    OnBoardingScreen({}, {})
}