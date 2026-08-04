package com.example.reapercompanion.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.database.MapDatabase
import com.example.reapercompanion.design.ReaperBadge
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperListCard

@Composable
fun MatchCoachMapScreen(
    selectedKiller: String,
    onBackClick: () -> Unit,
    onMapSelected: (String) -> Unit
) {
    val maps = remember {
        MapDatabase.getAllNames()
    }

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
                Spacer(modifier = Modifier.height(16.dp))

                ReaperHeader(
                    title = "MATCH COACH",
                    onBackClick = onBackClick
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Step 2 of 3",
                        modifier = Modifier.weight(1f),
                        color = ReaperColors.CyanGlow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )

                    ReaperBadge(
                        text = selectedKiller.ifBlank {
                            "KILLER NOT SET"
                        },
                        accentColor = Color(0xFFFF6B6B)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "CHOOSE THE MAP",
                    color = ReaperColors.PrimaryText,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        "Select the map where the trial will take place. Reaper will combine the map layout with the Killer matchup.",
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "SELECTED KILLER",
                    title = selectedKiller.ifBlank {
                        "No Killer Selected"
                    },
                    body =
                        "Choose a map to continue to the final matchup summary.",
                    accentColor = ReaperColors.CyanGlow,
                    badge = "${maps.size} MAPS"
                )
            }

            items(
                items = maps,
                key = { map ->
                    map
                }
            ) { map ->
                val mapData = remember(map) {
                    MapDatabase.get(map)
                }

                ReaperListCard(
                    title = map,
                    description = mapData?.summary
                        ?: "Map-specific strategy and matchup planning",
                    onClick = {
                        onMapSelected(map)
                    },
                    accentColor = ReaperColors.CyanGlow,
                    trailingText = "›"
                )
            }

            item {
                Text(
                    text = "Tap a map to continue to the matchup summary.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 6.dp,
                            bottom = 24.dp
                        ),
                    color = ReaperColors.SecondaryText,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}