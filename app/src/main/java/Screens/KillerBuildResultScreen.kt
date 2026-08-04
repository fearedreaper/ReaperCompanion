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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.database.KillerBuildDatabase
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.FavoriteBuild
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.storage.FavoritesStorage

@Composable
fun KillerBuildResultScreen(
    selectedGoal: String,
    onBackClick: () -> Unit,
    onGenerateAgainClick: () -> Unit
) {
    val context = LocalContext.current
    val build = KillerBuildDatabase.getBuild(selectedGoal)

    val favoriteBuild = FavoriteBuild(
        name = build.name,
        goal = build.goal,
        score = build.score,
        difficulty = build.difficulty,
        perks = build.perks.map { it.name }
    )

    var isSaved by remember(selectedGoal) {
        mutableStateOf(
            FavoritesStorage.isFavorite(
                context = context,
                build = favoriteBuild
            )
        )
    }

    var selectedPerk by remember {
        mutableStateOf<Perk?>(null)
    }

    selectedPerk?.let { perk ->
        PerkDetailsDialog(
            perk = perk,
            onDismiss = {
                selectedPerk = null
            }
        )
    }

    AppBackground {
        LazyColumn(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(18.dp))
            }

            item {
                KillerPortraitHeader(
                    goal = build.goal
                )
            }

            item {
                Text(
                    text = "YOUR KILLER BUILD",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFF6B6B),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = build.name,
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.PrimaryText,
                    fontSize = 31.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = build.goal,
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }

            item {
                KillerScorePanel(
                    score = build.score,
                    difficulty = build.difficulty
                )
            }

            item {
                KillerSectionHeading(
                    text = "RECOMMENDED PERKS"
                )
            }

            item {
                KillerPerkGrid(
                    perks = build.perks,
                    onPerkClick = { perk ->
                        selectedPerk = perk
                    }
                )
            }

            item {
                KillerInformationPanel(
                    title = "BUILD STRENGTHS",
                    lines = build.strengths.map { strength ->
                        "✓  $strength"
                    }
                )
            }

            item {
                KillerAlternativePerksPanel(
                    perks = build.alternatives,
                    onPerkClick = { perk ->
                        selectedPerk = perk
                    }
                )
            }

            item {
                KillerExplanationPanel(
                    explanation = build.explanation
                )
            }

            item {
                Button(
                    onClick = {
                        if (isSaved) {
                            FavoritesStorage.deleteFavorite(
                                context = context,
                                build = favoriteBuild
                            )

                            isSaved = false
                        } else {
                            FavoritesStorage.saveFavorite(
                                context = context,
                                build = favoriteBuild
                            )

                            isSaved = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(17.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSaved) {
                            Color(0xFF167A62)
                        } else {
                            Color(0xFFE24A4A)
                        },
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (isSaved) {
                            "SAVED ✓"
                        } else {
                            "SAVE KILLER BUILD"
                        },
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp
                    )
                }
            }

            item {
                Button(
                    onClick = onGenerateAgainClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(17.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7A2027),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "GENERATE ANOTHER BUILD",
                        fontWeight = FontWeight.Bold
                    )
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
                        color = Color(0x99E24A4A)
                    )
                ) {
                    Text(
                        text = "BACK TO KILLER GOALS",
                        color = Color(0xFFFF6B6B),
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
private fun KillerScorePanel(
    score: Int,
    difficulty: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = Color(0x99E24A4A)
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
                            Color(0xFF47171C),
                            Color(0xFF251316),
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
                Column {
                    Text(
                        text = "REAPER SCORE",
                        color = Color(0xFFFF6B6B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = score.toString(),
                        color = ReaperColors.PrimaryText,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        text = "OUT OF 100",
                        color = ReaperColors.SecondaryText,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "DIFFICULTY",
                        color = ReaperColors.SecondaryText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = difficulty,
                        color = ReaperColors.PrimaryText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "★★★★☆",
                        color = Color(0xFFFF6B6B),
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun KillerPerkGrid(
    perks: List<Perk>,
    onPerkClick: (Perk) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        perks.chunked(2).forEach { perkRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                perkRow.forEach { perk ->
                    KillerPerkCard(
                        perk = perk,
                        onClick = {
                            onPerkClick(perk)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (perkRow.size == 1) {
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun KillerPerkCard(
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
            color = Color(0x99E24A4A)
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

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = perk.name,
                modifier = Modifier.fillMaxWidth(),
                color = ReaperColors.PrimaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            PerkCategoryBadge(
                category = perk.category
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "TAP FOR DETAILS",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFFF6B6B),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun KillerAlternativePerksPanel(
    perks: List<Perk>,
    onPerkClick: (Perk) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0x99E24A4A)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Text(
                text = "ALTERNATIVE PERKS",
                color = Color(0xFFFF6B6B),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            perks.forEach { perk ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clickable {
                            onPerkClick(perk)
                        },
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0x66E24A4A)
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF171012)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Text(
                            text = perk.name,
                            color = ReaperColors.PrimaryText,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "${perk.owner} • Tap for details",
                            color = ReaperColors.SecondaryText,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun KillerInformationPanel(
    title: String,
    lines: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0x99E24A4A)
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
                color = Color(0xFFFF6B6B),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            lines.forEach { line ->
                Text(
                    text = line,
                    color = ReaperColors.PrimaryText,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun KillerExplanationPanel(
    explanation: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0x99E24A4A)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Text(
                text = "WHY THIS BUILD WORKS",
                color = Color(0xFFFF6B6B),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = explanation,
                color = ReaperColors.SecondaryText,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
private fun KillerSectionHeading(
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