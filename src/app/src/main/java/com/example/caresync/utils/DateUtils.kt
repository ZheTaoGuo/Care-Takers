package com.example.caresync.utils

import android.icu.util.Calendar
import java.util.Date

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