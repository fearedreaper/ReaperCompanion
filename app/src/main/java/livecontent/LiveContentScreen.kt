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

    LaunchedEffect(Unit) {
        liveContent = LiveContentService.fetchLiveContent()
        isLoading = false
    }

    val activeAnnouncements = remember(liveContent) {
        liveContent.announcements.filter { it.active }
    }

    val activeEvents = remember(liveContent) {
        liveContent.events.filter { it.active }
    }

    val featuredBuilds = remember(liveContent) {
        liveContent.featuredBuilds
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
                    title = "REAPER LIVE",
                    onBackClick = onBackClick
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "REAPER NETWORK",
                    title = if (isLoading) {
                        "Checking for Updates"
                    } else {
                        "Fresh Content Without App Updates"
                    },
                    body = if (isLoading) {
                        "Reaper is contacting the live content source."
                    } else {
                        "Announcements, live events, and featured builds update remotely without a new Play Store release."
                    },
                    badge = if (isLoading) {
                        "SYNCING"
                    } else {
                        "LIVE"
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
                        title = "ANNOUNCEMENTS",
                        count = activeAnnouncements.size,
                        accentColor = ReaperColors.CyanGlow
                    )
                }

                if (activeAnnouncements.isEmpty()) {
                    item {
                        EmptyLiveCard(
                            message = "No active announcements."
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
                            announcement = announcement
                        )
                    }
                }

                item {
                    ReaperDivider()
                }

                item {
                    LiveSectionHeader(
                        title = "LIVE EVENTS",
                        count = activeEvents.size,
                        accentColor = Color(0xFFFFC857)
                    )
                }

                if (activeEvents.isEmpty()) {
                    item {
                        EmptyLiveCard(
                            message = "No active events."
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
                            event = event
                        )
                    }
                }

                item {
                    ReaperDivider()
                }

                item {
                    LiveSectionHeader(
                        title = "FEATURED BUILDS",
                        count = featuredBuilds.size,
                        accentColor = Color(0xFF56D6A7)
                    )
                }

                if (featuredBuilds.isEmpty()) {
                    item {
                        EmptyLiveCard(
                            message = "No featured builds."
                        )
                    }
                } else {
                    items(
                        items = featuredBuilds,
                        key = { build ->
                            build.id
                        }
                    ) { build ->
                        FeaturedBuildCard(
                            build = build
                        )
                    }
                }
            }

            item {
                ReaperSecondaryButton(
                    text = "BACK",
                    onClick = onBackClick
                )
            }

            item {
                Text(
                    text = if (isLoading) {
                        "CONNECTING TO REAPER NETWORK..."
                    } else {
                        "LIVE CONTENT LOADED FROM THE SHARED REMOTE SOURCE."
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

            Spacer(modifier = Modifier.padding(horizontal = 10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "SYNCING LIVE CONTENT",
                    color = ReaperColors.PrimaryText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text =
                        "If the remote source is unavailable, Reaper will safely use the local fallback content.",
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
    announcement: LiveAnnouncement
) {
    ReaperCard(
        accentColor = ReaperColors.CyanGlow
    ) {
        Text(
            text = announcement.title,
            color = ReaperColors.PrimaryText,
            fontSize = 17.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = announcement.message,
            color = ReaperColors.SecondaryText,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun EventCard(
    event: LiveEvent
) {
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
                    text = event.title,
                    color = ReaperColors.PrimaryText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = event.description,
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            ReaperBadge(
                text = event.expires,
                accentColor = Color(0xFFFFC857)
            )
        }
    }
}

@Composable
private fun FeaturedBuildCard(
    build: FeaturedBuild
) {
    ReaperCard(
        accentColor = Color(0xFF56D6A7)
    ) {
        Text(
            text = build.title,
            color = ReaperColors.PrimaryText,
            fontSize = 17.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = build.description,
            color = ReaperColors.SecondaryText,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
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