package com.example.caresync.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.style.TextAlign
import com.example.caresync.R
import com.example.caresync.ui.theme.CustomFont

@Composable
fun AppTitle(onBackClick: (() -> Unit)? = null) {
    val fontFamily = CustomFont.Commissioner()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Show Back Button if `onBackClick` is provided
        if (onBackClick != null) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }

        Text(
            text = "CARESYNC",
            style = TextStyle(
                fontFamily = fontFamily,
                fontSize = 16.sp,
                letterSpacing = 0.4.em,
                color = Color.Black
            ),
            modifier = Modifier
                .weight(1f) // Centers text while back button stays aligned to the left
                .padding(end = if (onBackClick != null) 48.dp else 0.dp), // Adjust padding when back button is shown
            textAlign = TextAlign.Center
        )
    }
}

