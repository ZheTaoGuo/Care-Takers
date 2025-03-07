package com.example.caresync.ui.screens.mood

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.caresync.BottomNavItem
import com.example.caresync.CareSyncApp

@Composable
fun MoodScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Mood\nScreen", textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview
@Composable
fun MoodScreenPreview() {
    CareSyncApp({}, BottomNavItem.Mood.route)
}