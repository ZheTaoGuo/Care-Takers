package com.example.caresync.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.caresync.R
import com.example.caresync.ui.theme.CustomFont

@Composable
fun DashboardCard(title: String, backgroundColor: Color, iconBackgroundColor: Color,
                  modifier: Modifier = Modifier, onClick: () -> Unit) {
    val quicksand = CustomFont.Quicksand()
    val cardSize = 150.dp
    val iconSize = 32.dp
    val circleSize = 42.dp

    Card(
        modifier = modifier
            .size(cardSize)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.W300,
                fontFamily = quicksand,
                color = Color.Black,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(circleSize)
                    .background(iconBackgroundColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.line_out_light),
                    contentDescription = "Go to $title",
                    modifier = Modifier.size(iconSize),
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardCardPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // First row (Single large card, but still a square)
        DashboardCard(
            title = "Profile",
            backgroundColor = Color(0xFFBEC9F9),
            iconBackgroundColor = Color(0xFF9EACE6),
            modifier = Modifier.fillMaxWidth(),
            onClick = { }
        )

        // Second row (Two square cards in a row)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            DashboardCard(
                title = "Inventory",
                backgroundColor = Color(0xFFC8DFDC),
                iconBackgroundColor = Color(0xFFAFD6D1),
                modifier = Modifier.weight(1f),
                onClick = {  }
            )
            DashboardCard(
                title = "Monitoring",
                backgroundColor = Color(0xFFEFE1DA),
                iconBackgroundColor = Color(0xFFE6CBBD),
                modifier = Modifier.weight(1f),
                onClick = { }
            )
        }
    }
}
