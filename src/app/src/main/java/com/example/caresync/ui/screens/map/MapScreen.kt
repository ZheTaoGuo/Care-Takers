package com.example.caresync.ui.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.caresync.BottomNavItem
import com.example.caresync.CareSyncApp
import com.example.caresync.CenteredText

@Composable
fun MapScreen() { CenteredText("Map\nScreen") }


@Preview
@Composable
fun MapScreenPreview() {
    CareSyncApp(BottomNavItem.Map.route)
}
