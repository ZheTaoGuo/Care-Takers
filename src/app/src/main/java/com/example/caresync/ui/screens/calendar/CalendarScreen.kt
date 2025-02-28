package com.example.caresync.ui.screens.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.caresync.BottomNavItem
import com.example.caresync.CareSyncApp
import com.example.caresync.CenteredText

@Composable
fun CalendarScreen() { CenteredText("Calendar\nScreen") }

@Preview
@Composable
fun CalendarScreenPreview() {
    CareSyncApp(BottomNavItem.Calendar.route)
}
