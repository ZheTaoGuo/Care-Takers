package com.example.caresync.ui.screens.calendar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.caresync.datasource.MedicationDataSource
import com.example.caresync.model.MedicationDosage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Date

// Function to get current hour and minute (API 24+ compatible)
fun getCurrentTime(): Pair<Int, Int> {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.HOUR_OF_DAY) to calendar.get(Calendar.MINUTE)
}

// NOTE(RAYNER): Need to assume that this calendar doesn't get anything beyond the date it has.
@Composable
fun DayCalendarView(
    dosagesForDay: List<MedicationDosage>,
    minuteHeight: Float,
    startHour: Int,
    endHour: Int,
    getMedicationName: suspend (Long)->String
) {
    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    // Update time every minute
    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000L) // Wait 1 minute
            currentTime = getCurrentTime() // Update the state
            println("QQQ: " + currentTime)
        }
    }

    val (currentHour, currentMinute) = currentTime

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .drawBehind {
                val hourHeight = (60 * minuteHeight).dp.toPx() // Height of each hour row
                val yOffset = ((currentHour - startHour) * hourHeight) + ((currentMinute / 60f) * hourHeight)

                drawCircle(
                    color = Color.Red,
                    radius = 16f,
                    center = Offset(0f, yOffset)
                )
                drawLine(
                    color = Color.Red,
                    start = Offset(0f, yOffset),
                    end = Offset(size.width, yOffset),
                    strokeWidth = 3.dp.toPx()
                )
            },
        contentPadding = PaddingValues(16.dp)
    ) {
        items(24) { hour ->
            if (hour in startHour .. endHour) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((60 * minuteHeight).dp) // Adjust height for different time resolutions
                        .drawBehind {
                            drawLine(
                                color = Color.Gray.copy(alpha = 0.5f),
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                ) {
                    Row {
                        Text(
                            text = String.format("%02d:00", hour),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                        )

                        dosagesForDay.find { it.hour == hour }?.let { dosage ->
                            // Fetch the medication name asynchronously
                            var medicationName by remember { mutableStateOf<String?>(null) }

                            // Call getMedicationName only when dosage changes
                            LaunchedEffect(dosage.medicationId) {
                                medicationName = getMedicationName(dosage.medicationId)
                            }

                            // Show the dosage block only when medication name is available
                            medicationName?.let { name ->
                                CalendarBlock(
                                    title = name,
                                    hour = dosage.hour,
                                    min = dosage.minute,
                                    isDone = dosage.isDosageTaken,
                                    minuteHeight = minuteHeight
                                )
                            } ?: run {
                                // You can show a loading spinner or something until the medication name is loaded
                                Text("Loading...")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarBlock(title: String, hour: Int, min: Int, isDone: Boolean, minuteHeight: Float) {
    Box(
        modifier = Modifier
            .offset(y = (min * minuteHeight).dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height((58*minuteHeight).dp)
            .background(Color.Blue.copy(alpha = 0.3f))
            .padding(8.dp)
    ) {
        Text(text = "${title} [${hour}:${min}]", color = Color.Black)

        Checkbox(
            isDone,
            onCheckedChange = {},
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}


// TODO(RAYNER): Create fake view model and DAO since factory in preview doesn't work.
@Preview(showBackground = true)
@Composable
fun DayCalendarViewPreview() {
    DayCalendarView(
        dosagesForDay = MedicationDataSource.sampleMedicationDosages,
        minuteHeight = 1f,
        startHour = 8,
        endHour = 22,
        getMedicationName = { id: Long -> "TEST_MED_NAME_${id}" }
    )

// FOR DEBUGGING
//    Box { // FOR DEBUGGING
//        // Put the DayCalendarView here for debug.
//        Column {
//            Text(text = "CurDate: " + currentDate)
//            Text(text = "Dosages: " + dosagesByDate)
//        }
//    }
}