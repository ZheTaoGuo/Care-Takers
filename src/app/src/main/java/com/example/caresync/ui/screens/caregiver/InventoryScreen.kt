package com.example.caresync.ui.screens.caregiver

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.caresync.ui.components.AppTitle

@Composable
fun InventoryScreen(navController: NavHostController, viewModel: InventoryViewModel = viewModel()) {
    Scaffold(
        topBar = { AppTitle(onBackClick = { navController.navigate("caregiver_dashboard") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Store Info
            StoreSection(viewModel.storeName, viewModel.storeDistance)

            Spacer(modifier = Modifier.height(16.dp))

            // Medicine List with Checklist
            MedicineChecklist(viewModel)
        }
    }
}

@Composable
fun StoreSection(storeName: String, storeDistance: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD9E3F0))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Available at:",
                fontSize = 14.sp,
                fontWeight = FontWeight.W300,
                color = Color.Black
            )
            Text(
                text = storeName,
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                color = Color.Black
            )
            Text(
                text = storeDistance,
                fontSize = 14.sp,
                fontWeight = FontWeight.W300,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MedicineChecklist(viewModel: InventoryViewModel) {
    Column {
        Text(
            text = "Medicines to Buy",
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            color = Color.Black
        )

        viewModel.medicines.forEachIndexed { index, medicine ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { viewModel.toggleMedicineChecked(index) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = medicine.isChecked,
                    onCheckedChange = { viewModel.toggleMedicineChecked(index) }
                )
                Text(
                    text = medicine.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W300,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryScreenPreview() {
    InventoryScreen(rememberNavController())
}
