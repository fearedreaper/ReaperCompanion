package com.example.reapercompanion.design

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReaperHeader(
    title: String,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    accentColor: Color = ReaperColors.CyanGlow,
    trailingContent: (@Composable BoxScope.() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBackClick != null) {
            ReaperBackButton(
                onClick = onBackClick,
                accentColor = accentColor
            )
        } else {
            Spacer(modifier = Modifier.size(46.dp))
        }

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = ReaperColors.PrimaryText,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.5.sp,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier.size(46.dp),
            contentAlignment = Alignment.Center
        ) {
            trailingContent?.invoke(this)
        }
    }
}

@Composable
fun ReaperBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = ReaperColors.CyanGlow
) {
    Surface(
        modifier = modifier
            .size(46.dp)
            .clickable(onClick = onClick),
        shape = CircleShape,
        color = accentColor.copy(alpha = 0.08f),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.42f)
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "‹",
                color = accentColor,
                fontSize = 31.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
fun ReaperSectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    accentColor: Color = ReaperColors.CyanGlow
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            color = ReaperColors.PrimaryText,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        if (!subtitle.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                color = ReaperColors.SecondaryText,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .width(48.dp)
                .height(2.dp)
                .background(
                    color = accentColor,
                    shape = RoundedCornerShape(50)
                )
        )
    }
}

@Composable
fun ReaperCard(
    modifier: Modifier = Modifier,
    accentColor: Color = ReaperColors.CyanGlow,
    clickable: Boolean = false,
    onClick: () -> Unit = {},
    contentPadding: Dp = 20.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardModifier = if (clickable) {
        modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    } else {
        modifier.fillMaxWidth()
    }

    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.5f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            content = content
        )
    }
}

@Composable
fun ReaperGradientCard(
    modifier: Modifier = Modifier,
    accentColor: Color = ReaperColors.CyanGlow,
    clickable: Boolean = false,
    onClick: () -> Unit = {},
    contentPadding: Dp = 22.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardModifier = if (clickable) {
        modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    } else {
        modifier.fillMaxWidth()
    }

    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.4.dp,
            color = accentColor.copy(alpha = 0.72f)
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
                            accentColor.copy(alpha = 0.18f),
                            ReaperColors.CardBackground,
                            Color(0xFF071116)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(contentPadding),
                content = content
            )
        }
    }
}

@Composable
fun ReaperPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = ReaperColors.CyanGlow,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp),
        enabled = enabled,
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = accentColor,
            contentColor = Color(0xFF001014),
            disabledContainerColor = accentColor.copy(alpha = 0.25f),
            disabledContentColor = ReaperColors.SecondaryText
        )
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun ReaperSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = ReaperColors.CyanGlow,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) {
                accentColor.copy(alpha = 0.7f)
            } else {
                ReaperColors.BorderInactive
            }
        )
    ) {
        Text(
            text = text,
            color = if (enabled) {
                accentColor
            } else {
                ReaperColors.SecondaryText
            },
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
    }
}

@Composable
fun ReaperBadge(
    text: String,
    modifier: Modifier = Modifier,
    accentColor: Color = ReaperColors.CyanGlow
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = accentColor.copy(alpha = 0.12f),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.32f)
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 11.dp,
                vertical = 6.dp
            ),
            color = accentColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun ReaperAccentBar(
    modifier: Modifier = Modifier,
    accentColor: Color = ReaperColors.CyanGlow,
    height: Dp = 46.dp,
    width: Dp = 4.dp
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .background(
                color = accentColor,
                shape = RoundedCornerShape(50)
            )
    )
}

@Composable
fun ReaperListCard(
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = ReaperColors.CyanGlow,
    trailingText: String = "›"
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.55f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardAvailable
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp,
                    vertical = 17.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ReaperAccentBar(
                accentColor = accentColor
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = ReaperColors.PrimaryText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.4.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = description,
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 19.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = trailingText,
                color = accentColor,
                fontSize = 31.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
fun ReaperStatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    accentColor: Color = ReaperColors.CyanGlow
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.4f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                color = ReaperColors.SecondaryText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                color = accentColor,
                fontSize = 23.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ReaperInfoPanel(
    eyebrow: String,
    title: String,
    body: String,
    modifier: Modifier = Modifier,
    accentColor: Color = ReaperColors.CyanGlow,
    badge: String? = null
) {
    ReaperGradientCard(
        modifier = modifier,
        accentColor = accentColor
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = eyebrow,
                modifier = Modifier.weight(1f),
                color = accentColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.7.sp
            )

            if (!badge.isNullOrBlank()) {
                ReaperBadge(
                    text = badge,
                    accentColor = accentColor
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = title,
            color = ReaperColors.PrimaryText,
            fontSize = 25.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = body,
            color = ReaperColors.SecondaryText,
            fontSize = 15.sp,
            lineHeight = 22.sp
        )
    }
}

@Composable
fun ReaperDivider(
    modifier: Modifier = Modifier,
    accentColor: Color = ReaperColors.CyanGlow
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        accentColor.copy(alpha = 0.55f),
                        Color.Transparent
                    )
                )
            )
    )
}