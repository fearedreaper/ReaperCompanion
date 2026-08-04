package com.example.reapercompanion.database

import com.example.reapercompanion.models.PerkSynergy

object PerkSynergyDatabase {

    val survivorSynergies = listOf(
        PerkSynergy(
            perkId = "windows_of_opportunity",
            partnerPerkId = "lithe",
            score = 10,
            reason = "Windows helps identify strong vault routes, while Lithe creates distance after using them."
        ),
        PerkSynergy(
            perkId = "windows_of_opportunity",
            partnerPerkId = "resilience",
            score = 9,
            reason = "Resilience improves vaulting and other actions while injured, strengthening chase performance."
        ),
        PerkSynergy(
            perkId = "windows_of_opportunity",
            partnerPerkId = "adrenaline",
            score = 9,
            reason = "Adrenaline gives the build a powerful endgame recovery and escape option."
        ),
        PerkSynergy(
            perkId = "deja_vu",
            partnerPerkId = "prove_thyself",
            score = 10,
            reason = "Deja Vu identifies important generators, while Prove Thyself improves cooperative repairs."
        ),
        PerkSynergy(
            perkId = "well_make_it",
            partnerPerkId = "empathy",
            score = 9,
            reason = "Empathy helps locate injured Survivors, while We'll Make It speeds up healing after rescues."
        ),
        PerkSynergy(
            perkId = "quick_and_quiet",
            partnerPerkId = "dance_with_me",
            score = 10,
            reason = "Both perks help hide movement after rushed actions and create stronger escape plays."
        )
    )

    val killerSynergies = listOf(
        PerkSynergy(
            perkId = "scourge_hook_pain_resonance",
            partnerPerkId = "deadlock",
            score = 10,
            reason = "Pain Resonance damages generators while Deadlock blocks the next major objective."
        ),
        PerkSynergy(
            perkId = "scourge_hook_pain_resonance",
            partnerPerkId = "pop_goes_the_weasel",
            score = 9,
            reason = "Both perks reward hooks with strong generator regression."
        ),
        PerkSynergy(
            perkId = "lethal_pursuer",
            partnerPerkId = "barbecue_and_chilli",
            score = 10,
            reason = "Lethal Pursuer strengthens aura-reading duration, while Barbecue provides tracking after hooks."
        ),
        PerkSynergy(
            perkId = "enduring",
            partnerPerkId = "spirit_fury",
            score = 10,
            reason = "Enduring reduces pallet stun time while Spirit Fury destroys the pallet."
        ),
        PerkSynergy(
            perkId = "hex_ruin",
            partnerPerkId = "hex_undying",
            score = 10,
            reason = "Undying helps protect Ruin and extends the life of the Hex setup."
        ),
        PerkSynergy(
            perkId = "trail_of_torment",
            partnerPerkId = "nowhere_to_hide",
            score = 9,
            reason = "Damaging a generator grants stealth while revealing nearby Survivors."
        )
    )

    fun getPartnersFor(
        perkId: String
    ): List<PerkSynergy> {
        return (survivorSynergies + killerSynergies)
            .filter { synergy ->
                synergy.perkId == perkId ||
                        synergy.partnerPerkId == perkId
            }
            .sortedByDescending { synergy ->
                synergy.score
            }
    }
}