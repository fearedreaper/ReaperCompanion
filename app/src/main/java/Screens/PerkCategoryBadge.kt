package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.models.PerkCategory

@Composable
fun PerkCategoryBadge(
    category: PerkCategory
) {
    val badgeColor = when (category) {
        PerkCategory.CHASE -> Color(0xFFB26BFF)
        PerkCategory.GENERATOR -> Color(0xFFFFC857)
        PerkCategory.HEALING -> Color(0xFF42D98B)
        PerkCategory.SUPPORT -> Color(0xFF4AA3FF)
        PerkCategory.STEALTH -> Color(0xFF8E9AA3)
        PerkCategory.INFORMATION -> Color(0xFF00E5FF)
        PerkCategory.ENDGAME -> Color(0xFFFF6B6B)
        PerkCategory.SECOND_CHANCE -> Color(0xFFFF8FB3)
        PerkCategory.MEME -> Color(0xFFFF9D4D)
    }

    val label = category.name
        .replace("_", " ")

    Surface(
        shape = RoundedCornerShape(50),
        color = badgeColor.copy(alpha = 0.14f),
        border = BorderStroke(
            width = 1.dp,
            color = badgeColor.copy(alpha = 0.65f)
        )
    ) {
        Text(
            text = label,
            color = badgeColor,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = androidx.compose.ui.Modifier.padding(
                horizontal = 10.dp,
                vertical = 6.dp
            )
        )
    }
}