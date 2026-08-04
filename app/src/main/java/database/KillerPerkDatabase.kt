package com.example.reapercompanion.database

import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkCategory
import com.example.reapercompanion.models.PerkRole

object KillerPerkDatabase {

    private fun killerPerk(
        id: String,
        name: String,
        category: PerkCategory,
        description: String,
        owner: String,
        isBaseGame: Boolean
    ) = Perk(
        id = id,
        name = name,
        role = PerkRole.KILLER,
        category = category,
        description = description,
        owner = owner,
        isBaseGame = isBaseGame
    )

    val scourgeHookPainResonance = killerPerk(
        id = "scourge_hook_pain_resonance",
        name = "Scourge Hook: Pain Resonance",
        category = PerkCategory.GENERATOR,
        description = "Damages the generator with the most progress after hooking a Survivor on a Scourge Hook.",
        owner = "The Artist",
        isBaseGame = false
    )

    val popGoesTheWeasel = killerPerk(
        id = "pop_goes_the_weasel",
        name = "Pop Goes the Weasel",
        category = PerkCategory.GENERATOR,
        description = "After hooking a Survivor, kicking a generator removes additional progress.",
        owner = "The Clown",
        isBaseGame = false
    )

    val corruptIntervention = killerPerk(
        id = "corrupt_intervention",
        name = "Corrupt Intervention",
        category = PerkCategory.GENERATOR,
        description = "Blocks several distant generators at the beginning of the trial.",
        owner = "The Plague",
        isBaseGame = false
    )

    val deadlock = killerPerk(
        id = "deadlock",
        name = "Deadlock",
        category = PerkCategory.GENERATOR,
        description = "Blocks the generator with the most progress after another generator is completed.",
        owner = "The Cenobite",
        isBaseGame = false
    )

    val bamboozle = killerPerk(
        id = "bamboozle",
        name = "Bamboozle",
        category = PerkCategory.CHASE,
        description = "Vault faster and temporarily block the vaulted window from Survivors.",
        owner = "The Clown",
        isBaseGame = false
    )

    val enduring = killerPerk(
        id = "enduring",
        name = "Enduring",
        category = PerkCategory.CHASE,
        description = "Reduces the duration of pallet stuns.",
        owner = "The Hillbilly",
        isBaseGame = true
    )

    val spiritFury = killerPerk(
        id = "spirit_fury",
        name = "Spirit Fury",
        category = PerkCategory.CHASE,
        description = "After breaking enough pallets, the next pallet stun immediately destroys the pallet.",
        owner = "The Spirit",
        isBaseGame = false
    )

    val brutalStrength = killerPerk(
        id = "brutal_strength",
        name = "Brutal Strength",
        category = PerkCategory.CHASE,
        description = "Break pallets, breakable walls, and damage generators faster.",
        owner = "The Trapper",
        isBaseGame = true
    )

    val nowhereToHide = killerPerk(
        id = "nowhere_to_hide",
        name = "Nowhere to Hide",
        category = PerkCategory.INFORMATION,
        description = "Reveals nearby Survivor auras after damaging a generator.",
        owner = "The Knight",
        isBaseGame = false
    )

    val lethalPursuer = killerPerk(
        id = "lethal_pursuer",
        name = "Lethal Pursuer",
        category = PerkCategory.INFORMATION,
        description = "Reveals Survivor auras at the beginning of the trial and extends other aura-reading effects.",
        owner = "The Nemesis",
        isBaseGame = false
    )

    val barbecueAndChilli = killerPerk(
        id = "barbecue_and_chilli",
        name = "Barbecue & Chilli",
        category = PerkCategory.INFORMATION,
        description = "Reveals distant Survivor auras after hooking a Survivor.",
        owner = "The Cannibal",
        isBaseGame = false
    )

    val nursesCalling = killerPerk(
        id = "a_nurses_calling",
        name = "A Nurse's Calling",
        category = PerkCategory.INFORMATION,
        description = "Reveals the auras of Survivors healing within range.",
        owner = "The Nurse",
        isBaseGame = true
    )

    val tinkerer = killerPerk(
        id = "tinkerer",
        name = "Tinkerer",
        category = PerkCategory.STEALTH,
        description = "Become Undetectable when a generator reaches high progress.",
        owner = "The Hillbilly",
        isBaseGame = true
    )

    val trailOfTorment = killerPerk(
        id = "trail_of_torment",
        name = "Trail of Torment",
        category = PerkCategory.STEALTH,
        description = "Become Undetectable after damaging a generator.",
        owner = "The Executioner",
        isBaseGame = false
    )

    val darkDevotion = killerPerk(
        id = "dark_devotion",
        name = "Dark Devotion",
        category = PerkCategory.STEALTH,
        description = "Transfers your terror radius to the Obsession after injuring them.",
        owner = "The Plague",
        isBaseGame = false
    )

    val hexRuin = killerPerk(
        id = "hex_ruin",
        name = "Hex: Ruin",
        category = PerkCategory.GENERATOR,
        description = "Causes unattended generators to automatically lose progress while the Hex remains active.",
        owner = "The Hag",
        isBaseGame = false
    )

    val hexUndying = killerPerk(
        id = "hex_undying",
        name = "Hex: Undying",
        category = PerkCategory.SUPPORT,
        description = "Protects another Hex Totem and provides information near dull Totems.",
        owner = "The Blight",
        isBaseGame = false
    )

    val hexDevourHope = killerPerk(
        id = "hex_devour_hope",
        name = "Hex: Devour Hope",
        category = PerkCategory.ENDGAME,
        description = "Rewards distant unhooks with increasingly dangerous effects.",
        owner = "The Hag",
        isBaseGame = false
    )

    val noWayOut = killerPerk(
        id = "no_way_out",
        name = "No Way Out",
        category = PerkCategory.ENDGAME,
        description = "Temporarily blocks exit gate switches after Survivors attempt to open them.",
        owner = "The Trickster",
        isBaseGame = false
    )

    val rememberMe = killerPerk(
        id = "remember_me",
        name = "Remember Me",
        category = PerkCategory.ENDGAME,
        description = "Increases the time required for Survivors other than the Obsession to open exit gates.",
        owner = "The Nightmare",
        isBaseGame = false
    )

    val bloodWarden = killerPerk(
        id = "blood_warden",
        name = "Blood Warden",
        category = PerkCategory.ENDGAME,
        description = "Can block the exits after hooking a Survivor once an exit gate is open.",
        owner = "The Nightmare",
        isBaseGame = false
    )

    val allPerks = listOf(
        scourgeHookPainResonance,
        popGoesTheWeasel,
        corruptIntervention,
        deadlock,
        bamboozle,
        enduring,
        spiritFury,
        brutalStrength,
        nowhereToHide,
        lethalPursuer,
        barbecueAndChilli,
        nursesCalling,
        tinkerer,
        trailOfTorment,
        darkDevotion,
        hexRuin,
        hexUndying,
        hexDevourHope,
        noWayOut,
        rememberMe,
        bloodWarden
    )

    fun getPerkByName(name: String): Perk? {
        return allPerks.firstOrNull {
            it.name.equals(name, ignoreCase = true)
        }
    }

    fun getPerksByCategory(
        category: PerkCategory
    ): List<Perk> {
        return allPerks.filter {
            it.category == category
        }
    }
}