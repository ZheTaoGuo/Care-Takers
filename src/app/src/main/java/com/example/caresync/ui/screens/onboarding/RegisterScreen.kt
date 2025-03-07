package com.example.caresync.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caresync.R
import com.example.caresync.ui.components.AppTitle
import com.example.caresync.ui.components.BorderedCard
import com.example.caresync.ui.theme.CustomFont
import com.example.synccare.ui.components.InputField

@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit, onLoginCareGiver: ()->Unit, onLoginPatient: ()->Unit) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val quicksand = CustomFont.Quicksand()

    fun onRegister(email: String) {
        if (email.contains("caregiver", ignoreCase = true)) {
            onLoginCareGiver()
        } else {
            onLoginPatient()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTitle()

            // Logo & Illustration
            Image(
                painter = painterResource(id = R.drawable.register),
                contentDescription = "Register Illustration",
                modifier = Modifier.size(160.dp)
            )

            // Tagline & Description
            Text(
                text = "Join and stay cared for",
                fontSize = 24.sp,
                color = Color.Black,
                style = TextStyle(
                    fontFamily = quicksand, fontWeight = FontWeight.W300
                )
            )

            Text(
                text = "Create an account to manage medications and connect caregivers",
                fontSize = 12.sp,
                modifier = Modifier
                    .width(276.dp)
                    .padding(top = 12.dp),
                style = TextStyle(
                    fontFamily = quicksand,
                    fontWeight = FontWeight.W300,
                    textAlign = TextAlign.Center
                )
            )

            // Card for registration form
            BorderedCard {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome to CareSync!",
                        fontSize = 16.sp,
                        fontFamily = quicksand,
                        fontWeight = FontWeight.W300,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Email Input Field
                    InputField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Email",
                        leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = "Email Icon") },
                        inputType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Name Input Field
                    InputField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = "Name",
                        leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = "Name Icon") },
                        inputType = KeyboardType.Text
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Password Input Field
                    InputField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "Password",
                        inputType = KeyboardType.Password,
                        isPassword = true,
                        leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Password Icon") }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Confirm Password Input Field
                    InputField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = "Confirm Password",
                        inputType = KeyboardType.Password,
                        isPassword = true,
                        leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Confirm Password Icon") }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Register Button
                    Button(
                        onClick = { onRegister(email) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF26767F)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(24.dp))
                    ) {
                        Text(text = "Sign Up", fontSize = 16.sp, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Login Link
                    TextButton(
                        onClick = onNavigateToLogin,
                        modifier = Modifier.align(Alignment.Start)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append("Already have an account? ")
                                withStyle(style = SpanStyle(color = Color(0xFF26767F))) {
                                    append("Log in now!")
                                }
                            },
                            fontSize = 15.sp,
                            fontFamily = quicksand,
                            fontWeight = FontWeight.W300,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen({}, {}, {})
}
