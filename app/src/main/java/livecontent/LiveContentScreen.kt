package com.example.reapercompanion.livecontent

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.R
import com.example.reapercompanion.design.ReaperBadge
import com.example.reapercompanion.design.ReaperCard
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperDivider
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperSecondaryButton
import com.example.reapercompanion.screens.AppBackground

@Composable
fun LiveContentScreen(
    onBackClick: () -> Unit
) {
    var liveContent by remember {
        mutableStateOf(
            LiveContentRepository.getLiveContent()
        )
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    val isSpanish =
        stringResource(R.string.live_network) == "Red Reaper"

    LaunchedEffect(Unit) {
        liveContent = LiveContentService.fetchLiveContent()
        isLoading = false
    }

    val activeAnnouncements = remember(liveContent) {
        liveContent.announcements.filter { announcement ->
            announcement.active
        }
    }

    val activeEvents = remember(liveContent) {
        liveContent.events.filter { event ->
            event.active
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
                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                ReaperHeader(
                    title = stringResource(
                        R.string.live_content_title
                    ),
                    onBackClick = onBackClick
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = stringResource(
                        R.string.live_network
                    ),
                    title = if (isLoading) {
                        stringResource(
                            R.string.live_checking_updates
                        )
                    } else {
                        stringResource(
                            R.string.live_fresh_content
                        )
                    },
                    body = if (isLoading) {
                        stringResource(
                            R.string.live_contacting_source
                        )
                    } else {
                        stringResource(
                            R.string.live_content_body
                        )
                    },
                    badge = if (isLoading) {
                        stringResource(
                            R.string.live_syncing
                        )
                    } else {
                        stringResource(
                            R.string.live
                        )
                    }
                )
            }

            if (isLoading) {
                item {
                    LoadingCard()
                }
            } else {
                item {
                    LiveSectionHeader(
                        title = stringResource(
                            R.string.live_announcements
                        ),
                        count = activeAnnouncements.size,
                        accentColor = ReaperColors.CyanGlow
                    )
                }

                if (activeAnnouncements.isEmpty()) {
                    item {
                        EmptyLiveCard(
                            message = stringResource(
                                R.string.live_no_announcements
                            )
                        )
                    }
                } else {
                    items(
                        items = activeAnnouncements,
                        key = { announcement ->
                            announcement.id
                        }
                    ) { announcement ->
                        AnnouncementCard(
                            announcement = announcement,
                            isSpanish = isSpanish
                        )
                    }
                }

                item {
                    ReaperDivider()
                }

                item {
                    LiveSectionHeader(
                        title = stringResource(
                            R.string.live_events
                        ),
                        count = activeEvents.size,
                        accentColor = Color(0xFFFFC857)
                    )
                }

                if (activeEvents.isEmpty()) {
                    item {
                        EmptyLiveCard(
                            message = stringResource(
                                R.string.live_no_events
                            )
                        )
                    }
                } else {
                    items(
                        items = activeEvents,
                        key = { event ->
                            event.id
                        }
                    ) { event ->
                        EventCard(
                            event = event,
                            isSpanish = isSpanish
                        )
                    }
                }
            }

            item {
                ReaperSecondaryButton(
                    text = stringResource(
                        R.string.back
                    ),
                    onClick = onBackClick
                )
            }

            item {
                Text(
                    text = if (isLoading) {
                        stringResource(
                            R.string.live_connecting_footer
                        )
                    } else {
                        stringResource(
                            R.string.live_loaded_footer
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 4.dp,
                            bottom = 24.dp
                        ),
                    color = Color(0xFF526268),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    lineHeight = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun LoadingCard() {
    ReaperCard(
        accentColor = ReaperColors.CyanGlow
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                color = ReaperColors.CyanGlow
            )

            Spacer(
                modifier = Modifier.padding(
                    horizontal = 10.dp
                )
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(
                        R.string.live_syncing_content
                    ),
                    color = ReaperColors.PrimaryText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = stringResource(
                        R.string.live_fallback_body
                    ),
                    color = ReaperColors.SecondaryText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun LiveSectionHeader(
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
private fun AnnouncementCard(
    announcement: LiveAnnouncement,
    isSpanish: Boolean
) {
    val displayTitle =
        if (isSpanish && announcement.id == "welcome") {
            "Bienvenido a Reaper Live"
        } else {
            announcement.title
        }

    val displayMessage =
        if (isSpanish && announcement.id == "welcome") {
            "¡Gracias por usar Reaper Companion! Vuelve con frecuencia para ver códigos activos, eventos en vivo y las últimas novedades de Dead by Daylight."
        } else {
            announcement.message
        }

    ReaperCard(
        accentColor = ReaperColors.CyanGlow
    ) {
        Text(
            text = displayTitle,
            color = ReaperColors.PrimaryText,
            fontSize = 17.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = displayMessage,
            color = ReaperColors.SecondaryText,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun EventCard(
    event: LiveEvent,
    isSpanish: Boolean
) {
    val displayTitle =
        if (isSpanish && event.id == "launch") {
            "Celebración de lanzamiento"
        } else {
            event.title
        }

    val displayDescription =
        if (isSpanish && event.id == "launch") {
            "¡Reaper Companion ya está oficialmente disponible! Se añadirán regularmente nuevos códigos, eventos y novedades."
        } else {
            event.description
        }

    val displayExpires =
        if (
            isSpanish &&
            event.expires.equals(
                "Limited Time",
                ignoreCase = true
            )
        ) {
            "Tiempo limitado"
        } else {
            event.expires
        }

    ReaperCard(
        accentColor = Color(0xFFFFC857)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = displayTitle,
                    color = ReaperColors.PrimaryText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = displayDescription,
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            ReaperBadge(
                text = displayExpires,
                accentColor = Color(0xFFFFC857)
            )
        }
    }
}

@Composable
private fun EmptyLiveCard(
    message: String
) {
    ReaperCard {
        Text(
            text = message,
            modifier = Modifier.fillMaxWidth(),
            color = ReaperColors.SecondaryText,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}