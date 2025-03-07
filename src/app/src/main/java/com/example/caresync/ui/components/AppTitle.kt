package com.example.caresync.ui.components

import androidx.compose.foundation.layout.*
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
fun AppTitle() {
    val fontFamily = CustomFont.Commissioner()

    Text(
        text = "CARESYNC",
        style = TextStyle(
            fontFamily = fontFamily,
            fontSize = 16.sp, // Adjust font size as needed
            letterSpacing = 0.4.em, // 40% letter spacing
            color = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        textAlign = TextAlign.Center
    )
}
