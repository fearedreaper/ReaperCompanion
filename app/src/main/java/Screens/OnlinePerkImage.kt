package com.example.reapercompanion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkRole

@Composable
fun OnlinePerkImage(
    perk: Perk,
    modifier: Modifier = Modifier
) {
    val initials = remember(perk.name) {
        perk.name
            .split(" ")
            .filter { word ->
                word.isNotBlank()
            }
            .take(2)
            .joinToString("") { word ->
                word.firstOrNull()?.uppercase() ?: ""
            }
    }

    val imageUrls = remember(
        perk.id,
        perk.imageUrl
    ) {
        perkImageUrls(perk)
    }

    var imageIndex by remember(
        perk.id,
        imageUrls
    ) {
        mutableIntStateOf(0)
    }

    var allImagesFailed by remember(
        perk.id,
        imageUrls
    ) {
        mutableStateOf(false)
    }

    val currentImageUrl =
        imageUrls.getOrNull(imageIndex)

    val outerShape = RoundedCornerShape(18.dp)
    val innerShape = RoundedCornerShape(14.dp)

    val glowColor = when (perk.role) {
        PerkRole.KILLER ->
            Color(0xFFFF5A5A)

        PerkRole.SURVIVOR ->
            ReaperColors.CyanGlow
    }

    Box(
        modifier = modifier
            .clip(outerShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        glowColor.copy(alpha = 0.48f),
                        Color(0xFF10252C),
                        Color(0xFF060A0D)
                    )
                )
            )
            .border(
                width = 1.5.dp,
                color = glowColor.copy(alpha = 0.62f),
                shape = outerShape
            )
            .padding(7.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(innerShape)
                .background(Color(0xFF080D10)),
            contentAlignment = Alignment.Center
        ) {
            if (
                currentImageUrl.isNullOrBlank() ||
                allImagesFailed
            ) {
                Text(
                    text = initials,
                    color = glowColor,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Black
                )
            } else {
                AsyncImage(
                    model = currentImageUrl,
                    contentDescription = perk.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(3.dp),
                    contentScale = ContentScale.Fit,
                    onSuccess = {
                        allImagesFailed = false
                    },
                    onError = {
                        if (imageIndex < imageUrls.lastIndex) {
                            imageIndex++
                        } else {
                            allImagesFailed = true
                        }
                    }
                )
            }
        }
    }
}

private fun perkImageUrls(
    perk: Perk
): List<String> {
    val fileName = perkIconFileName(perk.id)

    val wikiUrl =
        "https://deadbydaylight.wiki.gg/wiki/" +
                "Special:Redirect/file/$fileName"

    val wikiThumbnailUrl =
        "$wikiUrl?width=512"

    val fandomUrl =
        "https://deadbydaylight.fandom.com/wiki/" +
                "Special:Redirect/file/$fileName"

    val urls = mutableListOf<String>()

    if (!perk.imageUrl.isNullOrBlank()) {
        urls.add(perk.imageUrl)
    }

    urls.add(wikiUrl)
    urls.add(wikiThumbnailUrl)
    urls.add(fandomUrl)

    return urls.distinct()
}

private fun perkIconFileName(
    perkId: String
): String {
    if (perkId == "five_moves_ahead") {
        return "IconsPerks%20FiveMovesAhead.png"
    }

    val iconName = perkId
        .split("_")
        .filter { part ->
            part.isNotBlank()
        }
        .mapIndexed { index, part ->
            if (index == 0) {
                part.lowercase()
            } else {
                part.lowercase()
                    .replaceFirstChar { character ->
                        character.uppercase()
                    }
            }
        }
        .joinToString("")

    return "IconPerks_${iconName}.png"
}