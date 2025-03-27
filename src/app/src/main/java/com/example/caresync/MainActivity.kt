package com.example.caresync

import RequestExactAlarmPermission
import RequestNotificationPermission
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.caresync.datasource.LoginStateDataStore
import com.example.caresync.ui.screens.CareSyncPatientAppScreens
import com.example.caresync.ui.screens.caregiver.CareGiverScreen
import com.example.caresync.ui.screens.onboarding.OnBoardingScreen
import com.example.caresync.ui.theme.CareSyncTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            var showSplash by remember { mutableStateOf(true) }
            var loginState by remember { mutableStateOf(LoginState.UNKNOWN) }
            val notificationHandler = (context.applicationContext as CareSyncApplication).notificationHandler

            // Simulate loading for 2 seconds
            LaunchedEffect(Unit) {
                delay(1300)
                // TODO: Maybe get the cached login state of the app?
                LoginStateDataStore.getLoginState(context).collect { cachedState ->
                    loginState = cachedState
                    showSplash = false
                }
            }
            CareSyncTheme {
                RequestNotificationPermission() // API 33+ Notifications
                RequestExactAlarmPermission() // API 31+ AlarmManager

                Crossfade(targetState = showSplash, label = "Screen Transition") { isSplash ->
                    if (isSplash) {
                        SplashScreen()
                    } else {
                        if (true) {
                            Column {
                                Button(onClick = {
                                    notificationHandler.showSimpleNotification(
                                        title = "Simple Notification",
                                        message = "Message or text with notification"
                                    )
                                }) {
                                    Text("Show Notification")
                                }
                                Button(onClick = {
                                    val tenSecondsInFuture = Calendar.getInstance().apply {
                                        add(Calendar.SECOND, 10) // Schedule for 10 seconds later
                                    }
                                    notificationHandler.scheduleNotification(
                                        tenSecondsInFuture.time,
                                        "Custom Title",
                                        "Custom Message!!!"
                                    )
                                    Toast.makeText(
                                        context,
                                        "Notification Scheduled!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }) {
                                    Text("Schedule Notification (10s)")
                                }
                            }
                        } else {
                            when (loginState) {
                                LoginState.PATIENT -> CareSyncPatientAppScreens(
                                    onLogout = {
                                        loginState = LoginState.UNKNOWN
                                        CoroutineScope(Dispatchers.IO).launch {
                                            LoginStateDataStore.saveLoginState(
                                                context,
                                                LoginState.UNKNOWN
                                            )
                                        }
                                    }
                                )

                                LoginState.CAREGIVER -> CareGiverScreen(
                                    onLogout = {
                                        loginState = LoginState.UNKNOWN
                                        CoroutineScope(Dispatchers.IO).launch {
                                            LoginStateDataStore.saveLoginState(
                                                context,
                                                LoginState.UNKNOWN
                                            )
                                        }
                                    }
                                )

                                LoginState.UNKNOWN -> OnBoardingScreen(
                                    onLoginCareGiver = {
                                        loginState = LoginState.CAREGIVER
                                        CoroutineScope(Dispatchers.IO).launch {
                                            LoginStateDataStore.saveLoginState(
                                                context,
                                                LoginState.CAREGIVER
                                            )
                                        }
                                    },
                                    onLoginPatient = {
                                        loginState = LoginState.PATIENT
                                        CoroutineScope(Dispatchers.IO).launch {
                                            LoginStateDataStore.saveLoginState(
                                                context,
                                                LoginState.PATIENT
                                            )
                                        }
                                    }
                                )
                            }
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


