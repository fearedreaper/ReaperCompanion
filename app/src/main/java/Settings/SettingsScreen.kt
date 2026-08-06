package com.example.reapercompanion.settings

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
import com.example.reapercompanion.design.ReaperSecondaryButton
import com.example.reapercompanion.screens.AppBackground

private const val PRIVACY_POLICY_URL =
    "https://fearedreaper.github.io/ReaperCompanion/privacy_policy.html"

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

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
                    title = "SETTINGS",
                    onBackClick = onBackClick
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "REAPER COMPANION",
                    title = "App Settings",
                    body =
                        "Manage app information, support details, and release information.",
                    badge = "VERSION 1.0.0"
                )
            }

            item {
                SettingsSectionHeader(
                    title = "APP INFORMATION"
                )
            }

            item {
                SettingsInfoCard(
                    title = "Reaper Companion",
                    description =
                        "A Dead by Daylight companion for builds, Match Coach strategy, favorites, and live content.",
                    badge = "1.0.0"
                )
            }

            item {
                SettingsInfoCard(
                    title = "Reaper Live",
                    description =
                        "Announcements, live events, and featured builds update remotely without a Play Store release.",
                    badge = "ACTIVE"
                )
            }

            item {
                ReaperDivider()
            }

            item {
                SettingsSectionHeader(
                    title = "SUPPORT"
                )
            }

            item {
                SettingsInfoCard(
                    title = "Contact Support",
                    description =
                        "Support contact details will be added before the public Play Store release.",
                    badge = "COMING SOON"
                )
            }

            item {
                SettingsInfoCard(
                    title = "Privacy Policy",
                    description =
                        "Read how Reaper Companion handles internet access, local app data, and privacy.",
                    badge = "AVAILABLE"
                )
            }

            item {
                ReaperSecondaryButton(
                    text = "OPEN PRIVACY POLICY",
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
                    title = "ABOUT"
                )
            }

            item {
                ReaperCard(
                    accentColor = Color(0xFFFFC857)
                ) {
                    Text(
                        text = "Built by Feared Reaper",
                        color = ReaperColors.PrimaryText,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text =
                            "Reaper Companion was created to help players build smarter, prepare for matchups, and stay connected through Reaper Live.",
                        color = ReaperColors.SecondaryText,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            item {
                ReaperSecondaryButton(
                    text = "BACK",
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