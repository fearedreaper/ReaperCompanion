package com.example.reapercompanion.notifications

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging

object ReaperNotificationManager {

    fun syncLanguageTopic(
        languageCode: String
    ) {
        val firebaseMessaging =
            FirebaseMessaging.getInstance()

        firebaseMessaging.unsubscribeFromTopic(
            GENERAL_TOPIC
        )

        if (
            languageCode.equals(
                "es",
                ignoreCase = true
            )
        ) {
            firebaseMessaging.unsubscribeFromTopic(
                ENGLISH_TOPIC
            )

            firebaseMessaging.subscribeToTopic(
                SPANISH_TOPIC
            )
        } else {
            firebaseMessaging.unsubscribeFromTopic(
                SPANISH_TOPIC
            )

            firebaseMessaging.subscribeToTopic(
                ENGLISH_TOPIC
            )
        }
    }

    fun notificationsAreEnabled(
        context: Context
    ): Boolean {
        return NotificationManagerCompat
            .from(context)
            .areNotificationsEnabled()
    }

    fun openSystemNotificationSettings(
        context: Context
    ) {
        val intent =
            if (
                Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.O
            ) {
                Intent(
                    Settings.ACTION_APP_NOTIFICATION_SETTINGS
                ).apply {
                    putExtra(
                        Settings.EXTRA_APP_PACKAGE,
                        context.packageName
                    )
                }
            } else {
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse(
                        "package:${context.packageName}"
                    )
                )
            }

        context.startActivity(intent)
    }

    private const val GENERAL_TOPIC =
        "reaper_live"

    private const val ENGLISH_TOPIC =
        "reaper_live_en"

    private const val SPANISH_TOPIC =
        "reaper_live_es"
}