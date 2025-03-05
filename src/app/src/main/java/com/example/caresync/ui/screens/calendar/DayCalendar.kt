package com.example.caresync.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.sp
import com.example.caresync.datasource.CalendarDataSource
import com.example.caresync.model.CalendarEntry
import kotlinx.coroutines.delay
import java.util.Calendar

// Function to get current hour and minute (API 24+ compatible)
fun getCurrentTime(): Pair<Int, Int> {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.HOUR_OF_DAY) to calendar.get(Calendar.MINUTE)
}

@Composable
fun DayCalendarView(events: List<CalendarEntry>) {
    val start: Int = 8
    val end: Int = 22
    val minuteHeight: Float = 1.0f

    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    // Update time every minute
    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000L) // Wait 1 minute
            currentTime = getCurrentTime() // Update the state
        }
    }

    val (currentHour, currentMinute) = currentTime

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .drawBehind {
                val hourHeight = (60 * minuteHeight).dp.toPx() // Height of each hour row
                val yOffset = ((currentHour - start) * hourHeight) + ((currentMinute / 60f) * hourHeight)

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
            if (hour in start..end) {
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
                    Row(

                    ) {
                        Text(
                            text = String.format("%02d:00", hour),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                        )

                        events.find { it.hour == hour }?.let { event ->
                            Box(
                                modifier = Modifier
                                    .offset(y = (event.min * minuteHeight).dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                    .fillMaxWidth()
                                    .height((58*minuteHeight).dp)
                                    .background(Color.Blue.copy(alpha = 0.3f))
                                    .padding(8.dp)
                            ) {
                                Text(text = "${event.title} [${event.hour}:${event.min}]", color = Color.Black)

                                Checkbox(
                                    event.completed,
                                    onCheckedChange = {},
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DayCalendarViewPreview() {
    DayCalendarView(CalendarDataSource.sampleEvents)
}