package com.example.reapercompanion.settings

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperBadge
import com.example.reapercompanion.design.ReaperCard
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperDivider
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperPrimaryButton
import com.example.reapercompanion.design.ReaperSecondaryButton
import com.example.reapercompanion.localization.LanguagePreferences
import com.example.reapercompanion.localization.LocaleManager
import com.example.reapercompanion.screens.AppBackground

private const val PRIVACY_POLICY_URL =
    "https://fearedreaper.github.io/ReaperCompanion/privacy_policy.html"

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    val currentLanguageCode =
        LanguagePreferences.getSelectedLanguageCode(context)

    val isSpanish =
        currentLanguageCode.equals(
            "es",
            ignoreCase = true
        )

    AppBackground {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                ReaperHeader(
                    title = if (isSpanish) {
                        "CONFIGURACIÓN"
                    } else {
                        "SETTINGS"
                    },
                    onBackClick = onBackClick
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "REAPER COMPANION",
                    title = if (isSpanish) {
                        "Configuración de la aplicación"
                    } else {
                        "App Settings"
                    },
                    body = if (isSpanish) {
                        "Administra el idioma, la información de la aplicación, los detalles de soporte y la información de lanzamiento."
                    } else {
                        "Manage language, app information, support details, and release information."
                    },
                    badge = "VERSION 1.0.0"
                )
            }

            item {
                SettingsSectionHeader(
                    title = "LANGUAGE / IDIOMA"
                )
            }

            item {
                ReaperCard(
                    accentColor = ReaperColors.CyanGlow
                ) {
                    Text(
                        text = if (isSpanish) {
                            "Idioma de la aplicación"
                        } else {
                            "App Language"
                        },
                        color = ReaperColors.PrimaryText,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (isSpanish) {
                            "Elige el idioma que quieres usar en Reaper Companion."
                        } else {
                            "Choose the language you want to use in Reaper Companion."
                        },
                        color = ReaperColors.SecondaryText,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    if (currentLanguageCode == "en") {
                        ReaperPrimaryButton(
                            text = "ENGLISH • SELECTED",
                            onClick = {}
                        )
                    } else {
                        ReaperSecondaryButton(
                            text = "ENGLISH",
                            onClick = {
                                changeAppLanguage(
                                    context = context,
                                    languageCode = "en"
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (currentLanguageCode == "es") {
                        ReaperPrimaryButton(
                            text = "ESPAÑOL • SELECCIONADO",
                            onClick = {}
                        )
                    } else {
                        ReaperSecondaryButton(
                            text = "ESPAÑOL",
                            onClick = {
                                changeAppLanguage(
                                    context = context,
                                    languageCode = "es"
                                )
                            }
                        )
                    }
                }
            }

            item {
                ReaperDivider()
            }

            item {
                SettingsSectionHeader(
                    title = if (isSpanish) {
                        "INFORMACIÓN DE LA APLICACIÓN"
                    } else {
                        "APP INFORMATION"
                    }
                )
            }

            item {
                SettingsInfoCard(
                    title = "Reaper Companion",
                    description = if (isSpanish) {
                        "Un compañero de Dead by Daylight para builds, estrategia de Match Coach, favoritos y contenido en vivo."
                    } else {
                        "A Dead by Daylight companion for builds, Match Coach strategy, favorites, and live content."
                    },
                    badge = "1.0.0"
                )
            }

            item {
                SettingsInfoCard(
                    title = "Reaper Live",
                    description = if (isSpanish) {
                        "Los códigos, eventos en vivo y actualizaciones importantes se actualizan de forma remota sin una nueva versión de Google Play."
                    } else {
                        "Codes, live events, and important updates refresh remotely without a Play Store release."
                    },
                    badge = if (isSpanish) {
                        "ACTIVO"
                    } else {
                        "ACTIVE"
                    }
                )
            }

            item {
                ReaperDivider()
            }

            item {
                SettingsSectionHeader(
                    title = if (isSpanish) {
                        "SOPORTE"
                    } else {
                        "SUPPORT"
                    }
                )
            }

            item {
                SettingsInfoCard(
                    title = if (isSpanish) {
                        "Contactar con soporte"
                    } else {
                        "Contact Support"
                    },
                    description = if (isSpanish) {
                        "Los detalles de contacto de soporte se añadirán antes del lanzamiento público en Play Store."
                    } else {
                        "Support contact details will be added before the public Play Store release."
                    },
                    badge = if (isSpanish) {
                        "PRÓXIMAMENTE"
                    } else {
                        "COMING SOON"
                    }
                )
            }

            item {
                SettingsInfoCard(
                    title = if (isSpanish) {
                        "Política de privacidad"
                    } else {
                        "Privacy Policy"
                    },
                    description = if (isSpanish) {
                        "Consulta cómo Reaper Companion gestiona el acceso a internet, los datos locales de la aplicación y la privacidad."
                    } else {
                        "Read how Reaper Companion handles internet access, local app data, and privacy."
                    },
                    badge = if (isSpanish) {
                        "DISPONIBLE"
                    } else {
                        "AVAILABLE"
                    }
                )
            }

            item {
                ReaperSecondaryButton(
                    text = if (isSpanish) {
                        "ABRIR POLÍTICA DE PRIVACIDAD"
                    } else {
                        "OPEN PRIVACY POLICY"
                    },
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(PRIVACY_POLICY_URL)
                        )

                        context.startActivity(intent)
                    }
                )
            }

            item {
                ReaperDivider()
            }

            item {
                SettingsSectionHeader(
                    title = if (isSpanish) {
                        "ACERCA DE"
                    } else {
                        "ABOUT"
                    }
                )
            }

            item {
                ReaperCard(
                    accentColor = Color(0xFFFFC857)
                ) {
                    Text(
                        text = if (isSpanish) {
                            "Creado por Feared Reaper"
                        } else {
                            "Built by Feared Reaper"
                        },
                        color = ReaperColors.PrimaryText,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isSpanish) {
                            "Reaper Companion fue creado para ayudar a los jugadores a crear mejores builds, prepararse para enfrentamientos y mantenerse conectados mediante Reaper Live."
                        } else {
                            "Reaper Companion was created to help players build smarter, prepare for matchups, and stay connected through Reaper Live."
                        },
                        color = ReaperColors.SecondaryText,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            item {
                ReaperSecondaryButton(
                    text = if (isSpanish) {
                        "ATRÁS"
                    } else {
                        "BACK"
                    },
                    onClick = onBackClick
                )
            }

            item {
                Text(
                    text = "REAPER COMPANION • VERSION 1.0.0",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 4.dp,
                            bottom = 24.dp
                        ),
                    color = Color(0xFF526268),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private fun changeAppLanguage(
    context: Context,
    languageCode: String
) {
    if (
        LanguagePreferences.getSelectedLanguageCode(context) ==
        languageCode
    ) {
        return
    }

    LanguagePreferences.saveSelectedLanguage(
        context = context,
        languageCode = languageCode
    )

    val activity = context.findActivity()

    LocaleManager.applyLanguage(
        context = activity ?: context,
        languageCode = languageCode
    )

    activity?.recreate()
}

private fun Context.findActivity(): Activity? {
    var currentContext = this

    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }

        currentContext = currentContext.baseContext
    }

    return currentContext as? Activity
}

@Composable
private fun SettingsSectionHeader(
    title: String
) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth(),
        color = ReaperColors.PrimaryText,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp
    )
}

@Composable
private fun SettingsInfoCard(
    title: String,
    description: String,
    badge: String
) {
    ReaperCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = ReaperColors.PrimaryText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = description,
                    color = ReaperColors.SecondaryText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 6.dp))

            ReaperBadge(
                text = badge,
                accentColor = ReaperColors.CyanGlow
            )
        }
    }
}