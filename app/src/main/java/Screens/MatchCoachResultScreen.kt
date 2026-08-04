package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.MatchCoachRecommendation
import com.example.reapercompanion.models.Perk

@Composable
fun MatchCoachResultScreen(
    recommendation: MatchCoachRecommendation,
    onBackClick: () -> Unit,
    onAnalyzeAnotherClick: () -> Unit
) {
    var openedPerk by remember {
        mutableStateOf<Perk?>(null)
    }

    openedPerk?.let { perk ->
        PerkDetailsDialog(
            perk = perk,
            onDismiss = {
                openedPerk = null
            }
        )
    }

    val threatColor = when (
        recommendation.threatLevel.uppercase()
    ) {
        "LOW" -> Color(0xFF56D6A7)
        "MODERATE" -> Color(0xFFFFC857)
        "HIGH" -> Color(0xFFFF8A4C)
        "EXTREME" -> Color(0xFFFF5A5A)
        else -> ReaperColors.CyanGlow
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
                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "REAPER MATCH COACH",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.CyanGlow,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = recommendation.title,
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.PrimaryText,
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        "${recommendation.opponentName} • ${recommendation.mapName}",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )
            }

            item {
                MatchCoachOverviewPanel(
                    recommendation = recommendation,
                    threatColor = threatColor
                )
            }

            item {
                MatchCoachSummaryPanel(
                    summary = recommendation.summary,
                    threatColor = threatColor
                )
            }

            item {
                MatchCoachSectionTitle(
                    text = "RECOMMENDED PERKS"
                )
            }

            item {
                MatchCoachPerkGrid(
                    perks = recommendation.recommendedPerks,
                    onPerkClick = { perk ->
                        openedPerk = perk
                    }
                )
            }

            item {
                MatchCoachAdvicePanel(
                    title = "CHASE PLAN",
                    lines = recommendation.chaseAdvice,
                    accentColor = Color(0xFFFF784F),
                    bullet = "›"
                )
            }

            item {
                MatchCoachAdvicePanel(
                    title = "OBJECTIVE PLAN",
                    lines = recommendation.objectiveAdvice,
                    accentColor = ReaperColors.CyanGlow,
                    bullet = "✓"
                )
            }

            item {
                MatchCoachAdvicePanel(
                    title = "ENDGAME PLAN",
                    lines = recommendation.endgameAdvice,
                    accentColor = Color(0xFFB26BFF),
                    bullet = "›"
                )
            }

            item {
                MatchCoachAdvicePanel(
                    title = "WATCH OUT FOR",
                    lines = recommendation.warnings,
                    accentColor = Color(0xFFFF5A5A),
                    bullet = "!"
                )
            }

            item {
                Button(
                    onClick = onAnalyzeAnotherClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ReaperColors.CyanGlow,
                        contentColor = Color(0xFF001014)
                    )
                ) {
                    Text(
                        text = "ANALYZE ANOTHER MATCH",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            item {
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = ReaperColors.BorderActive
                    )
                ) {
                    Text(
                        text = "BACK TO DEAD BY DAYLIGHT",
                        color = ReaperColors.CyanGlow,
                        fontWeight = FontWeight.Bold
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
private fun MatchCoachOverviewPanel(
    recommendation: MatchCoachRecommendation,
    threatColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = threatColor.copy(alpha = 0.75f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            threatColor.copy(alpha = 0.19f),
                            ReaperColors.CardAvailable,
                            ReaperColors.CardBackground
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ReaperScoreGauge(
                    score = recommendation.score,
                    size = 140.dp
                )

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "THREAT LEVEL",
                        color = ReaperColors.SecondaryText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.3.sp
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = recommendation.threatLevel.uppercase(),
                        color = threatColor,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(17.dp))

                    Text(
                        text = "MATCH DIFFICULTY",
                        color = ReaperColors.SecondaryText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.1.sp
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = recommendation.difficulty.uppercase(),
                        color = ReaperColors.PrimaryText,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun MatchCoachSummaryPanel(
    summary: String,
    threatColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.2.dp,
            color = threatColor.copy(alpha = 0.55f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Text(
                text = "REAPER ASSESSMENT",
                color = threatColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.8.sp
            )

            Spacer(modifier = Modifier.height(11.dp))

            Text(
                text = summary,
                color = ReaperColors.PrimaryText,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun MatchCoachPerkGrid(
    perks: List<Perk>,
    onPerkClick: (Perk) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        perks.chunked(2).forEach { rowPerks ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowPerks.forEach { perk ->
                    MatchCoachPerkCard(
                        perk = perk,
                        onClick = {
                            onPerkClick(perk)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (rowPerks.size == 1) {
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun MatchCoachPerkCard(
    perk: Perk,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .pressScale()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.CyanGlow.copy(alpha = 0.55f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OnlinePerkImage(
                perk = perk,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = perk.name,
                modifier = Modifier.fillMaxWidth(),
                color = ReaperColors.PrimaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(7.dp))

            PerkCategoryBadge(
                category = perk.category
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "TAP FOR DETAILS",
                modifier = Modifier.fillMaxWidth(),
                color = ReaperColors.CyanGlow,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.9.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MatchCoachAdvicePanel(
    title: String,
    lines: List<String>,
    accentColor: Color,
    bullet: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.52f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Text(
                text = title,
                color = accentColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.8.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (lines.isEmpty()) {
                Text(
                    text = "No additional advice available.",
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp
                )
            } else {
                lines.forEach { line ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    ) {
                        Text(
                            text = bullet,
                            color = accentColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(end = 10.dp)
                        )

                        Text(
                            text = line,
                            color = ReaperColors.PrimaryText,
                            fontSize = 15.sp,
                            lineHeight = 21.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MatchCoachSectionTitle(
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