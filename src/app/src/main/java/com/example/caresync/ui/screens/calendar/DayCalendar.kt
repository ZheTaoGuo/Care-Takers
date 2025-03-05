package com.example.caresync.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DayCalendarView(events: List<Event>) {
    val start: Int = 8
    val end: Int = 22
    val minSize: Float = 1.0f

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(24) { hour ->
            if (hour >= start && hour <= end) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((60 * minSize).dp) // Adjust height for different time resolutions
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
                                    .offset(y = (event.min * minSize).dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                    .fillMaxWidth()
                                    .height((58*minSize).dp)
                                    .background(Color.Blue.copy(alpha = 0.3f))
                                    .padding(8.dp)
                            ) {
                                Text(text = "${event.title} [${event.hour}:${event.min}]", color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}

data class Event(val title: String, val hour: Int, val min: Int, val details: String)


@Preview(showBackground = true)
@Composable
fun DayCalendarViewPreview() {
    val sampleEvents = listOf(
        Event("Meeting", 9, 30, "Once a day"),
        Event("Workout", 12, 0, "Blah blah blah"),
        Event("Lunch", 13, 45, "Blah blah blah"),
        Event("Project Work", 15, 15, "Blah blah blah")
    )
    DayCalendarView(sampleEvents)
}