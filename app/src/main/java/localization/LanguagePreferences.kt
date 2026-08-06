package com.example.reapercompanion.localization

import android.content.Context

object LanguagePreferences {

    private const val PREFERENCES_NAME =
        "reaper_language_preferences"

    private const val KEY_SELECTED_LANGUAGE =
        "selected_language"

    fun hasSelectedLanguage(
        context: Context
    ): Boolean {
        return preferences(context)
            .contains(KEY_SELECTED_LANGUAGE)
    }

    fun getSelectedLanguageCode(
        context: Context
    ): String {
        return preferences(context)
            .getString(
                KEY_SELECTED_LANGUAGE,
                "en"
            )
            ?: "en"
    }

    fun saveSelectedLanguage(
        context: Context,
        languageCode: String
    ) {
        preferences(context)
            .edit()
            .putString(
                KEY_SELECTED_LANGUAGE,
                languageCode
            )
            .apply()
    }

    fun clearSelectedLanguage(
        context: Context
    ) {
        preferences(context)
            .edit()
            .remove(KEY_SELECTED_LANGUAGE)
            .apply()
    }

    private fun preferences(
        context: Context
    ) = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )
}