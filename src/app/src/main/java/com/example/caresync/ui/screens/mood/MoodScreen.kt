package com.example.caresync.ui.screens.mood

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.caresync.BottomNavItem
import com.example.caresync.CareSyncApp
import com.example.caresync.CenteredText

@Composable
fun MoodScreen() { CenteredText("Mood\nScreen") }

@Preview
@Composable
fun MoodScreenPreview() {
    CareSyncApp(BottomNavItem.Mood.route)
}