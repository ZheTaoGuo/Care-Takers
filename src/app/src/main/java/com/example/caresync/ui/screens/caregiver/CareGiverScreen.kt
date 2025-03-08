package com.example.caresync.ui.screens.caregiver

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.caresync.R
import com.example.caresync.ui.components.AppTitle
import com.example.caresync.ui.components.DashboardCard
import com.example.caresync.ui.theme.CustomFont

@Composable
fun CareGiverScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // App Title
            AppTitle()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                contentAlignment = Alignment.TopStart
            ) {
                // Background Leaf Image (Correctly Positioned in Top-Left)
                Image(
                    painter = painterResource(id = R.drawable.leaf),
                    contentDescription = "Decorative Image",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .offset(x = (-32).dp, y = (16).dp),
                    alpha = 0.6f
                )

                // Title with Auto-Wrapping & Bold Styling
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

            // Dashboard Options
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val navController = rememberNavController()
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // First row (Single large card, but still a square)
                    DashboardCard(
                        title = "Profile",
                        backgroundColor = Color(0xFFBEC9F9),
                        iconBackgroundColor = Color(0xFF9EACE6),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate("profile_screen") }
                    )

                    // Second row (Two square cards in a row)
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
}

@Preview
@Composable
fun CareGiverPreview() {
    CareGiverScreen({})
}