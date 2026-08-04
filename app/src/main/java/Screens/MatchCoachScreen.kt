package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.database.MatchCoachEngine
import com.example.reapercompanion.design.ReaperColors

@Composable
fun MatchCoachScreen(
    onBackClick: () -> Unit,
    onAnalyzeClick: (
        opponentName: String,
        mapName: String
    ) -> Unit
) {
    val killers = remember {
        MatchCoachEngine.getSupportedKillers()
    }

    val maps = remember {
        MatchCoachEngine.getSupportedMaps()
    }

    var selectedKiller by remember {
        mutableStateOf("")
    }

    var selectedMap by remember {
        mutableStateOf("")
    }

    val canAnalyze =
        selectedKiller.isNotBlank() &&
                selectedMap.isNotBlank()

    AppBackground {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "REAPER MATCH COACH",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.PrimaryText,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        "Choose the Killer and map. Reaper will build a complete Survivor match plan.",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pressScale()
                        .clickable(onClick = onBackClick),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = ReaperColors.BorderActive
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = ReaperColors.CardBackground
                    )
                ) {
                    Text(
                        text = "‹  BACK TO DEAD BY DAYLIGHT",
                        modifier = Modifier.padding(16.dp),
                        color = ReaperColors.CyanGlow,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                SectionLabel(
                    text = "CHOOSE THE KILLER"
                )
            }

            killers.forEach { killer ->
                item {
                    MatchCoachChoiceCard(
                        title = killer,
                        subtitle = killerSubtitle(killer),
                        selected = selectedKiller == killer,
                        accentColor = Color(0xFFFF6B6B),
                        onClick = {
                            selectedKiller = killer
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))

                SectionLabel(
                    text = "CHOOSE THE MAP"
                )
            }

            maps.forEach { map ->
                item {
                    MatchCoachChoiceCard(
                        title = map,
                        subtitle = mapSubtitle(map),
                        selected = selectedMap == map,
                        accentColor = ReaperColors.CyanGlow,
                        onClick = {
                            selectedMap = map
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))

                MatchCoachSelectionSummary(
                    selectedKiller = selectedKiller,
                    selectedMap = selectedMap
                )
            }

            item {
                Button(
                    onClick = {
                        onAnalyzeClick(
                            selectedKiller,
                            selectedMap
                        )
                    },
                    enabled = canAnalyze,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ReaperColors.CyanGlow,
                        contentColor = Color(0xFF001014),
                        disabledContainerColor =
                            Color(0xFF202A2E),
                        disabledContentColor =
                            ReaperColors.DisabledText
                    )
                ) {
                    Text(
                        text = if (canAnalyze) {
                            "ANALYZE MATCH"
                        } else {
                            "CHOOSE KILLER AND MAP"
                        },
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun MatchCoachChoiceCard(
    title: String,
    subtitle: String,
    selected: Boolean,
    accentColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pressScale()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = if (selected) 1.7.dp else 1.dp,
            color = if (selected) {
                accentColor
            } else {
                ReaperColors.BorderInactive
            }
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                accentColor.copy(alpha = 0.14f)
            } else {
                ReaperColors.CardBackground
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = if (selected) {
                        accentColor
                    } else {
                        ReaperColors.PrimaryText
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = subtitle,
                    color = ReaperColors.SecondaryText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Text(
                text = if (selected) {
                    "SELECTED"
                } else {
                    "CHOOSE"
                },
                color = if (selected) {
                    accentColor
                } else {
                    ReaperColors.DisabledText
                },
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

@Composable
private fun MatchCoachSelectionSummary(
    selectedKiller: String,
    selectedMap: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.2.dp,
            color = ReaperColors.CyanGlow.copy(alpha = 0.55f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "MATCH SETUP",
                color = ReaperColors.CyanGlow,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.7.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            SummaryLine(
                label = "KILLER",
                value = selectedKiller.ifBlank {
                    "Not selected"
                },
                valueColor = Color(0xFFFF6B6B)
            )

            Spacer(modifier = Modifier.height(10.dp))

            SummaryLine(
                label = "MAP",
                value = selectedMap.ifBlank {
                    "Not selected"
                },
                valueColor = ReaperColors.CyanGlow
            )
        }
    }
}

@Composable
private fun SummaryLine(
    label: String,
    value: String,
    valueColor: Color
) {
    Text(
        text = label,
        color = ReaperColors.SecondaryText,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.2.sp
    )

    Spacer(modifier = Modifier.height(3.dp))

    Text(
        text = value,
        color = valueColor,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 21.sp
    )
}

@Composable
private fun SectionLabel(
    text: String
) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        color = ReaperColors.PrimaryText,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp
    )
}

private fun killerSubtitle(
    killer: String
): String {
    val normalized = killer.lowercase()

    return when {
        normalized.contains("nurse") ->
            "Blink mobility, prediction, and line-of-sight pressure"

        normalized.contains("blight") ||
                normalized.contains("hillbilly") ||
                normalized.contains("oni") ||
                normalized.contains("mastermind") ||
                normalized.contains("houndmaster") ||
                normalized.contains("slasher") ->
            "High-speed pressure, rapid rotations, and aggressive chase power"

        normalized.contains("huntress") ||
                normalized.contains("deathslinger") ||
                normalized.contains("trickster") ||
                normalized.contains("artist") ||
                normalized.contains("executioner") ||
                normalized.contains("nemesis") ||
                normalized.contains("unknown") ||
                normalized.contains("animatronic") ||
                normalized.contains("first") ->
            "Ranged threat, dangerous sightlines, and prediction pressure"

        normalized.contains("spirit") ||
                normalized.contains("wraith") ||
                normalized.contains("ghost face") ||
                normalized.contains("shape") ||
                normalized.contains("pig") ||
                normalized.contains("onryo") ||
                normalized.contains("good guy") ||
                normalized.contains("dark lord") ||
                normalized.contains("ghoul") ->
            "Stealth, surprise attacks, and difficult tracking"

        normalized.contains("trapper") ||
                normalized.contains("hag") ||
                normalized.contains("knight") ||
                normalized.contains("skull merchant") ||
                normalized.contains("singularity") ||
                normalized.contains("xenomorph") ->
            "Area control, route denial, and map-object pressure"

        else ->
            "Matchup-specific strategy, counters, and recommended perks"
    }
}

private fun mapSubtitle(
    map: String
): String {
    return when (map) {
        "Midwich Elementary School" ->
            "Indoor hallways, floor transitions, and limited sightlines"

        "Gideon Meat Plant" ->
            "Heavy pallet density and constant floor changes"

        "Raccoon City Police Department" ->
            "Complex navigation, long halls, and connected rooms"

        "Mount Ormond Resort" ->
            "Large open areas and a powerful central building"

        "Badham Preschool" ->
            "Multiple structures, strong windows, and basement risk"

        else ->
            "Map-specific strategy"
    }
}