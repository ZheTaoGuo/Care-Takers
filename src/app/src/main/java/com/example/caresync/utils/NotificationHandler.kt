package com.example.caresync.utils

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.caresync.MainActivity
import com.example.caresync.R
import java.util.Date
import kotlin.random.Random

class NotificationHandler(private val context: Context) {
    object Constants {
        const val dosageReminderChannelID = "dosage_reminder_id"
    }

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    init {
        // Create notification channel for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.dosageReminderChannelID,
                "Dosage Reminder Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "A notification channel for dosage taking reminders"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleNotification(scheduledDate: Date, title: String, message: String) {
        val currentTime = System.currentTimeMillis()
        val scheduledTime = scheduledDate.time

        // Check if the scheduled time is in the past
        if (scheduledTime <= currentTime) {
            Log.w("Alarm", "Cannot schedule notifications in the past!")
            return // Exit early
        }

        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("NOTIFICATION_TITLE", title)
            putExtra("NOTIFICATION_MESSAGE", message)
        }

        val requestCode = Random.nextInt() // TODO(RAYNER): Replace with passed in medicationDosageID?
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Check if we have permission to schedule an exact alarm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(AlarmManager::class.java)
            if (!alarmManager.canScheduleExactAlarms()) {
                // Handle this by requesting permission (step 3)
                return
            }
        }

        val timeInMillis = scheduledDate.time
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }

    fun showSimpleNotification(title: String, message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted, do not send notification
            return
        }

        val notificationId = Random.nextInt()

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(context, Constants.dosageReminderChannelID)
        } else {
            NotificationCompat.Builder(context)
        }
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.default_medication)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(notificationId, builder.build())
    }
}