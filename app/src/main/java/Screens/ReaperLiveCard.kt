package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.R
import com.example.reapercompanion.design.ReaperColors

@Composable
fun ReaperLiveCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pressScale()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.4.dp,
            color = Color(0xFFFFC857).copy(alpha = 0.75f)
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
                            Color(0x332A1A00),
                            ReaperColors.CardBackground,
                            Color(0xFF071116)
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.reaper_live_title
                            ),
                            color = ReaperColors.PrimaryText,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = stringResource(
                                R.string.reaper_live_categories
                            ),
                            color = Color(0xFFFFC857),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Color(0x22FFC857)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.reaper_live_badge
                            ),
                            color = Color(0xFFFFC857),
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

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = stringResource(
                        R.string.reaper_live_description
                    ),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = stringResource(
                        R.string.reaper_live_open
                    ),
                    color = Color(0xFFFFC857),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}