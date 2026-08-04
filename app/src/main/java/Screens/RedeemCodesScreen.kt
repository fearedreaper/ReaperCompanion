package com.example.reapercompanion.screens

import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperBadge
import com.example.reapercompanion.design.ReaperCard
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperDivider
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperPrimaryButton
import com.example.reapercompanion.design.ReaperSecondaryButton

private data class RedeemCode(
    val code: String,
    val reward: String,
    val status: CodeStatus,
    val note: String = ""
)

private enum class CodeStatus {
    ACTIVE,
    EXPIRED
}

@Composable
fun RedeemCodesScreen(
    onBackClick: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    var showExpiredCodes by remember {
        mutableStateOf(false)
    }

    val activeCodes = remember {
        redeemCodes.filter {
            it.status == CodeStatus.ACTIVE
        }
    }

    val expiredCodes = remember {
        redeemCodes.filter {
            it.status == CodeStatus.EXPIRED
        }
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
                    title = "REDEEM CODES",
                    onBackClick = onBackClick
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "THE CAMPFIRE",
                    title = "Dead by Daylight Rewards",
                    body =
                        "Copy a code, open Dead by Daylight, visit the in-game Store, and choose Redeem Code.",
                    badge = "LOCAL LIST"
                )
            }

            item {
                RedeemSectionHeader(
                    title = "ACTIVE CODES",
                    count = activeCodes.size,
                    accentColor = Color(0xFF56D6A7)
                )
            }

            if (activeCodes.isEmpty()) {
                item {
                    ReaperCard(
                        accentColor = Color(0xFF56D6A7)
                    ) {
                        Text(
                            text = "No active codes are saved yet.",
                            color = ReaperColors.PrimaryText,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text =
                                "This first version uses local data. Online code updates will be connected next.",
                            color = ReaperColors.SecondaryText,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            items(
                items = activeCodes,
                key = {
                    it.code
                }
            ) { redeemCode ->
                RedeemCodeCard(
                    redeemCode = redeemCode,
                    accentColor = Color(0xFF56D6A7),
                    onCopyClick = {
                        clipboardManager.setText(
                            AnnotatedString(redeemCode.code)
                        )

                        Toast.makeText(
                            context,
                            "${redeemCode.code} copied",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }

            item {
                ReaperDivider()
            }

            item {
                ReaperCard(
                    accentColor = Color(0xFFFFC857),
                    clickable = true,
                    onClick = {
                        showExpiredCodes = !showExpiredCodes
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "EXPIRED CODES",
                                color = ReaperColors.PrimaryText,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text =
                                    "${expiredCodes.size} saved code${if (expiredCodes.size == 1) "" else "s"}",
                                color = ReaperColors.SecondaryText,
                                fontSize = 13.sp
                            )
                        }

                        ReaperBadge(
                            text = if (showExpiredCodes) {
                                "HIDE"
                            } else {
                                "SHOW"
                            },
                            accentColor = Color(0xFFFFC857)
                        )
                    }
                }
            }

            if (showExpiredCodes) {
                items(
                    items = expiredCodes,
                    key = {
                        it.code
                    }
                ) { redeemCode ->
                    RedeemCodeCard(
                        redeemCode = redeemCode,
                        accentColor = Color(0xFFFFC857),
                        onCopyClick = {
                            clipboardManager.setText(
                                AnnotatedString(redeemCode.code)
                            )

                            Toast.makeText(
                                context,
                                "${redeemCode.code} copied",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }

            item {
                ReaperCard {
                    Text(
                        text = "HOW TO REDEEM",
                        color = ReaperColors.CyanGlow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.7.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    RedemptionStep(
                        number = "1",
                        text = "Open Dead by Daylight."
                    )

                    RedemptionStep(
                        number = "2",
                        text = "Open the in-game Store."
                    )

                    RedemptionStep(
                        number = "3",
                        text = "Select Redeem Code."
                    )

                    RedemptionStep(
                        number = "4",
                        text = "Paste the copied code exactly as shown."
                    )
                }
            }

            item {
                ReaperSecondaryButton(
                    text = "BACK TO DEAD BY DAYLIGHT",
                    onClick = onBackClick
                )
            }

            item {
                Text(
                    text =
                        "Code availability can change without notice.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 2.dp,
                            bottom = 24.dp
                        ),
                    color = Color(0xFF526268),
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun RedeemSectionHeader(
    title: String,
    count: Int,
    accentColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = ReaperColors.PrimaryText,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        ReaperBadge(
            text = count.toString(),
            accentColor = accentColor
        )
    }
}

@Composable
private fun RedeemCodeCard(
    redeemCode: RedeemCode,
    accentColor: Color,
    onCopyClick: () -> Unit
) {
    ReaperCard(
        accentColor = accentColor
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ReaperBadge(
                text = if (redeemCode.status == CodeStatus.ACTIVE) {
                    "ACTIVE"
                } else {
                    "EXPIRED"
                },
                accentColor = accentColor
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = redeemCode.reward,
                color = ReaperColors.SecondaryText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = redeemCode.code,
            modifier = Modifier.fillMaxWidth(),
            color = ReaperColors.PrimaryText,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.4.sp,
            textAlign = TextAlign.Center
        )

        if (redeemCode.note.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = redeemCode.note,
                modifier = Modifier.fillMaxWidth(),
                color = ReaperColors.SecondaryText,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ReaperPrimaryButton(
            text = "COPY CODE",
            onClick = onCopyClick,
            accentColor = accentColor
        )
    }
}

@Composable
private fun RedemptionStep(
    number: String,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        ReaperBadge(
            text = number
        )

        Spacer(modifier = Modifier.padding(horizontal = 7.dp))

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            color = ReaperColors.PrimaryText,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

/*
    These are intentionally sample local entries for the first UI version.
    Replace them with verified current codes when the online JSON source is connected.
*/
private val redeemCodes = listOf(
    RedeemCode(
        code = "REAPER-DEMO",
        reward = "Sample Reward",
        status = CodeStatus.ACTIVE,
        note = "Placeholder code used to test the copy button and screen layout."
    ),
    RedeemCode(
        code = "FOG-ARCHIVE",
        reward = "Expired Sample",
        status = CodeStatus.EXPIRED,
        note = "Placeholder expired code for testing the collapsed history section."
    )
)