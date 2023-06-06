package com.aspark.carebuddy.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService: FirebaseMessagingService(){

    override fun onNewToken(token: String) {

        Log.i("FirebaseNotificationService", "onNewToken: RefreshedToken: $token")

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.i("FirebaseNotificationService", "onMessageReceived: " +
                "notification received from firebase")
    }



}