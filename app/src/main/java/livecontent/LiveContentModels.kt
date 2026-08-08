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

data class LiveCode(
    val id: String,
    val code: String,
    val reward: String,
    val rewardEs: String = "",
    val expires: String,
    val expiresEs: String = "",
    val active: Boolean = true
)

data class LiveContent(
    val announcements: List<LiveAnnouncement> = emptyList(),
    val events: List<LiveEvent> = emptyList(),
    val codes: List<LiveCode> = emptyList()
)