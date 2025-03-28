package com.example.caresync.ui.screens.calendar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caresync.datasource.MedicationDataSource
import com.example.caresync.model.MedicationDosage
import com.example.caresync.utils.addOneDay
import com.example.caresync.utils.areSameDate
import com.example.caresync.utils.formatDateWithDayOfWeek
import com.example.caresync.utils.formatDateWithTime
import com.example.caresync.utils.minusOneDay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

// Function to get current hour and minute (API 24+ compatible)
fun getCurrentTime(): Pair<Int, Int> {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.HOUR_OF_DAY) to calendar.get(Calendar.MINUTE)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayCalendarView(
    dosagesForDay: List<MedicationDosage>,
    curDate: Date,
    minuteHeight: Float,
    startHour: Int,
    endHour: Int,
    getMedicationName: suspend (Long)->String,
    updateIsDosageTaken: suspend (Long, Boolean) -> Unit,
    navNextDay: () -> Unit,
    navPrevDay: () -> Unit,
    isSheetVisible: Boolean,
    onBtmSheetShow: () -> Unit,
    onBtmSheetDismiss: () -> Unit,
    setDosageToEdit: (MedicationDosage, String) -> Unit,
    dosageToEdit: MedicationDosage?,
    dosageToEditName: String?,
    dosageProposedPostponeDate: Date?,
    computeNextDosageDate: suspend (MedicationDosage) -> Date,
) {
    val coroutineScope = rememberCoroutineScope() // Creates a coroutine scope for this composable

    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    // Update time every minute
    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000L) // Wait 1 minute
            currentTime = getCurrentTime() // Update the state
            println("QQQ: " + currentTime)
        }
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    LaunchedEffect(isSheetVisible) {
        if (isSheetVisible) {
            coroutineScope.launch { sheetState.show() }.invokeOnCompletion {
                onBtmSheetShow()
            }
        } else {
            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                onBtmSheetDismiss()
            }
        }
    }

    val (currentHour, currentMinute) = currentTime

    if (isSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onBtmSheetDismiss,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 16.dp,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(50.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                if (dosageToEdit != null) {
                    Text("Editing: ${dosageToEditName}")
                    Text("Original Consumption Datetime: ${formatDateWithTime(dosageToEdit.scheduledDatetime)}")
                    Text("Postponement Datetime ${formatDateWithTime(dosageProposedPostponeDate ?: dosageToEdit.scheduledDatetime)}")
                    Button(onClick = {
                        // TODO: Postpone and set it. Create a new entry oso for it.
                    }) {
                        Text("Confirm Postponement")
                    }
                }

                Button(onClick = {
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        onBtmSheetDismiss()
                    }
                }) {
                    Text("Dismiss")
                }
            }
        }
    }

    key(curDate) { // Use key modifier to ensure full recomposition whenever `curDate` changes
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color = Color.Gray.copy(alpha = 0.3f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Button(onClick = navPrevDay) {
                    Text("<")
                }
                Text(
                    text = formatDateWithDayOfWeek(curDate),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Button(onClick = navNextDay) {
                    Text(">")
                }
            }

            HorizontalDivider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.5f))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .drawBehind {
                        if (areSameDate(curDate, Calendar.getInstance().time)) {
                            val hourHeight =
                                (60 * minuteHeight).dp.toPx() // Height of each hour row
                            val yOffset =
                                ((currentHour - startHour) * hourHeight) + ((currentMinute / 60f) * hourHeight)

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
                        }
                    },
                contentPadding = PaddingValues(16.dp)
            ) {
                items(24) { hour ->
                    if (hour in startHour..endHour) {
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

                                val dosagesForThisHour = dosagesForDay.filter { it.hour == hour }
                                if (dosagesForThisHour.isNotEmpty()) {
                                    dosagesForThisHour.forEach { dosage ->
                                        // Fetch the medication name asynchronously
                                        var medicationName by remember {
                                            mutableStateOf<String?>(
                                                null
                                            )
                                        }

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
                                                minuteHeight = minuteHeight,
                                                onCheckedChange = { isChecked ->
                                                    coroutineScope.launch {
                                                        updateIsDosageTaken(dosage.id, isChecked)
                                                    }
                                                },
                                                onLongPress = {
//                                                    Log.i(
//                                                        "DEBUG",
//                                                        "Checking if time in the past: ${dosage.scheduledDatetime.time < System.currentTimeMillis()}"
//                                                    )
//                                                    Log.i(
//                                                        "DEBUG",
//                                                        "schedDate: ${formatDateWithTime(dosage.scheduledDatetime)}, date: ${
//                                                            formatDateWithTime(Date())
//                                                        }, currentTime == ${dosage.scheduledDatetime.time}"
//                                                    )
                                                    val canPostpone = !dosage.isDosageTaken &&
                                                            !dosage.isRescheduled &&
                                                            dosage.scheduledDatetime.time < System.currentTimeMillis()
                                                    if (canPostpone) {
                                                        setDosageToEdit(dosage, name)
                                                        coroutineScope.launch {
                                                            computeNextDosageDate(dosage)
                                                        }.invokeOnCompletion {
                                                            onBtmSheetShow()
                                                        }
                                                    }
                                                },
                                                modifier = Modifier.weight(1f)
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarBlock(
    title: String,
    hour: Int,
    min: Int,
    isDone: Boolean,
    minuteHeight: Float,
    onCheckedChange: (Boolean) -> Unit,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .offset(y = (min * minuteHeight).dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .height((58*minuteHeight).dp)
            .background(Color.Blue.copy(alpha = 0.3f))
            .padding(8.dp)
    ) {
        Row {
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                Checkbox(
                    isDone,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
            Text(
                text = "${title} [${hour}:${min}]",
                color = Color.Black,
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                onLongPress()
                            }
                        )
                    }
            )
        }
    }
}


// TODO(RAYNER): Create fake view model and DAO since factory in preview doesn't work.
@Preview(showBackground = true)
@Composable
fun DayCalendarViewPreview() {
    // Use a SnapshotStateList so that updates to items trigger recomposition.
    val mockDosages = remember {
        mutableStateListOf<MedicationDosage>().apply {
            // Assuming sampleMedicationDosages is a List<MedicationDosage>
            addAll(MedicationDataSource.sampleMedicationDosages)
        }
    }

    var currentDate by remember { mutableStateOf(Date()) }
    var showSheet by remember { mutableStateOf(false) }
    var dosageToEdit by remember { mutableStateOf<MedicationDosage?>(null) }
    var dosageToEditName by remember { mutableStateOf<String?>(null) }

    DayCalendarView(
        dosagesForDay = mockDosages,
        curDate = currentDate,
        minuteHeight = 1f,
        startHour = 8,
        endHour = 22,
        getMedicationName = { id: Long ->
            MedicationDataSource.getMedicationNameById(id) ?: "UNKNOWN"
        },
        updateIsDosageTaken = { dosageId, isChecked ->
            // Find the index and update using copy()
            val index = mockDosages.indexOfFirst { it.id == dosageId }
            if (index != -1) {
                mockDosages[index] = mockDosages[index].copy(isDosageTaken = isChecked)
            }
        },
        navNextDay = { currentDate = addOneDay(currentDate) },
        navPrevDay = { currentDate = minusOneDay(currentDate) },
        isSheetVisible = showSheet,
        onBtmSheetShow = { showSheet = true },
        onBtmSheetDismiss = { showSheet = false },
        setDosageToEdit = { newDosageToEdit: MedicationDosage, name: String ->
            dosageToEdit = newDosageToEdit
            dosageToEditName = name
        },
        dosageToEdit = dosageToEdit,
        dosageToEditName = dosageToEditName,
        dosageProposedPostponeDate = dosageToEdit?.scheduledDatetime ?: Date(),
        computeNextDosageDate = { _ -> Date() } // Dummy function, can't implement in preview
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