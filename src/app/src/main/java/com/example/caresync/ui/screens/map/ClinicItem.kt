package com.example.caresync.ui.screens.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ClinicItem(
    clinic: Clinic,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    modifier: Modifier = Modifier
) {

    val cardHeight by animateDpAsState(
        targetValue = if (isExpanded) 260.dp else 130.dp,
        animationSpec = tween(durationMillis = 300),
        label = "CardHeightAnimation"
    )

    Card(
        modifier = modifier
            .width(260.dp)
            .height(cardHeight)
            .clickable { onToggleExpand() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Clinic Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = clinic.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = clinic.address,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                        color = Color.Gray
                    )
                }
            }

            if (isExpanded) {
                ClinicDetails(clinic)
            }
        }
    }
}

@Composable
fun ClinicDetails(clinic: Clinic) {
    Column {
        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Stock: ${clinic.stockStatus}",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            color = when (clinic.stockStatus) {
                StockStatus.AVAILABLE -> Color.Green
                StockStatus.LIMITED -> Color.Yellow
                StockStatus.OUT_OF_STOCK -> Color.Red
            }
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Contact: ${clinic.phoneNumber}",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            color = MaterialTheme.colorScheme.primary
        )

        clinic.website?.let {
            Text(
                text = "Visit Website",
                style = MaterialTheme.typography.bodySmall.copy(
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 10.sp
                ),
                modifier = Modifier.clickable { /* Open URL */ }
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Available Medications:",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
        )
        clinic.availableMedications.forEach { med ->
            Text(
                text = "- $med",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp),
                color = Color.Gray
            )
        }
    }
}
