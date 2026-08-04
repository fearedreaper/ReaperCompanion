package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.reapercompanion.design.ReaperColors

@Composable
fun KillerPortraitHeader(
    goal: String,
    modifier: Modifier = Modifier
) {
    val killerName = when (goal) {
        "GEN REGRESSION" -> "THE ARTIST"
        else -> "KILLER BUILD"
    }

    val portraitUrl = when (goal) {
        "GEN REGRESSION" ->
            "https://deadbydaylight.wiki.gg/wiki/" +
                    "Special:Redirect/file/K26%20TheArtist%20Portrait.png"

        else -> null
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = Color(0x99E24A4A)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF4A171C),
                            Color(0xFF1A0D10),
                            ReaperColors.CardBackground
                        )
                    )
                )
                .padding(14.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = killerName,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFF6B6B),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (portraitUrl != null) {
                    AsyncImage(
                        model = portraitUrl,
                        contentDescription = killerName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.7f)
                            .clip(RoundedCornerShape(18.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.7f)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color(0xFF120B0D)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "PORTRAIT COMING SOON",
                            color = ReaperColors.SecondaryText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}