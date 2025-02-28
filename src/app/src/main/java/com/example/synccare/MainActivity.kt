package com.example.synccare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.synccare.ui.theme.SyncCareTheme
import kotlinx.coroutines.delay
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            var showSplash by remember { mutableStateOf(true) }

            // Simulate loading for 2 seconds
            LaunchedEffect(Unit) {
                delay(1300)
                showSplash = false
            }

            Crossfade(targetState = showSplash, label = "Screen Transition") { isSplash ->
                if (isSplash) {
                    SplashScreen()
                } else {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF26767F)), // Custom background color
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.caresync_high_resolution_logo),
            contentDescription = "Splash Logo"
        )
    }
}


@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Ensure it's a clean white screen
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to CareSync!", style = MaterialTheme.typography.headlineLarge)
    }
}