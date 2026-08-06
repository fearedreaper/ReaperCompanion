package com.example.reapercompanion.localization

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

object LocaleManager {

    fun applySavedLanguage(
        context: Context
    ) {
        val languageCode =
            LanguagePreferences.getSelectedLanguageCode(context)

        applyLanguage(
            context = context,
            languageCode = languageCode
        )
    }

    fun applyLanguage(
        context: Context,
        languageCode: String
    ) {
        val locale =
            Locale.forLanguageTag(languageCode)

        Locale.setDefault(locale)

        updateResources(
            context = context,
            locale = locale
        )

        updateResources(
            context = context.applicationContext,
            locale = locale
        )
    }

    private fun updateResources(
        context: Context,
        locale: Locale
    ) {
        val resources = context.resources
        val configuration =
            Configuration(resources.configuration)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            configuration.setLocales(
                android.os.LocaleList(locale)
            )
        } else {
            @Suppress("DEPRECATION")
            configuration.locale = locale
        }

        @Suppress("DEPRECATION")
        resources.updateConfiguration(
            configuration,
            resources.displayMetrics
        )
    }
}