package com.example.reapercompanion.screens

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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    var animationsEnabled by remember {
        mutableStateOf(true)
    }

    var hapticsEnabled by remember {
        mutableStateOf(true)
    }

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
                    title = "Dead by Daylight Strategy Suite",
                    body =
                        "Build smarter, prepare for the Fog, and keep your favorite loadouts close.",
                    badge = "ALPHA"
                )
            }

            item {
                SettingsSectionLabel(
                    title = "EXPERIENCE"
                )
            }

            item {
                ReaperCard {
                    SettingsToggleRow(
                        title = "Animations",
                        description =
                            "Use Reaper transitions and subtle card motion.",
                        checked = animationsEnabled,
                        onCheckedChange = {
                            animationsEnabled = it
                        }
                    )

                    ReaperDivider(
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    SettingsToggleRow(
                        title = "Haptics",
                        description =
                            "Use light vibration feedback for important taps.",
                        checked = hapticsEnabled,
                        onCheckedChange = {
                            hapticsEnabled = it
                        }
                    )
                }
            }

            item {
                SettingsSectionLabel(
                    title = "ABOUT"
                )
            }

            item {
                ReaperCard {
                    SettingsInfoRow(
                        label = "VERSION",
                        value = "0.9 Alpha"
                    )

                    ReaperDivider(
                        modifier = Modifier.padding(vertical = 14.dp)
                    )

                    SettingsInfoRow(
                        label = "CREATED BY",
                        value = "Kevin"
                    )

                    ReaperDivider(
                        modifier = Modifier.padding(vertical = 14.dp)
                    )

                    SettingsInfoRow(
                        label = "FOCUS",
                        value = "Dead by Daylight"
                    )
                }
            }

            item {
                ReaperCard {
                    Text(
                        text = "ABOUT REAPER",
                        color = ReaperColors.CyanGlow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.7.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text =
                            "Reaper Companion is a player-focused strategy app for creating builds, comparing loadouts, exploring curated meta options, and preparing for specific Killer and map matchups.",
                        color = ReaperColors.PrimaryText,
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                }
            }

            item {
                SettingsSectionLabel(
                    title = "COMING LATER"
                )
            }

            item {
                ReaperCard {
                    FutureFeatureRow(
                        title = "Patch Notes",
                        description =
                            "Track balance updates and Reaper Companion changes."
                    )

                    ReaperDivider(
                        modifier = Modifier.padding(vertical = 14.dp)
                    )

                    FutureFeatureRow(
                        title = "Privacy",
                        description =
                            "Review future data and account settings."
                    )

                    ReaperDivider(
                        modifier = Modifier.padding(vertical = 14.dp)
                    )

                    FutureFeatureRow(
                        title = "Theme Controls",
                        description =
                            "Adjust glow intensity and visual preferences."
                    )
                }
            }

            item {
                ReaperSecondaryButton(
                    text = "BACK TO REAPER COMPANION",
                    onClick = onBackClick
                )
            }

            item {
                Text(
                    text = "THE ENTITY WATCHES. WE PREPARE.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 4.dp,
                            bottom = 24.dp
                        ),
                    color = Color(0xFF526268),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun SettingsSectionLabel(
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
private fun SettingsToggleRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
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

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                color = ReaperColors.SecondaryText,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF001014),
                checkedTrackColor = ReaperColors.CyanGlow,
                uncheckedThumbColor = ReaperColors.SecondaryText,
                uncheckedTrackColor = ReaperColors.CardAvailable
            )
        )
    }
}

@Composable
private fun SettingsInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = ReaperColors.SecondaryText,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp
        )

        ReaperBadge(
            text = value
        )
    }
}

@Composable
private fun FutureFeatureRow(
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = ReaperColors.PrimaryText,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                color = ReaperColors.SecondaryText,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

        ReaperBadge(
            text = "SOON",
            accentColor = Color(0xFFFFC857)
        )
    }
}