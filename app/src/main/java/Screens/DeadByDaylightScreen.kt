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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.R
import com.example.reapercompanion.design.ReaperColors

@Composable
fun DeadByDaylightScreen(
    onBackClick: () -> Unit,
    onSurvivorClick: () -> Unit,
    onKillerClick: () -> Unit,
    onItemCoachClick: () -> Unit,
    onMatchCoachClick: () -> Unit,
    onRandomBuildClick: () -> Unit,
    onMetaBuildsClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircleBackButton(
                    onClick = onBackClick
                )

                Text(
                    text = stringResource(R.string.home_dead_by_daylight),
                    modifier = Modifier.weight(1f),
                    color = ReaperColors.PrimaryText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(46.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            HeroPanel()

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = stringResource(R.string.dbd_choose_your_path),
                modifier = Modifier.fillMaxWidth(),
                color = ReaperColors.PrimaryText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            PremiumRoleCard(
                title = stringResource(R.string.dbd_survivor),
                subtitle = stringResource(R.string.dbd_survivor_subtitle),
                description =
                    stringResource(R.string.dbd_survivor_body),
                accent = ReaperColors.CyanGlow,
                badge = stringResource(R.string.dbd_build_wizard),
                iconRes = R.drawable.icon_survivor,
                onClick = onSurvivorClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            PremiumRoleCard(
                title = stringResource(R.string.dbd_killer),
                subtitle = stringResource(R.string.dbd_killer_subtitle),
                description =
                    stringResource(R.string.dbd_killer_body),
                accent = Color(0xFFE24A4A),
                badge = stringResource(R.string.dbd_build_wizard),
                iconRes = R.drawable.icon_killer,
                onClick = onKillerClick
            )

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = stringResource(R.string.dbd_quick_access),
                modifier = Modifier.fillMaxWidth(),
                color = ReaperColors.PrimaryText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            QuickAccessCard(
                title = stringResource(R.string.dbd_build_around_perk),
                subtitle = stringResource(R.string.dbd_build_around_perk_body),
                accent = Color(0xFFFFC857),
                label = stringResource(R.string.language_panel_eyebrow),
                iconRes = R.drawable.icon_build_around,
                onClick = onItemCoachClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            QuickAccessCard(
                title = stringResource(R.string.dbd_match_coach),
                subtitle = stringResource(R.string.dbd_match_coach_body),
                accent = Color(0xFF56D6A7),
                label = stringResource(R.string.dbd_match_plan),
                iconRes = R.drawable.icon_match_coach,
                onClick = onMatchCoachClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            QuickAccessCard(
                title = stringResource(R.string.dbd_random_build),
                subtitle = stringResource(R.string.dbd_random_build_body),
                accent = Color(0xFFB26BFF),
                label = stringResource(R.string.dbd_surprise_me),
                iconRes = R.drawable.icon_random,
                onClick = onRandomBuildClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            QuickAccessCard(
                title = stringResource(R.string.dbd_meta_builds),
                subtitle = stringResource(R.string.dbd_meta_builds_body),
                accent = Color(0xFFFFC857),
                label = stringResource(R.string.dbd_top_picks),
                iconRes = R.drawable.icon_meta,
                onClick = onMetaBuildsClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            QuickAccessCard(
                title = stringResource(R.string.dbd_favorites),
                subtitle = stringResource(R.string.dbd_favorites_body),
                accent = Color(0xFFFF6B9D),
                label = stringResource(R.string.dbd_saved),
                iconRes = R.drawable.icon_favorites,
                onClick = onFavoritesClick
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "REAPER COMPANION • DEAD BY DAYLIGHT",
                color = Color(0xFF526268),
                fontSize = 11.sp,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun HeroPanel() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
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
                            Color(0xFF0A3A43),
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
                        text = stringResource(R.string.app_name),
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
                    text = stringResource(R.string.dbd_hero_title),
                    color = ReaperColors.PrimaryText,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        stringResource(R.string.dbd_hero_body),
                    color = ReaperColors.SecondaryText,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
            }
        }
    }
}

@Composable
private fun PremiumRoleCard(
    title: String,
    subtitle: String,
    description: String,
    accent: Color,
    badge: String,
    iconRes: Int,
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
            color = accent.copy(alpha = 0.75f)
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
                            accent.copy(alpha = 0.18f),
                            ReaperColors.CardBackground
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = "$title icon",
                        modifier = Modifier.size(68.dp),
                        contentScale = ContentScale.Fit
                    )

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = accent.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = badge,
                            color = accent,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(
                                horizontal = 11.dp,
                                vertical = 6.dp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = title,
                    color = ReaperColors.PrimaryText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = subtitle,
                    color = accent,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = description,
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "${stringResource(R.string.open)}  ›",
                    modifier = Modifier.fillMaxWidth(),
                    color = accent,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
private fun QuickAccessCard(
    title: String,
    subtitle: String,
    accent: Color,
    label: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pressScale()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = accent.copy(alpha = 0.45f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "$title icon",
                modifier = Modifier.size(58.dp),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp)
            ) {
                Text(
                    text = title,
                    color = ReaperColors.PrimaryText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    color = ReaperColors.SecondaryText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(50),
                color = accent.copy(alpha = 0.12f)
            ) {
                Text(
                    text = label,
                    color = accent,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        horizontal = 9.dp,
                        vertical = 6.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun CircleBackButton(
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .pressScale()
            .clickable(onClick = onClick),
        shape = CircleShape,
        color = Color(0x1600E5FF),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0x5500E5FF)
        )
    ) {
        Text(
            text = "‹",
            color = ReaperColors.CyanGlow,
            fontSize = 30.sp,
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 6.dp
            )
        )
    }
}