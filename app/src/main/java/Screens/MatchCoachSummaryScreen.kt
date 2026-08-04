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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.database.MapDatabase
import com.example.reapercompanion.design.ReaperBadge
import com.example.reapercompanion.design.ReaperCard
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperDivider
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperPrimaryButton
import com.example.reapercompanion.design.ReaperSecondaryButton

@Composable
fun MatchCoachSummaryScreen(
    selectedKiller: String,
    selectedMap: String,
    onBackClick: () -> Unit,
    onAnalyzeClick: (
        opponentName: String,
        mapName: String
    ) -> Unit
) {
    val mapData = MapDatabase.get(selectedMap)

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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                ReaperHeader(
                    title = "MATCH COACH",
                    onBackClick = onBackClick
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Step 3 of 3 • Ready",
                    color = ReaperColors.CyanGlow,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "REAPER ANALYSIS",
                    color = ReaperColors.PrimaryText,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        "Your Killer and map are locked in. Reaper is ready to build the complete Survivor match plan.",
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "REAPER ANALYSIS READY",
                    title = "The Fog Is Set",
                    body =
                        "Your matchup combines Killer pressure, map geometry, perk recommendations, chase planning, objectives, and endgame advice.",
                    badge = "READY"
                )
            }

            item {
                ReaperCard(
                    accentColor = Color(0xFFFF6B6B)
                ) {
                    MatchupSummaryRow(
                        label = "KILLER",
                        value = selectedKiller.ifBlank {
                            "Not selected"
                        },
                        accentColor = Color(0xFFFF6B6B)
                    )
                }
            }

            item {
                ReaperCard(
                    accentColor = ReaperColors.CyanGlow
                ) {
                    MatchupSummaryRow(
                        label = "MAP",
                        value = selectedMap.ifBlank {
                            "Not selected"
                        },
                        accentColor = ReaperColors.CyanGlow
                    )

                    if (!mapData?.summary.isNullOrBlank()) {
                        ReaperDivider(
                            modifier = Modifier.padding(vertical = 14.dp)
                        )

                        Text(
                            text = mapData?.summary.orEmpty(),
                            color = ReaperColors.SecondaryText,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            item {
                ReaperCard {
                    Text(
                        text = "WHAT REAPER WILL ANALYZE",
                        color = ReaperColors.CyanGlow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.7.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    AnalysisLine(
                        number = "01",
                        text = "Recommended Survivor perks"
                    )

                    AnalysisLine(
                        number = "02",
                        text = "Chase adjustments"
                    )

                    AnalysisLine(
                        number = "03",
                        text = "Generator and objective strategy"
                    )

                    AnalysisLine(
                        number = "04",
                        text = "Endgame planning"
                    )

                    AnalysisLine(
                        number = "05",
                        text = "Priority warnings"
                    )
                }
            }

            item {
                ReaperPrimaryButton(
                    text = if (canAnalyze) {
                        "GENERATE MATCH PLAN"
                    } else {
                        "MATCHUP INCOMPLETE"
                    },
                    onClick = {
                        if (canAnalyze) {
                            onAnalyzeClick(
                                selectedKiller,
                                selectedMap
                            )
                        }
                    },
                    enabled = canAnalyze
                )
            }

            item {
                ReaperSecondaryButton(
                    text = "CHANGE MAP",
                    onClick = onBackClick
                )
            }

            item {
                Text(
                    text = "THE ENTITY WATCHES. REAPER PREPARES.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 4.dp,
                            bottom = 24.dp
                        ),
                    color = Color(0xFF526268),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.1.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun MatchupSummaryRow(
    label: String,
    value: String,
    accentColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label,
                color = ReaperColors.SecondaryText,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = value,
                color = ReaperColors.PrimaryText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 23.sp
            )
        }

        ReaperBadge(
            text = "SELECTED",
            accentColor = accentColor
        )
    }
}

@Composable
private fun AnalysisLine(
    number: String,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ReaperBadge(
            text = number
        )

        Spacer(modifier = Modifier.padding(horizontal = 7.dp))

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            color = ReaperColors.PrimaryText,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}