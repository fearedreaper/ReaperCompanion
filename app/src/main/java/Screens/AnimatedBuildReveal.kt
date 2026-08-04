package com.example.reapercompanion.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.Perk
import kotlinx.coroutines.delay

@Composable
fun AnimatedBuildReveal(
    perks: List<Perk>,
    score: Int,
    accentColor: Color = ReaperColors.CyanGlow,
    modifier: Modifier = Modifier,
    onFinished: () -> Unit
) {
    val revealPerks = perks.take(4)

    var visible by remember {
        mutableStateOf(true)
    }

    var revealCount by remember {
        mutableIntStateOf(0)
    }

    var statusText by remember {
        mutableStateOf("REAPER ANALYZING...")
    }

    var buildComplete by remember {
        mutableStateOf(false)
    }

    var targetProgress by remember {
        mutableStateOf(0f)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(
            durationMillis = 450
        ),
        label = "buildRevealProgress"
    )

    LaunchedEffect(revealPerks, score) {
        visible = true
        revealCount = 0
        buildComplete = false
        targetProgress = 0.08f
        statusText = "REAPER ANALYZING..."

        delay(550)

        revealPerks.forEachIndexed { index, _ ->
            statusText = when (index) {
                0 -> "CORE PERK FOUND"
                1 -> "PRIMARY SYNERGY FOUND"
                2 -> "SUPPORT SYNERGY FOUND"
                else -> "FINAL PERK FOUND"
            }

            revealCount = index + 1

            targetProgress =
                ((index + 1).toFloat() / revealPerks.size.toFloat())
                    .coerceIn(0f, 1f)

            delay(520)
        }

        statusText = "CALCULATING REAPER SCORE..."
        delay(650)

        buildComplete = true
        statusText = "BUILD COMPLETE"
        targetProgress = 1f

        delay(850)

        visible = false
        delay(300)

        onFinished()
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(250)
        ),
        exit = fadeOut(
            animationSpec = tween(300)
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF020608),
                            Color(0xFF071116),
                            Color(0xFF020405)
                        )
                    )
                )
                .padding(horizontal = 22.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "☠",
                    color = accentColor,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = statusText,
                    modifier = Modifier.fillMaxWidth(),
                    color = if (buildComplete) {
                        Color(0xFF56D6A7)
                    } else {
                        accentColor
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                LinearProgressIndicator(
                    progress = {
                        animatedProgress
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp)
                        .clip(RoundedCornerShape(50)),
                    color = accentColor,
                    trackColor = Color(0xFF202A2E)
                )

                Spacer(modifier = Modifier.height(26.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    revealPerks.forEachIndexed { index, perk ->
                        AnimatedVisibility(
                            visible = revealCount > index,
                            enter = fadeIn(
                                animationSpec = tween(300)
                            ) + slideInVertically(
                                animationSpec = tween(350),
                                initialOffsetY = {
                                    it / 2
                                }
                            )
                        ) {
                            RevealPerkCard(
                                perk = perk,
                                label = revealLabel(index),
                                accentColor = accentColor
                            )
                        }
                    }
                }

                if (buildComplete) {
                    Spacer(modifier = Modifier.height(22.dp))

                    ReaperScoreGauge(
                        score = score,
                        size = 130.dp,
                        accentColor = accentColor
                    )
                }
            }
        }
    }
}

@Composable
private fun RevealPerkCard(
    perk: Perk,
    label: String,
    accentColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.3.dp,
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
                            ReaperColors.CardBackground
                        )
                    )
                )
                .padding(13.dp)
        ) {
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OnlinePerkImage(
                    perk = perk,
                    modifier = Modifier
                        .width(70.dp)
                        .aspectRatio(1f)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 14.dp)
                ) {
                    Text(
                        text = label,
                        color = accentColor,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = perk.name,
                        color = ReaperColors.PrimaryText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = perk.owner,
                        color = ReaperColors.SecondaryText,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

private fun revealLabel(
    index: Int
): String {
    return when (index) {
        0 -> "CORE PERK"
        1 -> "PRIMARY SYNERGY"
        2 -> "SUPPORT SYNERGY"
        else -> "FINAL PERK"
    }
}