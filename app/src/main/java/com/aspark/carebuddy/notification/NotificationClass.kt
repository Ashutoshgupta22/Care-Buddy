package com.aspark.carebuddy.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.aspark.carebuddy.R

open class NotificationClass(private val context: Context) {

    init {
        registerNotificationChannel()
    }

    private fun registerNotificationChannel() {

        Log.d("NotificationClass", "registerNotificationChannel:" +
                " registering notification channel")

        val name = "Patient Request"
        val description = "Receive a notification when patients request your services"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("100",name,importance)
        channel.description = description

        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE)
                as NotificationManager

        notificationManager.createNotificationChannel(channel)

        val notification = Notification.Builder(context,"100")
            .setContentTitle("This is a notification")
            .setContentText("This is content text")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)

        try {
            NotificationManagerCompat.from(context).notify(100,notification.build())

        }
        catch (e: SecurityException) {
            Log.e("NotificationClass", "registerNotificationChannel: " +
                        "Notification permission not granted",e
            )

        }

    }
}