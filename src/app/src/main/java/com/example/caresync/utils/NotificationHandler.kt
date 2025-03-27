package com.example.caresync.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.caresync.MainActivity
import com.example.caresync.R
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

    fun showSimpleNotification() {
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
            .setContentTitle("Simple Notification")
            .setContentText("Message or text with notification")
            .setSmallIcon(R.drawable.default_medication)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(notificationId, builder.build())
    }
}