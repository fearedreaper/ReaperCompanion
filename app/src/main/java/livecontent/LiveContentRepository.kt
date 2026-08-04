package com.example.reapercompanion.livecontent

object LiveContentRepository {

    fun getLiveContent(): LiveContent {
        return LiveContent(

            announcements = listOf(
                LiveAnnouncement(
                    id = "welcome",
                    title = "Welcome to Reaper Companion",
                    message = "Thanks for downloading Reaper Companion. Watch this space for updates, events, and featured builds.",
                    active = true
                )
            ),

            events = listOf(
                LiveEvent(
                    id = "launch",
                    title = "Launch Celebration",
                    description = "Reaper Companion is now live! Check back often for new guides, events, and content.",
                    expires = "Limited Time",
                    active = true
                )
            ),

            featuredBuilds = listOf(
                FeaturedBuild(
                    id = "starter",
                    title = "Stealth Survivor",
                    description = "Lightweight • Windows of Opportunity • Kindred • Adrenaline"
                )
            )
        )
    }
}