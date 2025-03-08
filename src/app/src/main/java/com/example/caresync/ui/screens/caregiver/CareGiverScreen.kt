package com.example.caresync.ui.screens.caregiver

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.caresync.R
import com.example.caresync.ui.components.AppTitle
import com.example.caresync.ui.components.DashboardCard
import com.example.caresync.ui.theme.CustomFont
import kotlinx.coroutines.launch

@Composable
fun CareGiverScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { CareGiverBottomNavBar(navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            CareGiverNavHost(navController, onLogout)
        }
    }
}

@Composable
fun CareGiverNavHost(navController: NavHostController, onLogout: () -> Unit) {
    NavHost(navController, startDestination = "caregiver_dashboard") {
        composable("caregiver_dashboard") { CareGiverDashboard(navController) }
        composable("profile_screen") { ProfileScreen(navController) }
        composable("inventory_screen") { InventoryScreen() }
        composable("monitoring_screen") { MonitoringScreen() }
        composable("caregiver_settings") { CareGiverSettingsScreen(navController, onLogout) }
    }
}

@Composable
fun CareGiverDashboard(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            AppTitle()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.leaf),
                    contentDescription = "Decorative Image",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .offset(x = (-32).dp, y = (16).dp),
                    alpha = 0.6f
                )

                Text(
                    text = buildAnnotatedString {
                        append("The right medicine, at the ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("right time")
                        }
                    },
                    fontSize = 36.sp,
                    fontWeight = FontWeight.W300,
                    fontFamily = CustomFont.Quicksand(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                DashboardCard(
                    title = "Profile",
                    backgroundColor = Color(0xFFBEC9F9),
                    iconBackgroundColor = Color(0xFF9EACE6),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate("profile_screen") }
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    DashboardCard(
                        title = "Inventory",
                        backgroundColor = Color(0xFFC8DFDC),
                        iconBackgroundColor = Color(0xFFAFD6D1),
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("inventory_screen") }
                    )
                    DashboardCard(
                        title = "Monitoring",
                        backgroundColor = Color(0xFFEFE1DA),
                        iconBackgroundColor = Color(0xFFE6CBBD),
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("monitoring_screen") }
                    )
                }
            }
        }
    }
}

@Composable
fun CareGiverBottomNavBar(navController: NavHostController) {
    val items = listOf(
        CareGiverNavItem("caregiver_dashboard", "Dashboard", Icons.Default.Dashboard),
        CareGiverNavItem("caregiver_settings", "Settings", Icons.Default.Settings)
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
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
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

// Data class for bottom nav items
data class CareGiverNavItem(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@Preview
@Composable
fun CareGiverPreview() {
    CareGiverScreen({})
}
