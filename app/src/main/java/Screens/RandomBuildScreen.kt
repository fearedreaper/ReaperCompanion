package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.database.KillerPerkDatabase
import com.example.reapercompanion.database.PerkDatabase
import com.example.reapercompanion.design.ReaperBadge
import com.example.reapercompanion.design.ReaperCard
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperPrimaryButton
import com.example.reapercompanion.design.ReaperSecondaryButton
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkRole

@Composable
fun RandomBuildScreen(
    onBackClick: () -> Unit
) {
    var selectedRole by remember {
        mutableStateOf(PerkRole.SURVIVOR)
    }

    var rerollKey by remember {
        mutableIntStateOf(0)
    }

    var openedPerk by remember {
        mutableStateOf<Perk?>(null)
    }

    val availablePerks = remember(selectedRole) {
        when (selectedRole) {
            PerkRole.SURVIVOR ->
                PerkDatabase.getSurvivorPerks()

            PerkRole.KILLER ->
                KillerPerkDatabase.allPerks
        }
    }

    val randomBuild = remember(
        selectedRole,
        rerollKey,
        availablePerks
    ) {
        availablePerks
            .shuffled()
            .distinctBy { perk ->
                perk.id
            }
            .take(4)
    }

    openedPerk?.let { perk ->
        PerkDetailsDialog(
            perk = perk,
            onDismiss = {
                openedPerk = null
            }
        )
    }

    val accentColor = when (selectedRole) {
        PerkRole.SURVIVOR ->
            ReaperColors.CyanGlow

        PerkRole.KILLER ->
            Color(0xFFFF5A5A)
    }

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
                    title = "RANDOM BUILD",
                    onBackClick = onBackClick,
                    accentColor = accentColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Let the Entity choose your loadout.",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }

            item {
                RandomRoleSelector(
                    selectedRole = selectedRole,
                    onRoleClick = { role ->
                        selectedRole = role
                        rerollKey++
                    }
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "THE ENTITY DECIDES",
                    title = when (selectedRole) {
                        PerkRole.SURVIVOR ->
                            "Random Survivor Build"

                        PerkRole.KILLER ->
                            "Random Killer Build"
                    },
                    body =
                        "Four perks have been selected from your current database.",
                    accentColor = accentColor,
                    badge = if (selectedRole == PerkRole.SURVIVOR) {
                        "SURVIVOR"
                    } else {
                        "KILLER"
                    }
                )
            }

            item {
                RandomPerkGrid(
                    perks = randomBuild,
                    accentColor = accentColor,
                    onPerkClick = { perk ->
                        openedPerk = perk
                    }
                )
            }

            item {
                ReaperCard(
                    accentColor = accentColor
                ) {
                    Text(
                        text = "REAPER VERDICT",
                        color = accentColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.7.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = when (selectedRole) {
                            PerkRole.SURVIVOR ->
                                "The Entity has chosen your path. Adapt, survive, and make every perk matter."

                            PerkRole.KILLER ->
                                "The Entity demands pressure. Use the build you were given and make every chase count."
                        },
                        color = ReaperColors.PrimaryText,
                        fontSize = 15.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            item {
                ReaperPrimaryButton(
                    text = "REROLL BUILD",
                    onClick = {
                        rerollKey++
                    },
                    accentColor = accentColor
                )
            }

            item {
                ReaperSecondaryButton(
                    text = "BACK TO DEAD BY DAYLIGHT",
                    onClick = onBackClick,
                    accentColor = accentColor
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun RandomRoleSelector(
    selectedRole: PerkRole,
    onRoleClick: (PerkRole) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RandomRoleButton(
            title = "SURVIVOR",
            selected = selectedRole == PerkRole.SURVIVOR,
            accentColor = ReaperColors.CyanGlow,
            onClick = {
                onRoleClick(PerkRole.SURVIVOR)
            },
            modifier = Modifier.weight(1f)
        )

        RandomRoleButton(
            title = "KILLER",
            selected = selectedRole == PerkRole.KILLER,
            accentColor = Color(0xFFFF5A5A),
            onClick = {
                onRoleClick(PerkRole.KILLER)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun RandomRoleButton(
    title: String,
    selected: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .pressScale()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(
            width = if (selected) {
                1.6.dp
            } else {
                1.dp
            },
            color = if (selected) {
                accentColor
            } else {
                ReaperColors.BorderInactive
            }
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                accentColor.copy(alpha = 0.14f)
            } else {
                ReaperColors.CardBackground
            }
        )
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            color = if (selected) {
                accentColor
            } else {
                ReaperColors.SecondaryText
            },
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RandomPerkGrid(
    perks: List<Perk>,
    accentColor: Color,
    onPerkClick: (Perk) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        perks.chunked(2).forEach { rowPerks ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowPerks.forEach { perk ->
                    RandomPerkCard(
                        perk = perk,
                        accentColor = accentColor,
                        onClick = {
                            onPerkClick(perk)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (rowPerks.size == 1) {
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun RandomPerkCard(
    perk: Perk,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ReaperCard(
        modifier = modifier
            .pressScale(),
        accentColor = accentColor,
        clickable = true,
        onClick = onClick,
        contentPadding = 14.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OnlinePerkImage(
                perk = perk,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = perk.name,
                modifier = Modifier.fillMaxWidth(),
                color = ReaperColors.PrimaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            ReaperBadge(
                text = perk.category.name.replace("_", " "),
                accentColor = accentColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "TAP FOR DETAILS",
                modifier = Modifier.fillMaxWidth(),
                color = accentColor,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.9.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}