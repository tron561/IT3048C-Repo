package com.newsnow

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("TAG", "The token refreshed: $token")
    }
}