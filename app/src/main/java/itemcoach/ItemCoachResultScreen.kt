package com.example.reapercompanion.itemcoach

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.reapercompanion.screens.AppBackground

@Composable
fun ItemCoachResultScreen(
    recommendationSet: ItemCoachRecommendationSet,
    onBackClick: () -> Unit,
    onChooseAnotherClick: () -> Unit
) {
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
                    title = "ITEM COACH",
                    onBackClick = onBackClick
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "REAPER DECISION ENGINE",
                    title = recommendationSet.goalTitle,
                    body = "Reaper ranked the strongest loadouts for this objective.",
                    accentColor = Color(0xFFFFC857),
                    badge = "RANKED"
                )
            }

            if (recommendationSet.recommendations.isEmpty()) {
                item {
                    ReaperCard {
                        Text(
                            text = "Recommendations for this goal are coming soon.",
                            modifier = Modifier.fillMaxWidth(),
                            color = ReaperColors.SecondaryText,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(
                    items = recommendationSet.recommendations,
                    key = { recommendation -> recommendation.tier.name }
                ) { recommendation ->
                    RankedRecommendationCard(
                        recommendation = recommendation
                    )
                }
            }

            if (recommendationSet.nextUnlock.isNotEmpty()) {
                item { ReaperDivider() }

                item {
                    ResultSectionHeader(
                        title = "NEXT UNLOCK",
                        badge = recommendationSet.nextUnlock.size.toString(),
                        accentColor = Color(0xFFB38CFF)
                    )
                }

                item {
                    ReaperCard(
                        accentColor = Color(0xFFB38CFF)
                    ) {
                        Text(
                            text = "If you unlock one thing next, work toward these:",
                            color = ReaperColors.SecondaryText,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        recommendationSet.nextUnlock.forEachIndexed { index, unlock ->
                            Text(
                                text = "${index + 1}. $unlock",
                                color = ReaperColors.PrimaryText,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 3.dp)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))

                ReaperSecondaryButton(
                    text = "CHOOSE ANOTHER GOAL",
                    onClick = onChooseAnotherClick
                )
            }

            item {
                ReaperSecondaryButton(
                    text = "BACK",
                    onClick = onBackClick
                )
            }

            item {
                Text(
                    text = "REAPER RECOMMENDS. YOU EXECUTE.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 24.dp),
                    color = Color(0xFF526268),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun RankedRecommendationCard(
    recommendation: RankedRecommendation
) {
    val accentColor = when (recommendation.tier) {
        RecommendationTier.REAPER_CHOICE -> Color(0xFFFFC857)
        RecommendationTier.STRONG_ALTERNATIVE -> ReaperColors.CyanGlow
        RecommendationTier.BUDGET -> Color(0xFF56D6A7)
    }

    val tierLabel = when (recommendation.tier) {
        RecommendationTier.REAPER_CHOICE -> "REAPER'S CHOICE"
        RecommendationTier.STRONG_ALTERNATIVE -> "STRONG ALTERNATIVE"
        RecommendationTier.BUDGET -> "BUDGET CHOICE"
    }

    val stars = when (recommendation.tier) {
        RecommendationTier.REAPER_CHOICE -> "★★★★★"
        RecommendationTier.STRONG_ALTERNATIVE -> "★★★★☆"
        RecommendationTier.BUDGET -> "★★★☆☆"
    }

    ReaperCard(
        accentColor = accentColor
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = tierLabel,
                    color = accentColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.3.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = recommendation.title,
                    color = ReaperColors.PrimaryText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Black
                )
            }

            ReaperBadge(
                text = stars,
                accentColor = accentColor
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DetailBlock(
            label = "ITEM",
            values = listOf(recommendation.item),
            accentColor = accentColor
        )

        Spacer(modifier = Modifier.height(14.dp))

        DetailBlock(
            label = "ADD-ONS",
            values = recommendation.addOns,
            accentColor = ReaperColors.CyanGlow
        )

        Spacer(modifier = Modifier.height(14.dp))

        DetailBlock(
            label = "PERKS",
            values = recommendation.perks,
            accentColor = Color(0xFF56D6A7)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "WHY THIS WORKS",
            color = accentColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = recommendation.whyThisWorks,
            color = ReaperColors.SecondaryText,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        ReaperDivider()
        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "REAPER EXECUTION TIP",
            color = Color(0xFFFF6B6B),
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = recommendation.executionTip,
            color = ReaperColors.PrimaryText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun DetailBlock(
    label: String,
    values: List<String>,
    accentColor: Color
) {
    Text(
        text = label,
        color = accentColor,
        fontSize = 11.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = 1.2.sp
    )

    Spacer(modifier = Modifier.height(6.dp))

    values.forEach { value ->
        Text(
            text = "• $value",
            color = ReaperColors.PrimaryText,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}

@Composable
private fun ResultSectionHeader(
    title: String,
    badge: String,
    accentColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = ReaperColors.PrimaryText,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        ReaperBadge(
            text = badge,
            accentColor = accentColor
        )
    }
}