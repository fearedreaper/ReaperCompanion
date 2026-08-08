package com.example.reapercompanion.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.reapercompanion.MainActivity
import com.example.reapercompanion.R
import com.example.reapercompanion.localization.LanguagePreferences
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ReaperMessagingService : FirebaseMessagingService() {

    override fun onNewToken(
        token: String
    ) {
        super.onNewToken(token)

        ReaperNotificationManager.syncLanguageTopic(
            LanguagePreferences.getSelectedLanguageCode(
                this
            )
        )
    }

    override fun onMessageReceived(
        remoteMessage: RemoteMessage
    ) {
        super.onMessageReceived(remoteMessage)

        val title =
            remoteMessage.notification?.title
                ?: remoteMessage.data["title"]
                ?: "Reaper Live"

        val message =
            remoteMessage.notification?.body
                ?: remoteMessage.data["message"]
                ?: "New Reaper Live content is available."

        val destination =
            remoteMessage.data["destination"]
                ?: "reaperLive"

        showNotification(
            title = title,
            message = message,
            destination = destination
        )
    }

    private fun showNotification(
        title: String,
        message: String,
        destination: String
    ) {
        createNotificationChannel()

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val intent =
            Intent(
                this,
                MainActivity::class.java
            ).apply {
                putExtra(
                    "destination",
                    destination
                )

                flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            }

        val pendingIntent =
            PendingIntent.getActivity(
                this,
                1001,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val notification =
            NotificationCompat.Builder(
                this,
                CHANNEL_ID
            )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(message)
                )
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        NotificationManagerCompat
            .from(this)
            .notify(
                System.currentTimeMillis().toInt(),
                notification
            )
    }

    private fun createNotificationChannel() {
        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    "Reaper Live Updates",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description =
                        "New codes, events, and game updates from Reaper Live."
                }

            val notificationManager =
                getSystemService(
                    NotificationManager::class.java
                )

            notificationManager.createNotificationChannel(
                channel
            )
        }
    }

    companion object {
        private const val CHANNEL_ID =
            "reaper_live_updates"
    }
}