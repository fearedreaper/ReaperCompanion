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
    recommendation: ItemCoachRecommendation,
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
                    title = recommendation.goalTitle,
                    body =
                        "Reaper built a complete loadout around your objective.",
                    accentColor = Color(0xFFFFC857),
                    badge = "RECOMMENDED"
                )
            }

            item {
                ResultSectionHeader(
                    title = "RECOMMENDED ITEM",
                    badge = "ITEM",
                    accentColor = Color(0xFFFFC857)
                )
            }

            item {
                ReaperCard(
                    accentColor = Color(0xFFFFC857)
                ) {
                    Text(
                        text = recommendation.recommendedItem,
                        color = ReaperColors.PrimaryText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            item {
                ResultSectionHeader(
                    title = "BEST ADD-ONS",
                    badge = recommendation.recommendedAddOns.size.toString(),
                    accentColor = ReaperColors.CyanGlow
                )
            }

            items(
                items = recommendation.recommendedAddOns,
                key = { addOn -> addOn }
            ) { addOn ->
                SimpleResultCard(
                    title = addOn,
                    accentColor = ReaperColors.CyanGlow
                )
            }

            item {
                ReaperDivider()
            }

            item {
                ResultSectionHeader(
                    title = "RECOMMENDED PERKS",
                    badge = recommendation.recommendedPerks.size.toString(),
                    accentColor = Color(0xFF56D6A7)
                )
            }

            items(
                items = recommendation.recommendedPerks,
                key = { perk -> perk }
            ) { perk ->
                SimpleResultCard(
                    title = perk,
                    accentColor = Color(0xFF56D6A7)
                )
            }

            recommendation.recommendedOffering?.let { offering ->
                item {
                    ReaperDivider()
                }

                item {
                    ResultSectionHeader(
                        title = "RECOMMENDED OFFERING",
                        badge = "OPTIONAL",
                        accentColor = Color(0xFFB38CFF)
                    )
                }

                item {
                    SimpleResultCard(
                        title = offering,
                        accentColor = Color(0xFFB38CFF)
                    )
                }
            }

            item {
                ReaperDivider()
            }

            item {
                ResultSectionHeader(
                    title = "WHY THIS WORKS",
                    badge = "STRATEGY",
                    accentColor = Color(0xFFFFC857)
                )
            }

            item {
                ReaperCard(
                    accentColor = Color(0xFFFFC857)
                ) {
                    Text(
                        text = recommendation.explanation,
                        color = ReaperColors.SecondaryText,
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
            }

            if (recommendation.warnings.isNotEmpty()) {
                item {
                    ReaperDivider()
                }

                item {
                    ResultSectionHeader(
                        title = "REAPER WARNINGS",
                        badge = recommendation.warnings.size.toString(),
                        accentColor = Color(0xFFFF6B6B)
                    )
                }

                items(
                    items = recommendation.warnings,
                    key = { warning -> warning }
                ) { warning ->
                    DetailResultCard(
                        title = "WARNING",
                        body = warning,
                        accentColor = Color(0xFFFF6B6B)
                    )
                }
            }

            if (recommendation.alternatives.isNotEmpty()) {
                item {
                    ReaperDivider()
                }

                item {
                    ResultSectionHeader(
                        title = "ALTERNATIVES",
                        badge = recommendation.alternatives.size.toString(),
                        accentColor = ReaperColors.CyanGlow
                    )
                }

                items(
                    items = recommendation.alternatives,
                    key = { alternative -> alternative }
                ) { alternative ->
                    DetailResultCard(
                        title = "OPTION",
                        body = alternative,
                        accentColor = ReaperColors.CyanGlow
                    )
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
                    text = "REAPER RECOMMENDS THE WHOLE LOADOUT—NOT JUST FOUR PERKS.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 4.dp,
                            bottom = 24.dp
                        ),
                    color = Color(0xFF526268),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    lineHeight = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
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

@Composable
private fun SimpleResultCard(
    title: String,
    accentColor: Color
) {
    ReaperCard(
        accentColor = accentColor
    ) {
        Text(
            text = title,
            color = ReaperColors.PrimaryText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DetailResultCard(
    title: String,
    body: String,
    accentColor: Color
) {
    ReaperCard(
        accentColor = accentColor
    ) {
        Column {
            Text(
                text = title,
                color = accentColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = body,
                color = ReaperColors.SecondaryText,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}