package com.example.caresync.ui.screens.caregiver

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.caresync.R
import com.example.caresync.ui.components.AppTitle
import com.example.caresync.ui.theme.CustomFont
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoringScreen(navController: NavHostController) {
    val viewModel: MonitoringViewModel = viewModel()
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { AppTitle(onBackClick = { navController.popBackStack() }) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Profile
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(50.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Ryan Chenkie",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = CustomFont.Quicksand()
                    )
                    Text(
                        text = "Teacher",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W300,
                        color = Color.Gray,
                        fontFamily = CustomFont.Quicksand()
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                // View by Dropdown
                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = {}
                ) {
                    TextButton(onClick = {}) {
                        Text(text = "View by: ${viewModel.selectedView}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Adherence") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Mood") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                0 -> AdherenceTab(viewModel)
                1 -> MoodTab(viewModel)
            }
        }
    }
}

@Composable
fun AdherenceTab(viewModel: MonitoringViewModel) {
    val pieChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice("Taken", 80f, Color(0xFF27AE60)),  // Green - Adhered
            PieChartData.Slice("Missed", 20f, Color(0xFFF53844))  // Red - Missed Dose
        ),
        plotType = PlotType.Pie
    )


    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = true,
        animationDuration = 1500,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFC8DFDC), shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PieChart(
            modifier = Modifier
                .width(180.dp)
                .height(180.dp).background(color = Color.Transparent),
            pieChartData = pieChartData,
            pieChartConfig = pieChartConfig
        )

        // Statistics below the chart
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Doses Taken", fontSize = 14.sp, color = Color.Black)
                Text(text = "80%", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF27AE60))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Doses Missed", fontSize = 14.sp, color = Color.Black)
                Text(text = "20%", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF53844))
            }
        }
    }
}

@Composable
fun MoodTab(viewModel: MonitoringViewModel) {
    val bubblePositions = remember { mutableStateListOf<Pair<Dp, Dp>>() }
    val maxWidth = 250.dp  // Max width of the scatter area
    val maxHeight = 200.dp // Max height of the scatter area
    val padding = 16.dp    // Extra padding to ensure bubbles stay inside

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(maxHeight + padding)
            .background(Color(0xFFEFE1DA), shape = RoundedCornerShape(16.dp))
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            viewModel.moodStats.forEach { (mood, count) ->
                val newPosition = generateNonCollidingPosition(bubblePositions, count, maxWidth, maxHeight)
                bubblePositions.add(newPosition)

                MoodBubble(
                    text = mood,
                    count = count,
                    position = newPosition,
                    maxWidth = maxWidth,
                    maxHeight = maxHeight
                )
            }
        }
    }
}

@Composable
fun MoodBubble(text: String, count: Int, position: Pair<Dp, Dp>, maxWidth: Dp, maxHeight: Dp) {
    val size = (sqrt(count.toFloat()) * 20).coerceIn(40f, 100f) // Limit size range
    val color = when (text) {
        "Happy" -> Color(0xFF99CC99)
        "Bad" -> Color(0xFFFF6666)
        "Okay" -> Color(0xFFC8C8C8)
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .offset(
                x = position.first.coerceIn(0.dp, maxWidth - size.dp), // Ensure within bounds
                y = position.second.coerceIn(0.dp, maxHeight - size.dp)
            )
            .size(size.dp)
            .background(color, shape = RoundedCornerShape(size / 2))
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = (size / 5).coerceIn(12f, 22f).sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

/**
 * Generates a non-colliding position for the bubble.
 */
fun generateNonCollidingPosition(
    existingPositions: List<Pair<Dp, Dp>>,
    count: Int,
    maxWidth: Dp,
    maxHeight: Dp
): Pair<Dp, Dp> {
    val size = (sqrt(count.toFloat()) * 20).coerceIn(40f, 100f)
    val margin = size.dp * 1.3f // Increase margin to prevent collisions
    val random = Random

    repeat(15) {  // Try multiple times to find a valid position
        val x = random.nextInt(0, (maxWidth.value - size).toInt()).dp
        val y = random.nextInt(0, (maxHeight.value - size).toInt()).dp

        val newPosition = x to y

        // Ensure no overlap
        if (existingPositions.none { (existingX, existingY) ->
                abs(existingX.value - x.value) < margin.value &&
                        abs(existingY.value - y.value) < margin.value
            }) {
            return newPosition
        }
    }

    // Fallback: Place in the center if no valid position is found
    return (maxWidth / 2 - size.dp / 2) to (maxHeight / 2 - size.dp / 2)
}
