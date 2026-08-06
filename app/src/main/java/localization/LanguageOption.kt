package com.example.reapercompanion.localization

data class LanguageOption(
    val code: String,
    val displayName: String,
    val nativeName: String
)

object SupportedLanguages {

    val all: List<LanguageOption> = listOf(
        LanguageOption(
            code = "en",
            displayName = "English",
            nativeName = "English"
        ),
        LanguageOption(
            code = "es",
            displayName = "Spanish",
            nativeName = "Español"
        ),
        LanguageOption(
            code = "fr",
            displayName = "French",
            nativeName = "Français"
        ),
        LanguageOption(
            code = "de",
            displayName = "German",
            nativeName = "Deutsch"
        ),
        LanguageOption(
            code = "it",
            displayName = "Italian",
            nativeName = "Italiano"
        ),
        LanguageOption(
            code = "pt-BR",
            displayName = "Portuguese (Brazil)",
            nativeName = "Português (Brasil)"
        ),
        LanguageOption(
            code = "zh-CN",
            displayName = "Chinese (Simplified)",
            nativeName = "简体中文"
        ),
        LanguageOption(
            code = "ja",
            displayName = "Japanese",
            nativeName = "日本語"
        ),
        LanguageOption(
            code = "ko",
            displayName = "Korean",
            nativeName = "한국어"
        ),
        LanguageOption(
            code = "tr",
            displayName = "Turkish",
            nativeName = "Türkçe"
        )
    )

    fun findByCode(
        code: String
    ): LanguageOption {
        return all.firstOrNull { language ->
            language.code.equals(
                code,
                ignoreCase = true
            )
        } ?: all.first()
    }
}