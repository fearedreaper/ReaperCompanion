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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkRole

@Composable
fun BuildAroundPerkScreen(
    onBackClick: () -> Unit,
    onPerkClick: (Perk) -> Unit
) {
    var searchText by remember {
        mutableStateOf("")
    }

    var selectedRole by remember {
        mutableStateOf(PerkRole.SURVIVOR)
    }

    val allPerks = when (selectedRole) {
        PerkRole.SURVIVOR -> PerkDatabase.getSurvivorPerks()
        PerkRole.KILLER -> KillerPerkDatabase.allPerks
    }

    val filteredPerks = allPerks.filter { perk ->
        searchText.isBlank() ||
                perk.name.contains(
                    other = searchText,
                    ignoreCase = true
                ) ||
                perk.owner.contains(
                    other = searchText,
                    ignoreCase = true
                ) ||
                perk.category.name.contains(
                    other = searchText,
                    ignoreCase = true
                )
    }

    val accentColor = when (selectedRole) {
        PerkRole.SURVIVOR -> ReaperColors.CyanGlow
        PerkRole.KILLER -> Color(0xFFFF6B6B)
    }

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
                    text = "BUILD AROUND A PERK",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.PrimaryText,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = "Choose one perk and Reaper Companion will build the rest of the loadout around it.",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

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
                        text = "‹  BACK TO DEAD BY DAYLIGHT",
                        modifier = Modifier.padding(16.dp),
                        color = ReaperColors.CyanGlow,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    RoleButton(
                        text = "SURVIVOR",
                        selected = selectedRole == PerkRole.SURVIVOR,
                        selectedColor = ReaperColors.CyanGlow,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedRole = PerkRole.SURVIVOR
                            searchText = ""
                        }
                    )

                    RoleButton(
                        text = "KILLER",
                        selected = selectedRole == PerkRole.KILLER,
                        selectedColor = Color(0xFFFF6B6B),
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedRole = PerkRole.KILLER
                            searchText = ""
                        }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Search perk, owner, or category...",
                            color = ReaperColors.DisabledText
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(17.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = ReaperColors.PrimaryText,
                        unfocusedTextColor = ReaperColors.PrimaryText,
                        cursorColor = accentColor,
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = ReaperColors.BorderInactive,
                        focusedContainerColor = ReaperColors.CardBackground,
                        unfocusedContainerColor = ReaperColors.CardBackground
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${filteredPerks.size} PERKS",
                    color = accentColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
            }

            if (filteredPerks.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = ReaperColors.BorderInactive
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = ReaperColors.CardBackground
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "NO PERKS FOUND",
                                color = ReaperColors.PrimaryText,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(7.dp))

                            Text(
                                text = "Try a different search.",
                                color = ReaperColors.SecondaryText,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            } else {
                filteredPerks.forEach { perk ->
                    item {
                        BuildAroundPerkCard(
                            perk = perk,
                            accentColor = accentColor,
                            onClick = {
                                onPerkClick(perk)
                            }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun RoleButton(
    text: String,
    selected: Boolean,
    selectedColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) {
                selectedColor.copy(alpha = 0.25f)
            } else {
                ReaperColors.CardBackground
            },
            contentColor = if (selected) {
                selectedColor
            } else {
                ReaperColors.SecondaryText
            }
        ),
        border = BorderStroke(
            width = if (selected) 1.5.dp else 1.dp,
            color = if (selected) {
                selectedColor
            } else {
                ReaperColors.BorderInactive
            }
        )
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp
        )
    }
}

@Composable
private fun BuildAroundPerkCard(
    perk: Perk,
    accentColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pressScale()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.2.dp,
            color = accentColor.copy(alpha = 0.6f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OnlinePerkImage(
                perk = perk,
                modifier = Modifier
                    .width(92.dp)
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = perk.name,
                    color = ReaperColors.PrimaryText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = perk.owner,
                    color = ReaperColors.SecondaryText,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(9.dp))

                PerkCategoryBadge(
                    category = perk.category
                )

                Spacer(modifier = Modifier.height(9.dp))

                Text(
                    text = "TAP TO GENERATE BUILD",
                    color = accentColor,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}