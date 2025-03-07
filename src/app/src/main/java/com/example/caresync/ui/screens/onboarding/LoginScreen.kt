package com.example.caresync.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
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
fun LoginScreen(onNavigateToRegister: () -> Unit, onLoginCareGiver: ()->Unit, onLoginPatient: ()->Unit) {
    fun login(email: String) {
        if (email.contains("caregiver", ignoreCase = true)) {
            onLoginCareGiver()
        } else {
            onLoginPatient()
        }
    }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val quicksand = CustomFont.Quicksand()

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
                painter = painterResource(id = R.drawable.login), // Change to actual logo
                contentDescription = "Login Illustration",
                modifier = Modifier.size(200.dp)
            )

            // Tagline & Description
            Text(text = "Log in. Stay healthy.", fontSize = 24.sp, color = Color.Black, style = TextStyle(
                fontFamily = quicksand, fontWeight = FontWeight(300)
            ))
            Text(
                text = "Access your medications and caregiver support in one place.",
                fontSize = 15.sp,
                modifier = Modifier.width(276.dp).padding(top = 12.dp),
                style = TextStyle(
                    fontFamily = quicksand, fontWeight = FontWeight(300),
                    textAlign = TextAlign.Center
                )
            )

            // Card for login form
            BorderedCard {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome Back!",
                        fontSize = 20.sp,
                        fontFamily = CustomFont.Quicksand(),
                        fontWeight = FontWeight.W300,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Email Input Field
                    InputField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Email",
                        leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = "Email Icon") },
                        inputType = KeyboardType.Email
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

                    // Forgot Password Link
                    TextButton(
                        onClick = { onForgotPassword() },
                        modifier = Modifier.align(Alignment.Start).offset(y = (-8).dp)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append("Forgot your password? ") // Black color
                                withStyle(style = SpanStyle(color = Color(0xFF26767F))) {
                                    append("Reset here!")
                                }
                            },
                            fontSize = 15.sp,
                            fontFamily = CustomFont.Quicksand(),
                            fontWeight = FontWeight.W300,
                            color = Color.Black
                        )
                    }


                    // Login Button
                    Button(
                        onClick = { login(email) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF26767F)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(24.dp))
                    ) {
                        Text(text = "Log In", fontSize = 16.sp, color = Color.White)
                    }
                    // Register Link
                    TextButton(
                        onClick = onNavigateToRegister,
                        modifier = Modifier.align(Alignment.Start).offset(y = (-8).dp)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append("Don't have an account yet? ")
                                withStyle(style = SpanStyle(color = Color(0xFF26767F))) {
                                    append("Register now!")
                                }
                            },
                            fontSize = 15.sp,
                            fontFamily = CustomFont.Quicksand(),
                            fontWeight = FontWeight.W300,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

fun onForgotPassword(){
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen({}, {}, {})
}