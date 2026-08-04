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
import com.example.reapercompanion.database.PerkDatabase
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.BuildRecommendation
import com.example.reapercompanion.models.FavoriteBuild
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.storage.FavoritesStorage

@Composable
fun BuildResultScreen(
    selectedGoal: String,
    onBackClick: () -> Unit,
    onGenerateAgainClick: () -> Unit
) {
    val context = LocalContext.current
    val build = getBuildForGoal(selectedGoal)

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

                Text(
                    text = "YOUR REAPER BUILD",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.CyanGlow,
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
                ScorePanel(
                    score = build.score,
                    difficulty = build.difficulty
                )
            }

            item {
                SectionHeading("RECOMMENDED PERKS")
            }

            item {
                PerkGrid(
                    perks = build.perks,
                    onPerkClick = { perk ->
                        selectedPerk = perk
                    }
                )
            }

            item {
                StrengthPanel(
                    strengths = build.strengths
                )
            }

            item {
                AlternativePerksPanel(
                    perks = build.alternatives,
                    onPerkClick = { perk ->
                        selectedPerk = perk
                    }
                )
            }

            item {
                ExplanationPanel(
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
                            ReaperColors.CyanGlow
                        },
                        contentColor = if (isSaved) {
                            Color.White
                        } else {
                            Color(0xFF001014)
                        }
                    )
                ) {
                    Text(
                        text = if (isSaved) {
                            "SAVED ✓"
                        } else {
                            "SAVE BUILD"
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
                        containerColor = ReaperColors.CyanDark,
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
                        color = ReaperColors.BorderActive
                    )
                ) {
                    Text(
                        text = "BACK TO SURVIVOR GOALS",
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
private fun ScorePanel(
    score: Int,
    difficulty: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = ReaperColors.BorderActive
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
                            Color(0xFF07343D),
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
                Column {
                    Text(
                        text = "REAPER SCORE",
                        color = ReaperColors.CyanGlow,
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
                        color = ReaperColors.CyanGlow,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun PerkGrid(
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
                    PerkCard(
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
private fun PerkCard(
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
            color = ReaperColors.BorderActive
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

            Text(
                text = "TAP FOR DETAILS",
                modifier = Modifier.fillMaxWidth(),
                color = ReaperColors.CyanGlow,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun StrengthPanel(
    strengths: List<String>
) {
    InformationPanel(
        title = "BUILD STRENGTHS",
        lines = strengths.map { strength ->
            "✓  $strength"
        }
    )
}

@Composable
private fun AlternativePerksPanel(
    perks: List<Perk>,
    onPerkClick: (Perk) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderActive
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
                color = ReaperColors.CyanGlow,
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
                        color = ReaperColors.BorderInactive
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
private fun InformationPanel(
    title: String,
    lines: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderActive
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
                color = ReaperColors.CyanGlow,
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
private fun ExplanationPanel(
    explanation: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderActive
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
                color = ReaperColors.CyanGlow,
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
private fun SectionHeading(
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

private fun getBuildForGoal(
    goal: String
): BuildRecommendation {
    return when (goal) {
        "LOOP BETTER" -> BuildRecommendation(
            name = "Loop Monster",
            goal = goal,
            score = 94,
            difficulty = "Medium",
            perks = listOf(
                PerkDatabase.windowsOfOpportunity,
                PerkDatabase.lithe,
                PerkDatabase.resilience,
                PerkDatabase.adrenaline
            ),
            alternatives = listOf(
                PerkDatabase.finesse,
                PerkDatabase.balancedLanding,
                PerkDatabase.quickAndQuiet,
                PerkDatabase.fiveMovesAhead
            ),
            strengths = listOf(
                "Strong chase pathing",
                "Creates distance after vaults",
                "Useful in solo queue",
                "Powerful endgame recovery"
            ),
            explanation =
                "This build helps you identify nearby resources, create distance after a fast vault, gain value while injured, and receive a powerful boost when the final generator is completed."
        )

        "RUSH GENERATORS" -> BuildRecommendation(
            name = "Generator Specialist",
            goal = goal,
            score = 91,
            difficulty = "Easy",
            perks = listOf(
                PerkDatabase.dejaVu,
                PerkDatabase.proveThyself,
                PerkDatabase.resilience,
                PerkDatabase.adrenaline
            ),
            alternatives = listOf(
                PerkDatabase.builtToLast,
                PerkDatabase.overzealous,
                PerkDatabase.stakeOut,
                PerkDatabase.hyperfocus
            ),
            strengths = listOf(
                "Fast objective progress",
                "Generator information",
                "Beginner friendly",
                "Strong coordinated repairs"
            ),
            explanation =
                "This build focuses on locating important generators, improving repair efficiency, and maintaining pressure on objectives throughout the match."
        )

        "SUPPORT THE TEAM" -> BuildRecommendation(
            name = "Guardian Angel",
            goal = goal,
            score = 90,
            difficulty = "Easy",
            perks = listOf(
                PerkDatabase.wellMakeIt,
                PerkDatabase.botanyKnowledge,
                PerkDatabase.empathy,
                PerkDatabase.kindred
            ),
            alternatives = listOf(
                PerkDatabase.desperateMeasures,
                PerkDatabase.aftercare,
                PerkDatabase.babysitter,
                PerkDatabase.leader
            ),
            strengths = listOf(
                "Fast healing",
                "Strong rescue value",
                "Team information",
                "Beginner friendly"
            ),
            explanation =
                "This support build helps you locate injured teammates, heal efficiently, make safer rescues, and provide useful information to the entire team."
        )

        "STEALTH" -> BuildRecommendation(
            name = "Vanishing Act",
            goal = goal,
            score = 87,
            difficulty = "Medium",
            perks = listOf(
                PerkDatabase.quickAndQuiet,
                PerkDatabase.lightweight,
                PerkDatabase.distortion,
                PerkDatabase.ironWill
            ),
            alternatives = listOf(
                PerkDatabase.danceWithMe,
                PerkDatabase.luckyBreak,
                PerkDatabase.calmSpirit,
                PerkDatabase.urbanEvasion
            ),
            strengths = listOf(
                "Quiet movement",
                "Reduced tracking information",
                "Strong line-of-sight breaks",
                "Useful against aura reading"
            ),
            explanation =
                "This stealth build reduces the information you leave behind and gives you tools for quietly breaking line of sight and disappearing during a chase."
        )

        "TROLL THE KILLER" -> BuildRecommendation(
            name = "Chaos Gremlin",
            goal = goal,
            score = 88,
            difficulty = "Hard",
            perks = listOf(
                PerkDatabase.headOn,
                PerkDatabase.quickAndQuiet,
                PerkDatabase.flashbang,
                PerkDatabase.deception
            ),
            alternatives = listOf(
                PerkDatabase.blastMine,
                PerkDatabase.diversion,
                PerkDatabase.powerStruggle,
                PerkDatabase.chemicalTrap
            ),
            strengths = listOf(
                "Funny coordinated plays",
                "Locker mind games",
                "Surprise saves",
                "Best with friends"
            ),
            explanation =
                "This build is designed for locker tricks, surprise stuns, fake movements, and funny saves. It works best with friends who can coordinate around the chaos."
        )

        "SURVIVE LONGER" -> BuildRecommendation(
            name = "Second Chance",
            goal = goal,
            score = 92,
            difficulty = "Easy",
            perks = listOf(
                PerkDatabase.offTheRecord,
                PerkDatabase.windowsOfOpportunity,
                PerkDatabase.lithe,
                PerkDatabase.adrenaline
            ),
            alternatives = listOf(
                PerkDatabase.decisiveStrike,
                PerkDatabase.unbreakable,
                PerkDatabase.deliverance,
                PerkDatabase.deadHard
            ),
            strengths = listOf(
                "Protection after unhook",
                "Reliable chase information",
                "Strong escape potential",
                "Good for solo queue"
            ),
            explanation =
                "This build gives you information, distance, protection after being unhooked, and a strong opportunity to escape during the final stage of the match."
        )

        else -> BuildRecommendation(
            name = "Reaper's Choice",
            goal = goal,
            score = 89,
            difficulty = "Medium",
            perks = listOf(
                PerkDatabase.kindred,
                PerkDatabase.lithe,
                PerkDatabase.dejaVu,
                PerkDatabase.wellMakeIt
            ),
            alternatives = listOf(
                PerkDatabase.resilience,
                PerkDatabase.distortion,
                PerkDatabase.bond,
                PerkDatabase.adrenaline
            ),
            strengths = listOf(
                "Balanced utility",
                "Good information",
                "Reliable mobility",
                "Team support"
            ),
            explanation =
                "This balanced build provides information, chase mobility, objective guidance, and faster healing after rescues."
        )
    }
}