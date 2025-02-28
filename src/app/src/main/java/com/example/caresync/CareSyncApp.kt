package com.example.caresync

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.caresync.ui.screens.CalendarScreen
import com.example.caresync.ui.screens.HealthCardScreen
import com.example.caresync.ui.screens.MapScreen
import com.example.caresync.ui.screens.MedicationScreen
import com.example.caresync.ui.screens.MoodScreen

@Composable
// TODO: Need to decide what is the default destination at launch.
fun CareSyncApp(startDestination: String = BottomNavItem.Medication.route) {
    val navController = rememberNavController()
    Scaffold(
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
            composable(BottomNavItem.Medication.route) { MedicationScreen() }
            composable(BottomNavItem.Calendar.route) { CalendarScreen() }
            composable(BottomNavItem.HealthCard.route) { HealthCardScreen() }
            composable(BottomNavItem.Mood.route) { MoodScreen() }
            composable(BottomNavItem.Map.route) { MapScreen() }
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
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(item.title) }
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
sealed class BottomNavItem(val route: String, val title: String, val icon: Int) {
    // TODO: TO ALL Replace these icons with what you would want for your tab. Make sure it's a painter resource id
    data object Medication : BottomNavItem("medication", "Med", android.R.drawable.ic_menu_agenda)
    data object Calendar : BottomNavItem("calendar", "Cal", android.R.drawable.ic_menu_my_calendar)
    data object HealthCard : BottomNavItem("healthcard", "HCard", android.R.drawable.ic_menu_info_details)
    data object Mood : BottomNavItem("mood", "Mood", android.R.drawable.ic_menu_camera)
    data object Map : BottomNavItem("map", "Map", android.R.drawable.ic_menu_mapmode)
}


// TODO: Remove this in future once everyone has implemented their screens
@Composable
fun CenteredText(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text, style = MaterialTheme.typography.headlineMedium)
    }
}






@Preview
@Composable
fun CareSyncAppPreview() {
    CareSyncApp()
}