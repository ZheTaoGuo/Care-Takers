package com.example.caresync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import kotlinx.coroutines.delay
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.caresync.ui.screens.caregiver.CareGiverScreen
import com.example.caresync.ui.screens.onboarding.OnBoardingScreen
import com.example.caresync.ui.theme.CareSyncTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            var showSplash by remember { mutableStateOf(true) }
            var loginState by remember { mutableStateOf(LoginState.UNKNOWN) }

            // Simulate loading for 2 seconds
            LaunchedEffect(Unit) {
                delay(1300)
                showSplash = false
                // TODO: Maybe get the cached login state of the app?
            }
            CareSyncTheme {
                Crossfade(targetState = showSplash, label = "Screen Transition") { isSplash ->
                    if (isSplash) {
                        SplashScreen()
                    } else {
                        when (loginState) {
                            LoginState.PATIENT -> CareSyncApp(
                                onLogout = { loginState = LoginState.UNKNOWN }
                            )

                            LoginState.CAREGIVER -> CareGiverScreen(
                                onLogout = { loginState = LoginState.UNKNOWN }
                            )

                            LoginState.UNKNOWN -> OnBoardingScreen(
                                onLoginCareGiver = { loginState = LoginState.CAREGIVER },
                                onLoginPatient = { loginState = LoginState.PATIENT }
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class LoginState {
    UNKNOWN, CAREGIVER, PATIENT
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


