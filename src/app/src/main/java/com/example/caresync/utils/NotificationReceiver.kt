package com.example.caresync.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("NOTIFICATION_TITLE") ?: "Default Title"
        val message = intent.getStringExtra("NOTIFICATION_MESSAGE") ?: "Default Message"

        val notificationHandler = NotificationHandler(context)
        notificationHandler.showSimpleNotification(title, message)
    }
}