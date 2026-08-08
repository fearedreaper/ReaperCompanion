package com.example.reapercompanion.livecontent

object LiveContentRepository {

    fun getLiveContent(): LiveContent {
        return LiveContent(
            announcements = listOf(
                LiveAnnouncement(
                    id = "welcome",
                    title = "Welcome to Reaper Live",
                    titleEs = "Bienvenido a Reaper Live",
                    message =
                        "Thanks for using Reaper Companion! Check back regularly for active codes, live events, and the latest Dead by Daylight updates.",
                    messageEs =
                        "¡Gracias por usar Reaper Companion! Vuelve con frecuencia para ver códigos activos, eventos en vivo y las últimas novedades de Dead by Daylight.",
                    active = true
                )
            ),
            events = listOf(
                LiveEvent(
                    id = "launch",
                    title = "Launch Celebration",
                    titleEs = "Celebración de lanzamiento",
                    description =
                        "Reaper Companion is officially live! New codes, events, and important updates will be added regularly.",
                    descriptionEs =
                        "¡Reaper Companion ya está oficialmente disponible! Se añadirán regularmente nuevos códigos, eventos y actualizaciones importantes.",
                    expires = "Limited Time",
                    expiresEs = "Tiempo limitado",
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
                    expiresEs =
                        "Sin fecha de vencimiento anunciada",
                    active = true
                )
            ),
            updates = listOf(
                LiveUpdate(
                    id = "patch_10_0_3",
                    title = "Update 10.0.3 Bugfix Patch",
                    titleEs =
                        "Actualización correctiva 10.0.3",
                    description =
                        "The latest live patch fixes issues affecting maps, perks, Killers, loadouts, and general gameplay.",
                    descriptionEs =
                        "El parche más reciente corrige problemas relacionados con mapas, ventajas, asesinos, configuraciones y la jugabilidad general.",
                    date = "July 21, 2026",
                    dateEs = "21 de julio de 2026",
                    category = "PATCH",
                    categoryEs = "PARCHE",
                    url =
                        "https://forums.bhvr.com/dead-by-daylight/kb/articles/553-10-0-3-bugfix-patch",
                    active = true
                )
            )
        )
    }
}