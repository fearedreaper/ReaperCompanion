package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.FavoriteBuild
import com.example.reapercompanion.storage.FavoritesStorage

@Composable
fun FavoritesScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    var favorites by remember {
        mutableStateOf(
            FavoritesStorage.loadFavorites(context)
        )
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
                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.clickable(
                            onClick = onBackClick
                        ),
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

                    Text(
                        text = "FAVORITES",
                        modifier = Modifier.weight(1f),
                        color = ReaperColors.PrimaryText,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 23.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Your saved Reaper builds",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (favorites.isEmpty()) {
                item {
                    EmptyFavoritesCard()
                }
            } else {
                items(favorites) { build ->
                    FavoriteBuildCard(
                        build = build,
                        onDeleteClick = {
                            FavoritesStorage.deleteFavorite(
                                context = context,
                                build = build
                            )

                            favorites =
                                FavoritesStorage.loadFavorites(context)
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
private fun EmptyFavoritesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderInactive
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "☆",
                color = ReaperColors.CyanGlow,
                fontSize = 52.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "NO SAVED BUILDS",
                color = ReaperColors.PrimaryText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Builds you save will appear here.",
                color = ReaperColors.SecondaryText,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun FavoriteBuildCard(
    build: FavoriteBuild,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = ReaperColors.BorderActive
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardAvailable
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = build.name,
                color = ReaperColors.PrimaryText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = build.goal,
                color = ReaperColors.CyanGlow,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "REAPER SCORE",
                        color = ReaperColors.SecondaryText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${build.score} / 100",
                        color = ReaperColors.CyanGlow,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "DIFFICULTY",
                        color = ReaperColors.SecondaryText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = build.difficulty,
                        color = ReaperColors.PrimaryText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            build.perks.forEach { perk ->
                Text(
                    text = "•  $perk",
                    color = ReaperColors.PrimaryText,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 3.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onDeleteClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A1F26),
                    contentColor = Color(0xFFFF9AA8)
                )
            ) {
                Text(
                    text = "DELETE BUILD",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}