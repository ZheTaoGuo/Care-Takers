package com.example.caresync.ui.screens

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.caresync.ui.screens.calendar.CalendarScreen
import com.example.caresync.ui.screens.healthcard.HealthCardScreen
import com.example.caresync.ui.screens.map.MapScreen
import com.example.caresync.ui.screens.medication.AddMedicationScreen
import com.example.caresync.ui.screens.medication.MedicationScreen
import com.example.caresync.ui.screens.mood.MoodScreen
import com.example.caresync.ui.screens.settings.SettingsScreen

@Composable
// TODO: Need to decide what is the default destination at launch.
fun CareSyncPatientAppScreens(
    onLogout: () -> Unit,
    startDestination: String = BottomNavItem.Medication.route,
    initialShowSettings: Boolean = false
) {
    val navController = rememberNavController()
    var showSettings by remember { mutableStateOf(initialShowSettings) }

    if (showSettings) {
        SettingsScreen(
            onClose = { showSettings = false },
            onLogout = onLogout
        )
    } else {
        Scaffold(
            topBar = {
                val currentRoute = currentRoute(navController) ?: "CareSync"
                TopBar(navController) { showSettings = true }
            },
            bottomBar = { BottomNavBar(navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding),
                // NOTE: Remove transitions between tabs. Adjust as we see fit.
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                composable(BottomNavItem.Medication.route) { MedicationScreen(
                    navController = navController
                ) }
                composable(route = "addMedication") {
                    AddMedicationScreen(
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                composable(BottomNavItem.Calendar.route) { CalendarScreen() }
                composable(BottomNavItem.HealthCard.route) { HealthCardScreen() }
                composable(BottomNavItem.Mood.route) { MoodScreen() }
                composable(BottomNavItem.Map.route) { MapScreen() }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Medication,
        BottomNavItem.Calendar,
        BottomNavItem.HealthCard,
        BottomNavItem.Mood,
        BottomNavItem.Map
    )

    NavigationBar {
        val currentRoute = currentRoute(navController)
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.shortTitle) },
                label = { Text(item.shortTitle) }
            )
        }
    }
}

// Utility to get current route
@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

// Enum-like class for navigation items
sealed class BottomNavItem(val route: String, val shortTitle: String, val topBarTitle: String, val icon: Int) {
    // TODO: TO ALL Replace these icons with what you would want for your tab. Make sure it's a painter resource id
    data object Medication : BottomNavItem("medication", "Med", "Medication", android.R.drawable.ic_menu_agenda)
    data object Calendar : BottomNavItem("calendar", "Cal", "Calendar", android.R.drawable.ic_menu_my_calendar)
    data object HealthCard : BottomNavItem("healthcard", "HCard", "Health Card", android.R.drawable.ic_menu_info_details)
    data object Mood : BottomNavItem("mood", "Mood", "Mood", android.R.drawable.ic_menu_camera)
    data object Map : BottomNavItem("map", "Map", "Map", android.R.drawable.ic_menu_mapmode)

    companion object {
        // Helper function to get the display title from the route
        fun getTitleFromRoute(route: String): String {
            return when (route) {
                Medication.route -> Medication.topBarTitle
                Calendar.route -> Calendar.topBarTitle
                HealthCard.route -> HealthCard.topBarTitle
                Mood.route -> Mood.topBarTitle
                Map.route -> Map.topBarTitle
                else -> "CareSync"
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, onSettingsClick: () -> Unit) {
    val currentRoute = currentRoute(navController) ?: "CareSync"
    val displayTitle = BottomNavItem.getTitleFromRoute(currentRoute)

    CenterAlignedTopAppBar(
        title = { Text(displayTitle, style = MaterialTheme.typography.titleLarge) },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    )
}



@Preview
@Composable
fun CareSyncAppPreview() {
    CareSyncPatientAppScreens({})
}