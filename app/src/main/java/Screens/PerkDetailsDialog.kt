package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.Perk

@Composable
fun PerkDetailsDialog(
    perk: Perk,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF080D10),
        shape = RoundedCornerShape(24.dp),
        title = {
            Column {
                OnlinePerkImage(
                    perk = perk,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.35f)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = perk.name,
                    color = ReaperColors.PrimaryText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = perk.owner,
                    color = ReaperColors.CyanGlow,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column {
                PerkCategoryBadge(
                    category = perk.category
                )

                Spacer(modifier = Modifier.height(14.dp))

                DetailCard(
                    label = "ROLE",
                    value = perk.role.name
                        .lowercase()
                        .replaceFirstChar { it.uppercase() }
                )

                Spacer(modifier = Modifier.height(12.dp))

                DetailCard(
                    label = "AVAILABILITY",
                    value = if (perk.isBaseGame) {
                        "Base Game"
                    } else {
                        "Character or DLC Perk"
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = ReaperColors.BorderActive
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = ReaperColors.CardBackground
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = "DESCRIPTION",
                            color = ReaperColors.CyanGlow,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = perk.description,
                            color = ReaperColors.PrimaryText,
                            fontSize = 15.sp,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ReaperColors.CyanDark,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "CLOSE",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Composable
private fun DetailCard(
    label: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderInactive
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = label,
                color = ReaperColors.SecondaryText,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                color = ReaperColors.PrimaryText,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}