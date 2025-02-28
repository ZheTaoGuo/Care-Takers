package com.example.caresync.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.caresync.BottomNavItem
import com.example.caresync.CareSyncApp
import com.example.caresync.CenteredText

@Composable
fun HealthCardScreen() { CenteredText("Health\nCard\nScreen") }


@Preview
@Composable
fun HealthCardScreenPreview() {
    CareSyncApp(BottomNavItem.HealthCard.route)
}