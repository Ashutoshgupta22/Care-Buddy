package com.aspark.carebuddy.notification

import android.app.Notification
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.aspark.carebuddy.R
import com.aspark.carebuddy.model.User.Companion.currentUser
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.qualifiers.ApplicationContext

class FirebaseNotificationService: FirebaseMessagingService(){

    override fun onNewToken(token: String) {

        Log.i("FirebaseNotificationService", "onNewToken: RefreshedToken: $token")

        val preferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("firebase_token", token)
        editor.apply()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.i("FirebaseNotificationService", "onMessageReceived: " +
                "notification received from firebase")

        val title = message.notification?.title
        val description = message.notification?.body
    }
}