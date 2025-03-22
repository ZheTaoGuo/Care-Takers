package com.example.caresync.ui.screens.mood
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.caresync.ui.screens.mood.MoodEntry
import com.example.caresync.ui.screens.mood.MoodState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun MoodRatingPopup(
    onDismissRequest: () -> Unit,
    onMoodSelected: (MoodState) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFFEEEEEE),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "How are you feeling today?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MoodButton(
                        mood = MoodState.GOOD,
                        backgroundColor = Color(0xFF9ED5AC),
                        onClick = { onMoodSelected(MoodState.GOOD) }
                    )

                    MoodButton(
                        mood = MoodState.OKAY,
                        backgroundColor = Color(0xFFD9D9D9),
                        onClick = { onMoodSelected(MoodState.OKAY) }
                    )

                    MoodButton(
                        mood = MoodState.BAD,
                        backgroundColor = Color(0xFFF99CA0),
                        onClick = { onMoodSelected(MoodState.BAD) }
                    )
                }
            }
        }
    }
}

@Composable
fun MoodButton(
    mood: MoodState,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(60.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = when (mood) {
                    MoodState.GOOD -> "🙂"
                    MoodState.OKAY -> "😐"
                    MoodState.BAD -> "🙁"
                },
                fontSize = 20.sp
            )

            Text(
                text = when (mood) {
                    MoodState.GOOD -> "Good"
                    MoodState.OKAY -> "Okay"
                    MoodState.BAD -> "Bad"
                },
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun MoodEntryItem(entry: MoodEntry) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Mood emoji
        Text(
            text = when (entry.mood) {
                MoodState.GOOD -> "🙂"
                MoodState.OKAY -> "😐"
                MoodState.BAD -> "🙁"
            },
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Mood details
        Column {
            Text(
                text = when (entry.mood) {
                    MoodState.GOOD -> "Good"
                    MoodState.OKAY -> "Okay"
                    MoodState.BAD -> "Bad"
                },
                fontWeight = FontWeight.Medium
            )

            val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
            Text(
                text = dateFormatter.format(Date(entry.date)),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
