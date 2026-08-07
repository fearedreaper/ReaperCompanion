package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

data class KillerGoal(
    val id: String,
    val title: String,
    val subtitle: String
)

@Composable
fun KillerScreen(
    onBackClick: () -> Unit,
    onGoalClick: (String) -> Unit
) {
    val goals = listOf(
        KillerGoal(
            id = "GEN REGRESSION",
            title = stringResource(R.string.killer_goal_gen_regression_title),
            subtitle = stringResource(R.string.killer_goal_gen_regression_subtitle)
        ),
        KillerGoal(
            id = "ANTI-LOOP",
            title = stringResource(R.string.killer_goal_anti_loop_title),
            subtitle = stringResource(R.string.killer_goal_anti_loop_subtitle)
        ),
        KillerGoal(
            id = "AURA READING",
            title = stringResource(R.string.killer_goal_aura_reading_title),
            subtitle = stringResource(R.string.killer_goal_aura_reading_subtitle)
        ),
        KillerGoal(
            id = "STEALTH",
            title = stringResource(R.string.killer_goal_stealth_title),
            subtitle = stringResource(R.string.killer_goal_stealth_subtitle)
        ),
        KillerGoal(
            id = "HEX BUILD",
            title = stringResource(R.string.killer_goal_hex_title),
            subtitle = stringResource(R.string.killer_goal_hex_subtitle)
        ),
        KillerGoal(
            id = "ENDGAME",
            title = stringResource(R.string.killer_goal_endgame_title),
            subtitle = stringResource(R.string.killer_goal_endgame_subtitle)
        ),
        KillerGoal(
            id = "BEGINNER",
            title = stringResource(R.string.killer_goal_beginner_title),
            subtitle = stringResource(R.string.killer_goal_beginner_subtitle)
        ),
        KillerGoal(
            id = "MEME BUILD",
            title = stringResource(R.string.killer_goal_meme_title),
            subtitle = stringResource(R.string.killer_goal_meme_subtitle)
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
                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = stringResource(R.string.killer_builder_title),
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.PrimaryText,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = stringResource(R.string.killer_builder_prompt),
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onBackClick),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = ReaperColors.BorderActive
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF0C151A)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.killer_back_to_dbd),
                        modifier = Modifier.padding(16.dp),
                        color = ReaperColors.CyanGlow,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            goals.forEach { goal ->
                item {
                    KillerGoalCard(
                        goal = goal,
                        onClick = {
                            onGoalClick(goal.id)
                        }
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
private fun KillerGoalCard(
    goal: KillerGoal,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = Color(0x99E24A4A)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF171012)
        )
    ) {
        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Text(
                text = goal.title,
                color = Color(0xFFFF6B6B),
                fontSize = 17.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.2.sp
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = goal.subtitle,
                color = ReaperColors.SecondaryText,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}