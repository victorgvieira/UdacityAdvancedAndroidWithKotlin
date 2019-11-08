package com.example.android.eggtimernotifications

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.android.eggtimernotifications.util.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = MyFirebaseMessagingService::class.java.simpleName
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

        // DONE step 3.5 check messages for data. NOTE: it's not listed in the checkout repository
        // Check is message contains a data payload
        // NOTE: Using firebase-messaging version 20.0.0 the remote message and data is not null
        remoteMessage.data.let {
            Log.d(TAG, "Message data payload: $it")
        }

        // DONE step 3.6 check messages for notifications and call sendNotifications. NOTE: it's not listed in the checkout repository
        // Check is message contains a notification payload
        remoteMessage.notification?.let {
            Log.d(TAG, "Message notification body: ${it.body}")
            // Do this to avoid null pointer
            it.body?.let { body ->
                sendNotification(body)
            }
        }

    }
    // [END receive_message]

    // DONE Step 3.2 log registration token
    // [START on_new_token]
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }

    // [END on_new_token]

    /**
     * Persist token to third-party (your app) servers.
     *
     * @param token The new token.
     * */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: implement this method to send token to your app server.
    }

    /**
     * Create and show simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     * */
    private fun sendNotification(messageBody: String) {
        val notificationManager =
            ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            ) as NotificationManager

        notificationManager.sendNotification(messageBody, applicationContext)

    }
}