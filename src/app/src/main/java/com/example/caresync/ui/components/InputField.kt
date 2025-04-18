package com.example.synccare.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    inputType: KeyboardType, // Text, Password, Number, etc.
    isPassword: Boolean = false,
    leadingIcon: @Composable () -> Unit // Pass Material Icon composable
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)) // Capsule shape
            .background(Color(0xFF9EC3C7)) // Background color
            .padding(horizontal = 16.dp, vertical = 0.dp).height(
                46.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Display the leading icon
        leadingIcon()

        Spacer(modifier = Modifier.width(12.dp)) // Space between icon and text field

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color(0xFF6F6C6C), fontSize = 14.sp) }, // Custom placeholder color
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontSize = 14.sp
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = inputType,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.weight(1f).padding(vertical = 0.dp)
        )

        // Toggle password visibility for password fields
        if (isPassword) {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                    contentDescription = "Toggle Password Visibility",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun InputFieldPreview(){
    var email = "hi"
    InputField(
        value = email,
        onValueChange = { email = it },
        placeholder = "Email",
        leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = "Email Icon",
            modifier = Modifier.size(18.dp)
        ) },
        inputType = KeyboardType.Email
    )
}