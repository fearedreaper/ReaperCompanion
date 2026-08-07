package com.example.reapercompanion.localization

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.R
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperListCard
import com.example.reapercompanion.screens.AppBackground

@Composable
fun LanguageSelectionScreen(
    onLanguageSelected: (LanguageOption) -> Unit
) {
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
                Spacer(modifier = Modifier.height(16.dp))

                ReaperHeader(
                    title = stringResource(R.string.language_welcome),
                    onBackClick = null
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.language_choose_title),
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.PrimaryText,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.language_choose_body),
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow =
                        stringResource(
                            R.string.language_panel_eyebrow
                        ),
                    title =
                        stringResource(
                            R.string.language_panel_title
                        ),
                    body =
                        stringResource(
                            R.string.language_panel_body
                        ),
                    accentColor = Color(0xFFFFC857),
                    badge =
                        stringResource(
                            R.string.language_count,
                            SupportedLanguages.all.size
                        )
                )
            }

            items(
                items = SupportedLanguages.all,
                key = { language ->
                    language.code
                }
            ) { language ->
                ReaperListCard(
                    title = language.nativeName,
                    description = language.displayName,
                    onClick = {
                        onLanguageSelected(language)
                    },
                    accentColor = ReaperColors.CyanGlow,
                    trailingText = "›"
                )
            }

            item {
                Text(
                    text =
                        stringResource(
                            R.string.language_choose_to_continue
                        ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 4.dp,
                            bottom = 24.dp
                        ),
                    color = ReaperColors.SecondaryText,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}