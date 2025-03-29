package com.example.caresync.ui.screens.mood

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.caresync.ui.screens.mood.MoodEntryItem
import com.example.caresync.ui.screens.mood.MoodRatingPopup

@Composable
fun MoodScreen() {
    // Create view model
    val viewModel: MoodViewModel = viewModel()

    // Collect states
    val uiState by viewModel.uiState.collectAsState()
    val moodEntries by viewModel.moodEntries.collectAsState()

    // Screen content
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "My Mood Tracker",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (moodEntries.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No mood entries yet.\nTap the + button to add your first mood.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(moodEntries.sortedByDescending { it.date }) { entry ->
                        MoodEntryItem(entry)
                        HorizontalDivider()
                    }
                }
            }

            if (moodEntries.isNotEmpty()) {
                Button(
                    onClick = { viewModel.onEvent(MoodEvent.ClearHistory) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Clear All Entries")
                }
            }
        }

        // FAB to add new mood
        FloatingActionButton(
            onClick = { viewModel.onEvent(MoodEvent.ShowDialog) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Mood"
            )
        }

        // Show mood dialog if needed
        if (uiState.showDialog) {
            MoodRatingPopup(
                onDismissRequest = { viewModel.onEvent(MoodEvent.DismissDialog) },
                onMoodSelected = { mood ->
                    viewModel.onEvent(MoodEvent.SelectMood(mood))
                }
            )
        }
    }
}