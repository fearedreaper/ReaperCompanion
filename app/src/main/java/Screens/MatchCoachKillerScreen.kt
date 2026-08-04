package com.example.reapercompanion.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.database.MatchCoachEngine
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperListCard

@Composable
fun MatchCoachKillerScreen(
    onBackClick: () -> Unit,
    onKillerSelected: (String) -> Unit
) {
    val killers = remember {
        MatchCoachEngine.getSupportedKillers()
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
                    onBackClick = onBackClick,
                    accentColor = Color(0xFFFF6B6B)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Step 1 of 3",
                    color = Color(0xFFFF6B6B),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "CHOOSE THE KILLER",
                    color = ReaperColors.PrimaryText,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        "Select the Killer you are preparing to face. Reaper will use that matchup when building your plan.",
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "REAPER MATCH COACH",
                    title = "Know the Threat",
                    body =
                        "Each Killer changes how you should route, repair, rescue, and prepare for endgame.",
                    accentColor = Color(0xFFFF6B6B),
                    badge = "${killers.size} KILLERS"
                )
            }

            items(
                items = killers,
                key = { killer ->
                    killer
                }
            ) { killer ->
                ReaperListCard(
                    title = killer,
                    description = killerSubtitle(killer),
                    onClick = {
                        onKillerSelected(killer)
                    },
                    accentColor = Color(0xFFFF6B6B),
                    trailingText = "›"
                )
            }

            item {
                Text(
                    text = "Tap a Killer to continue to map selection.",
                    modifier = Modifier
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
            "High-speed pressure, fast rotations, and aggressive chase power"

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

        normalized.contains("doctor") ||
                normalized.contains("clown") ||
                normalized.contains("legion") ||
                normalized.contains("plague") ||
                normalized.contains("cenobite") ||
                normalized.contains("dredge") ||
                normalized.contains("twins") ||
                normalized.contains("nightmare") ||
                normalized.contains("demogorgon") ||
                normalized.contains("lich") ||
                normalized.contains("krasue") ->
            "Disruption, secondary objectives, and team-wide pressure"

        else ->
            "Matchup-specific strategy, counters, and recommended perks"
    }
}