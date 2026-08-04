package com.example.reapercompanion.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperColors

@Composable
fun ReaperScoreGauge(
    score: Int,
    modifier: Modifier = Modifier,
    size: Dp = 150.dp,
    accentColor: Color? = null
) {
    val safeScore = score.coerceIn(0, 100)

    val automaticColor = when (safeScore) {
        in 0..59 -> Color(0xFFFF5A5A)
        in 60..74 -> Color(0xFFFFA64D)
        in 75..89 -> Color(0xFF56D6A7)
        else -> ReaperColors.CyanGlow
    }

    val gaugeColor = accentColor ?: automaticColor

    val ratingLabel = when (safeScore) {
        in 0..59 -> "RISKY"
        in 60..74 -> "SOLID"
        in 75..89 -> "STRONG"
        in 90..94 -> "ELITE"
        else -> "REAPER APPROVED"
    }

    val animatedProgress by animateFloatAsState(
        targetValue = safeScore / 100f,
        animationSpec = tween(
            durationMillis = 1100
        ),
        label = "reaperScoreProgress"
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            val strokeWidth = 13.dp.toPx()
            val padding = strokeWidth / 2f

            val arcSize = Size(
                width = this.size.width - strokeWidth,
                height = this.size.height - strokeWidth
            )

            val topLeft = Offset(
                x = padding,
                y = padding
            )

            drawArc(
                color = Color(0xFF263238),
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )

            drawArc(
                color = gaugeColor.copy(alpha = 0.18f),
                startAngle = 135f,
                sweepAngle = 270f * animatedProgress,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidth + 9.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )

            drawArc(
                color = gaugeColor,
                startAngle = 135f,
                sweepAngle = 270f * animatedProgress,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = safeScore.toString(),
                color = ReaperColors.PrimaryText,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = "REAPER SCORE",
                color = ReaperColors.SecondaryText,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = ratingLabel,
                color = gaugeColor,
                fontSize = 9.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.8.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}