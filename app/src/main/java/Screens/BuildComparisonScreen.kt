package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.BuildRecommendation
import com.example.reapercompanion.models.Perk

@Composable
fun BuildComparisonScreen(
    firstBuild: BuildRecommendation,
    secondBuild: BuildRecommendation,
    onBackClick: () -> Unit,
    onChooseFirstClick: () -> Unit,
    onChooseSecondClick: () -> Unit
) {
    val firstWins = calculateComparisonWins(
        build = firstBuild,
        opponent = secondBuild
    )

    val secondWins = calculateComparisonWins(
        build = secondBuild,
        opponent = firstBuild
    )

    AppBackground {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "COMPARE BUILDS",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.CyanGlow,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Which loadout fits you better?",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.PrimaryText,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = "Compare score, difficulty, perks, and strengths before choosing.",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CompactBuildHeader(
                        label = "BUILD A",
                        build = firstBuild,
                        comparisonWins = firstWins,
                        accentColor = ReaperColors.CyanGlow,
                        modifier = Modifier.weight(1f)
                    )

                    CompactBuildHeader(
                        label = "BUILD B",
                        build = secondBuild,
                        comparisonWins = secondWins,
                        accentColor = Color(0xFFB26BFF),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                ComparisonSectionTitle(
                    text = "PERK LOADOUT"
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CompactPerkList(
                        perks = firstBuild.perks,
                        accentColor = ReaperColors.CyanGlow,
                        modifier = Modifier.weight(1f)
                    )

                    CompactPerkList(
                        perks = secondBuild.perks,
                        accentColor = Color(0xFFB26BFF),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                ComparisonSectionTitle(
                    text = "BUILD STRENGTHS"
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CompactStrengthList(
                        strengths = firstBuild.strengths,
                        accentColor = ReaperColors.CyanGlow,
                        modifier = Modifier.weight(1f)
                    )

                    CompactStrengthList(
                        strengths = secondBuild.strengths,
                        accentColor = Color(0xFFB26BFF),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                ComparisonSummaryPanel(
                    firstBuild = firstBuild,
                    secondBuild = secondBuild,
                    firstWins = firstWins,
                    secondWins = secondWins
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onChooseFirstClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(58.dp),
                        shape = RoundedCornerShape(17.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ReaperColors.CyanGlow,
                            contentColor = Color(0xFF001014)
                        )
                    ) {
                        Text(
                            text = "CHOOSE A",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Button(
                        onClick = onChooseSecondClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(58.dp),
                        shape = RoundedCornerShape(17.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB26BFF),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "CHOOSE B",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            item {
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(17.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = ReaperColors.BorderActive
                    )
                ) {
                    Text(
                        text = "BACK",
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
private fun CompactBuildHeader(
    label: String,
    build: BuildRecommendation,
    comparisonWins: Int,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.4.dp,
            color = accentColor.copy(alpha = 0.75f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            accentColor.copy(alpha = 0.22f),
                            ReaperColors.CardBackground
                        )
                    )
                )
                .padding(15.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = label,
                    color = accentColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.4.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = build.name,
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.PrimaryText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = build.score.toString(),
                    color = accentColor,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "REAPER SCORE",
                    color = ReaperColors.SecondaryText,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(11.dp))

                Text(
                    text = build.difficulty.uppercase(),
                    color = ReaperColors.PrimaryText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$comparisonWins COMPARISON POINTS",
                    modifier = Modifier.fillMaxWidth(),
                    color = accentColor,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 13.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun CompactPerkList(
    perks: List<Perk>,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.55f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            perks.take(4).forEachIndexed { index, perk ->
                Column {
                    Text(
                        text = "${index + 1}. ${perk.name}",
                        color = ReaperColors.PrimaryText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = perk.category.name
                            .replace("_", " "),
                        color = accentColor,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun CompactStrengthList(
    strengths: List<String>,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.55f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            strengths.take(4).forEach { strength ->
                Text(
                    text = "✓ $strength",
                    color = ReaperColors.PrimaryText,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun ComparisonSummaryPanel(
    firstBuild: BuildRecommendation,
    secondBuild: BuildRecommendation,
    firstWins: Int,
    secondWins: Int
) {
    val recommendation = when {
        firstWins > secondWins ->
            "${firstBuild.name} has the stronger overall comparison."

        secondWins > firstWins ->
            "${secondBuild.name} has the stronger overall comparison."

        firstBuild.score > secondBuild.score ->
            "${firstBuild.name} wins the tie with a higher Reaper Score."

        secondBuild.score > firstBuild.score ->
            "${secondBuild.name} wins the tie with a higher Reaper Score."

        else ->
            "These builds are evenly matched. Choose based on your preferred perks."
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.2.dp,
            color = Color(0xFFFFC857).copy(alpha = 0.7f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF17150D)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "REAPER RECOMMENDATION",
                color = Color(0xFFFFC857),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.6.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = recommendation,
                color = ReaperColors.PrimaryText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
private fun ComparisonSectionTitle(
    text: String
) {
    Text(
        text = text,
        color = ReaperColors.PrimaryText,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp
    )
}

private fun calculateComparisonWins(
    build: BuildRecommendation,
    opponent: BuildRecommendation
): Int {
    var wins = 0

    if (build.score > opponent.score) {
        wins += 2
    }

    if (
        difficultyRank(build.difficulty) <
        difficultyRank(opponent.difficulty)
    ) {
        wins += 1
    }

    if (build.strengths.size > opponent.strengths.size) {
        wins += 1
    }

    if (
        build.perks
            .map { perk -> perk.category }
            .distinct()
            .size >
        opponent.perks
            .map { perk -> perk.category }
            .distinct()
            .size
    ) {
        wins += 1
    }

    return wins
}

private fun difficultyRank(
    difficulty: String
): Int {
    return when (difficulty.lowercase()) {
        "easy" -> 1
        "medium" -> 2
        "hard" -> 3
        "expert" -> 4
        else -> 3
    }
}