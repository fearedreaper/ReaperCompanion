package com.example.reapercompanion.livecontent

data class LiveAnnouncement(
    val id: String,
    val title: String,
    val message: String,
    val active: Boolean = true
)

data class LiveEvent(
    val id: String,
    val title: String,
    val description: String,
    val expires: String,
    val active: Boolean = true
)

data class LiveContent(
    val announcements: List<LiveAnnouncement> = emptyList(),
    val events: List<LiveEvent> = emptyList()
)