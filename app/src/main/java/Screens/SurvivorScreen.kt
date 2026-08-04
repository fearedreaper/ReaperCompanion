package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperColors

data class SurvivorGoal(
    val title: String,
    val description: String
)

@Composable
fun SurvivorScreen(
    onBackClick: () -> Unit,
    onGoalClick: (String) -> Unit
) {
    val goals = listOf(
        SurvivorGoal(
            title = "LOOP BETTER",
            description = "Extend chases and create distance"
        ),
        SurvivorGoal(
            title = "RUSH GENERATORS",
            description = "Complete objectives as quickly as possible"
        ),
        SurvivorGoal(
            title = "SUPPORT THE TEAM",
            description = "Heal, rescue, protect and assist teammates"
        ),
        SurvivorGoal(
            title = "STEALTH",
            description = "Avoid detection and disappear from the Killer"
        ),
        SurvivorGoal(
            title = "TROLL THE KILLER",
            description = "Create funny moments and annoying mind games"
        ),
        SurvivorGoal(
            title = "SURVIVE LONGER",
            description = "Second chances and safer escapes"
        ),
        SurvivorGoal(
            title = "SURPRISE ME",
            description = "Let Reaper Companion choose your playstyle"
        )
    )

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "‹",
                    color = ReaperColors.CyanGlow,
                    fontSize = 38.sp,
                    modifier = Modifier
                        .clickable(onClick = onBackClick)
                        .padding(horizontal = 8.dp)
                )

                Text(
                    text = "SURVIVOR",
                    modifier = Modifier.weight(1f),
                    color = ReaperColors.PrimaryText,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier.width(46.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "What do you want to accomplish?",
                color = ReaperColors.SecondaryText,
                fontSize = 17.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                goals.forEach { goal ->
                    SurvivorGoalCard(
                        goal = goal,
                        onClick = {
                            onGoalClick(goal.title)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SurvivorGoalCard(
    goal: SurvivorGoal,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderActive
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardAvailable
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp,
                    vertical = 17.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(46.dp)
                    .background(
                        color = ReaperColors.CyanGlow,
                        shape = RoundedCornerShape(50)
                    )
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = goal.title,
                    color = ReaperColors.PrimaryText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = goal.description,
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 19.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "›",
                color = ReaperColors.CyanGlow,
                fontSize = 32.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}