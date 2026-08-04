package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.R
import com.example.reapercompanion.design.ReaperColors

@Composable
fun HomeScreen(
    onDeadByDaylightClick: () -> Unit,
    onReaperLiveClick: () -> Unit
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
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircleActionButton("☰")
                    CircleActionButton("⚙")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = painterResource(
                        id = R.drawable.reaper_companion_logo
                    ),
                    contentDescription = "Reaper Companion Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "DEAD BY DAYLIGHT COMPANION",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.CyanGlow,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Build smarter. Read the match. Escape—or dominate.",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 15.sp,
                    lineHeight = 21.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                WelcomePanel()
            }

            item {
                PrimaryLaunchCard(
                    onClick = onDeadByDaylightClick
                )
            }

            item {
                ReaperLiveCard(
                    onClick = onReaperLiveClick
                )
            }

            item {
                FeatureStrip()
            }

            item {
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "REAPER COMPANION • DEAD BY DAYLIGHT",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    color = Color(0xFF526268),
                    fontSize = 12.sp,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun WelcomePanel() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0x5500E5FF)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xCC10171C)
        )
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Text(
                text = "WELCOME BACK",
                color = ReaperColors.CyanGlow,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ready for the Fog?",
                color = ReaperColors.PrimaryText,
                fontSize = 27.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text =
                    "Generate Survivor and Killer builds, compare loadouts, save favorites, and prepare for specific Killers and maps with Reaper Match Coach.",
                color = ReaperColors.SecondaryText,
                fontSize = 15.sp,
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
private fun PrimaryLaunchCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pressScale()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(26.dp),
        border = BorderStroke(
            width = 1.6.dp,
            color = ReaperColors.CyanGlow
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
                            Color(0xFF10171C),
                            Color(0xFF071116)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0x2200E5FF)
                ) {
                    Text(
                        text = "ENTER THE FOG",
                        color = ReaperColors.CyanGlow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "DEAD BY DAYLIGHT",
                    color = ReaperColors.PrimaryText,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text =
                        "Survivor builds • Killer builds • Build Around • Match Coach • Favorites",
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "OPEN COMPANION",
                        color = ReaperColors.CyanGlow,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "›",
                        color = ReaperColors.CyanGlow,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureStrip() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FeatureBadge(
            label = "BUILD",
            modifier = Modifier.weight(1f)
        )

        FeatureBadge(
            label = "COMPARE",
            modifier = Modifier.weight(1f)
        )

        FeatureBadge(
            label = "COACH",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FeatureBadge(
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = ReaperColors.CardBackground,
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderInactive
        )
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 14.dp
            ),
            color = ReaperColors.CyanGlow,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CircleActionButton(
    text: String
) {
    Surface(
        modifier = Modifier.size(46.dp),
        shape = CircleShape,
        color = Color(0x1600E5FF),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0x5500E5FF)
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = ReaperColors.CyanGlow,
                fontSize = 20.sp
            )
        }
    }
}