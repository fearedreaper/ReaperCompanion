package com.example.reapercompanion.livecontent

object LiveContentRepository {

    fun getLiveContent(): LiveContent {
        return LiveContent(
            announcements = listOf(
                LiveAnnouncement(
                    id = "welcome",
                    title = "Welcome to Reaper Live",
                    message =
                        "Thanks for using Reaper Companion! Check back regularly for active codes, live events, and the latest Dead by Daylight updates.",
                    active = true
                )
            ),
            events = listOf(
                LiveEvent(
                    id = "launch",
                    title = "Launch Celebration",
                    description =
                        "Reaper Companion is officially live! New codes, events, and important updates will be added regularly.",
                    expires = "Limited Time",
                    active = true
                )
            ),
            codes = listOf(
                LiveCode(
                    id = "finderscreepers",
                    code = "FINDERSCREEPERS",
                    reward =
                        "Élodie in the Catacombs Banner and Élodie's Skull Badge",
                    rewardEs =
                        "Estandarte Élodie en las catacumbas e insignia Calavera de Élodie",
                    expires = "No expiration announced",
                    expiresEs = "Sin fecha de vencimiento anunciada",
                    active = true
                )
            )
        )
    }
}