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
import com.example.reapercompanion.database.BuildStyleEngine
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.BuildRecommendation
import com.example.reapercompanion.models.BuildStyle
import com.example.reapercompanion.models.FavoriteBuild
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkRole
import com.example.reapercompanion.storage.FavoritesStorage

@Composable
fun BuildAroundResultScreen(
    selectedPerk: Perk,
    selectedStyle: BuildStyle,
    comparisonBuildExists: Boolean,
    onCompareClick: (BuildRecommendation) -> Unit,
    onBackClick: () -> Unit,
    onChooseAnotherClick: () -> Unit
) {
    val context = LocalContext.current

    val build = remember(selectedPerk.id, selectedStyle) {
        BuildStyleEngine.generateStyledBuild(
            selectedPerk = selectedPerk,
            style = selectedStyle
        )
    }

    val accentColor = when (selectedPerk.role) {
        PerkRole.SURVIVOR -> ReaperColors.CyanGlow
        PerkRole.KILLER -> Color(0xFFFF6B6B)
    }

    val roleLabel = when (selectedPerk.role) {
        PerkRole.SURVIVOR -> "SURVIVOR"
        PerkRole.KILLER -> "KILLER"
    }

    val favoriteBuild = FavoriteBuild(
        name = build.name,
        goal = build.goal,
        score = build.score,
        difficulty = build.difficulty,
        perks = build.perks.map { perk ->
            perk.name
        }
    )

    var isSaved by remember(selectedPerk.id, selectedStyle) {
        mutableStateOf(
            FavoritesStorage.isFavorite(
                context = context,
                build = favoriteBuild
            )
        )
    }

    var openedPerk by remember {
        mutableStateOf<Perk?>(null)
    }

    var showReveal by remember(
        selectedPerk.id,
        selectedStyle
    ) {
        mutableStateOf(true)
    }

    openedPerk?.let { perk ->
        PerkDetailsDialog(
            perk = perk,
            onDismiss = {
                openedPerk = null
            }
        )
    }

    if (showReveal) {
        AnimatedBuildReveal(
            perks = build.perks,
            score = build.score,
            accentColor = accentColor,
            onFinished = {
                showReveal = false
            }
        )
    } else {
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

                    Text(
                        text = "BUILD AROUND A PERK",
                        modifier = Modifier.fillMaxWidth(),
                        color = accentColor,
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
                        fontSize = 29.sp,
                        fontWeight = FontWeight.Black,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "$roleLabel LOADOUT",
                        modifier = Modifier.fillMaxWidth(),
                        color = ReaperColors.SecondaryText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.4.sp,
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    CorePerkPanel(
                        perk = selectedPerk,
                        accentColor = accentColor,
                        onClick = {
                            openedPerk = selectedPerk
                        }
                    )
                }

                item {
                    BuildAroundScorePanel(
                        score = build.score,
                        difficulty = build.difficulty,
                        accentColor = accentColor
                    )
                }

                item {
                    Text(
                        text = "GENERATED LOADOUT",
                        color = ReaperColors.PrimaryText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                }

                item {
                    BuildAroundPerkGrid(
                        perks = build.perks,
                        accentColor = accentColor,
                        onPerkClick = { perk ->
                            openedPerk = perk
                        }
                    )
                }

                item {
                    BuildAroundInformationPanel(
                        title = "BUILD STRENGTHS",
                        accentColor = accentColor,
                        lines = build.strengths.map { strength ->
                            "✓  $strength"
                        }
                    )
                }

                item {
                    BuildAroundAlternativesPanel(
                        perks = build.alternatives,
                        accentColor = accentColor,
                        onPerkClick = { perk ->
                            openedPerk = perk
                        }
                    )
                }

                item {
                    BuildAroundExplanationPanel(
                        explanation = build.explanation,
                        accentColor = accentColor
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
                                accentColor
                            },
                            contentColor = if (
                                selectedPerk.role == PerkRole.SURVIVOR &&
                                !isSaved
                            ) {
                                Color(0xFF001014)
                            } else {
                                Color.White
                            }
                        )
                    ) {
                        Text(
                            text = if (isSaved) {
                                "SAVED ✓"
                            } else {
                                "SAVE GENERATED BUILD"
                            },
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }


                item {
                    Button(
                        onClick = {
                            onCompareClick(build)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),
                        shape = RoundedCornerShape(17.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB26BFF),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = if (comparisonBuildExists) {
                                "COMPARE WITH BUILD A"
                            } else {
                                "SAVE AS BUILD A"
                            },
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                item {
                    Button(
                        onClick = onChooseAnotherClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),
                        shape = RoundedCornerShape(17.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accentColor.copy(alpha = 0.28f),
                            contentColor = accentColor
                        )
                    ) {
                        Text(
                            text = "CHOOSE ANOTHER PERK",
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
                            color = accentColor.copy(alpha = 0.65f)
                        )
                    ) {
                        Text(
                            text = "BACK TO DEAD BY DAYLIGHT",
                            color = accentColor,
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
}

@Composable
private fun CorePerkPanel(
    perk: Perk,
    accentColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pressScale()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.5.dp,
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
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            accentColor.copy(alpha = 0.22f),
                            ReaperColors.CardBackground
                        )
                    )
                )
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OnlinePerkImage(
                    perk = perk,
                    modifier = Modifier
                        .fillMaxWidth(0.34f)
                        .aspectRatio(1f)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = "CORE PERK",
                        color = accentColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = perk.name,
                        color = ReaperColors.PrimaryText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = perk.owner,
                        color = ReaperColors.SecondaryText,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PerkCategoryBadge(
                        category = perk.category
                    )
                }
            }
        }
    }
}

@Composable
private fun BuildAroundScorePanel(
    score: Int,
    difficulty: String,
    accentColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = accentColor.copy(alpha = 0.7f)
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
                            accentColor.copy(alpha = 0.2f),
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
                    score = score,
                    size = 138.dp,
                    accentColor = accentColor
                )

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "BUILD DIFFICULTY",
                        color = ReaperColors.SecondaryText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = difficulty.uppercase(),
                        color = ReaperColors.PrimaryText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = when (difficulty.lowercase()) {
                            "easy" -> "BEGINNER FRIENDLY"
                            "medium" -> "MODERATE"
                            "hard" -> "HIGH SKILL"
                            "expert" -> "EXPERT"
                            else -> "CUSTOM"
                        },
                        color = accentColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun BuildAroundPerkGrid(
    perks: List<Perk>,
    accentColor: Color,
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
                    BuildAroundGeneratedPerkCard(
                        perk = perk,
                        accentColor = accentColor,
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
private fun BuildAroundGeneratedPerkCard(
    perk: Perk,
    accentColor: Color,
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
            color = accentColor.copy(alpha = 0.65f)
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

            Spacer(modifier = Modifier.height(11.dp))

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
                color = accentColor,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun BuildAroundAlternativesPanel(
    perks: List<Perk>,
    accentColor: Color,
    onPerkClick: (Perk) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.55f)
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
                color = accentColor,
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
                        color = accentColor.copy(alpha = 0.3f)
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF0C1216)
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
private fun BuildAroundInformationPanel(
    title: String,
    accentColor: Color,
    lines: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.55f)
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
private fun BuildAroundExplanationPanel(
    explanation: String,
    accentColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.55f)
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
                color = accentColor,
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