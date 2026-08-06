package com.example.reapercompanion.itemcoach

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperListCard
import com.example.reapercompanion.screens.AppBackground

@Composable
fun ItemCoachGoalScreen(
    onBackClick: () -> Unit,
    onGoalSelected: (String) -> Unit
) {
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
                    title = "ITEM COACH",
                    onBackClick = onBackClick
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "REAPER DECISION ENGINE",
                    title = "What Are You Trying to Do?",
                    body =
                        "Choose your objective and Reaper will recommend the item, add-ons, perks, offering, and strategy that best support it.",
                    accentColor = Color(0xFFFFC857),
                    badge = "FULL LOADOUT"
                )
            }

            items(
                items = ItemCoachGoals.all,
                key = { goal ->
                    goal.id
                }
            ) { goal ->
                ReaperListCard(
                    title = goal.title,
                    description = goal.description,
                    onClick = {
                        onGoalSelected(goal.id)
                    },
                    accentColor = ReaperColors.CyanGlow,
                    trailingText = "›"
                )
            }

            item {
                Text(
                    text =
                        "Reaper recommends the whole setup—not just four perks.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 4.dp,
                            bottom = 24.dp
                        ),
                    color = ReaperColors.SecondaryText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.6.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}