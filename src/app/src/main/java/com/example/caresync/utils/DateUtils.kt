package com.example.caresync.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun areSameDate(date1: Date, date2: Date): Boolean {
    val cal1 = Calendar.getInstance().apply {
        time = date1
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val cal2 = Calendar.getInstance().apply {
        time = date2
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return cal1.time == cal2.time
}

fun getDayBounds(date: Date): Pair<Long, Long> {
    val calendar = Calendar.getInstance().apply { time = date }
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val startOfDay = calendar.timeInMillis

    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    val endOfDay = calendar.timeInMillis

    return startOfDay to endOfDay
}

fun getDateWithTime(today: Date, hour: Int, minute: Int): Date {
    return Calendar.getInstance().apply {
        time = today
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time
}

fun getTodayWithSpecifiedTime(hour: Int, minute: Int): Date {
    val today = Calendar.getInstance().time
    return getDateWithTime(today, hour, minute)
}

fun addOneDay(date: Date): Date {
    return java.util.Calendar.getInstance().apply {
        time = date
        add(Calendar.DAY_OF_YEAR, 1)
    }.time
}

fun minusOneDay(date: Date): Date {
    return java.util.Calendar.getInstance().apply {
        time = date
        add(Calendar.DAY_OF_YEAR, -1)
    }.time
}

fun formatDateWithDayOfWeek(date: Date): String {
    val formatter = SimpleDateFormat("(E) dd-MM-yyyy", Locale.getDefault())
    return formatter.format(date)
}

fun formatDateWithTime(date: Date): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy: HH:mm", Locale.getDefault())
    return formatter.format(date)
}

