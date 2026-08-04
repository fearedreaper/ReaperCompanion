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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.BuildStyle
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkRole

private data class BuildStyleOption(
    val style: BuildStyle,
    val title: String,
    val subtitle: String,
    val accentColor: Color
)

@Composable
fun BuildStylePickerScreen(
    selectedPerk: Perk,
    onBackClick: () -> Unit,
    onStyleClick: (BuildStyle) -> Unit
) {
    val roleAccent = when (selectedPerk.role) {
        PerkRole.SURVIVOR -> ReaperColors.CyanGlow
        PerkRole.KILLER -> Color(0xFFFF6B6B)
    }

    val options = listOf(
        BuildStyleOption(
            style = BuildStyle.BALANCED,
            title = "BALANCED",
            subtitle = "A reliable mix of information, utility, and pressure",
            accentColor = roleAccent
        ),
        BuildStyleOption(
            style = BuildStyle.AGGRESSIVE,
            title = "AGGRESSIVE",
            subtitle = "Prioritize momentum, chase pressure, and active play",
            accentColor = Color(0xFFFF784F)
        ),
        BuildStyleOption(
            style = BuildStyle.SAFE,
            title = "SAFE",
            subtitle = "Favor consistency, protection, and lower-risk value",
            accentColor = Color(0xFF56D6A7)
        ),
        BuildStyleOption(
            style = BuildStyle.BEGINNER,
            title = "BEGINNER",
            subtitle = "Use simple, accessible perks with dependable effects",
            accentColor = Color(0xFFFFC857)
        ),
        BuildStyleOption(
            style = BuildStyle.MEME,
            title = "MEME",
            subtitle = "Create strange, chaotic, and entertaining combinations",
            accentColor = Color(0xFFB26BFF)
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
                    text = "CHOOSE A BUILD STYLE",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.PrimaryText,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = selectedPerk.name,
                    modifier = Modifier.fillMaxWidth(),
                    color = roleAccent,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = "Choose how Reaper Companion should shape the rest of the loadout.",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pressScale()
                        .clickable(onClick = onBackClick),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = roleAccent.copy(alpha = 0.65f)
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = ReaperColors.CardBackground
                    )
                ) {
                    Text(
                        text = "‹  CHOOSE A DIFFERENT PERK",
                        modifier = Modifier.padding(16.dp),
                        color = roleAccent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            options.forEach { option ->
                item {
                    BuildStyleCard(
                        option = option,
                        onClick = {
                            onStyleClick(option.style)
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
private fun BuildStyleCard(
    option: BuildStyleOption,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pressScale()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.4.dp,
            color = option.accentColor.copy(alpha = 0.7f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = option.accentColor.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Text(
                text = option.title,
                color = option.accentColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.3.sp
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = option.subtitle,
                color = ReaperColors.SecondaryText,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "GENERATE  ›",
                modifier = Modifier.fillMaxWidth(),
                color = option.accentColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )
        }
    }
}