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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.R
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
    val localizedGoals = listOf(
        ItemCoachGoal(
            id = "blind_killer",
            title = stringResource(R.string.itemcoach_goal_blind_title),
            description = stringResource(R.string.itemcoach_goal_blind_description)
        ),
        ItemCoachGoal(
            id = "heal_faster",
            title = stringResource(R.string.itemcoach_goal_heal_title),
            description = stringResource(R.string.itemcoach_goal_heal_description)
        ),
        ItemCoachGoal(
            id = "rush_generators",
            title = stringResource(R.string.itemcoach_goal_generators_title),
            description = stringResource(R.string.itemcoach_goal_generators_description)
        ),
        ItemCoachGoal(
            id = "sabotage_hooks",
            title = stringResource(R.string.itemcoach_goal_sabotage_title),
            description = stringResource(R.string.itemcoach_goal_sabotage_description)
        ),
        ItemCoachGoal(
            id = "support_teammates",
            title = stringResource(R.string.itemcoach_goal_support_title),
            description = stringResource(R.string.itemcoach_goal_support_description)
        ),
        ItemCoachGoal(
            id = "stealth",
            title = stringResource(R.string.itemcoach_goal_stealth_title),
            description = stringResource(R.string.itemcoach_goal_stealth_description)
        )
    )

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
                    title = stringResource(R.string.itemcoach_title),
                    onBackClick = onBackClick
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = stringResource(R.string.itemcoach_eyebrow),
                    title = stringResource(R.string.itemcoach_goal_title),
                    body = stringResource(R.string.itemcoach_goal_body),
                    accentColor = Color(0xFFFFC857),
                    badge = stringResource(R.string.itemcoach_full_loadout)
                )
            }

            items(
                items = localizedGoals,
                key = { goal -> goal.id }
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
                    text = stringResource(R.string.itemcoach_footer),
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