package com.example.reapercompanion.database

import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkCategory
import com.example.reapercompanion.models.PerkRole

object PerkDatabase {

    private fun survivorPerk(
        id: String,
        name: String,
        category: PerkCategory,
        description: String,
        owner: String,
        isBaseGame: Boolean,
        imageUrl: String? = null
    ) = Perk(
        id = id,
        name = name,
        role = PerkRole.SURVIVOR,
        category = category,
        description = description,
        owner = owner,
        isBaseGame = isBaseGame,
        imageUrl = imageUrl
    )

    val windowsOfOpportunity = survivorPerk(
        id = "windows_of_opportunity",
        name = "Windows of Opportunity",
        category = PerkCategory.INFORMATION,
        description = "Reveals nearby pallets, breakable walls, and vault locations.",
        owner = "Kate Denson",
        isBaseGame = false
    )

    val lithe = survivorPerk(
        id = "lithe",
        name = "Lithe",
        category = PerkCategory.CHASE,
        description = "Gain a burst of speed after performing a rushed vault.",
        owner = "Feng Min",
        isBaseGame = false
    )

    val resilience = survivorPerk(
        id = "resilience",
        name = "Resilience",
        category = PerkCategory.CHASE,
        description = "Perform several actions faster while injured.",
        owner = "General Survivor Perk",
        isBaseGame = true
    )

    val adrenaline = survivorPerk(
        id = "adrenaline",
        name = "Adrenaline",
        category = PerkCategory.ENDGAME,
        description = "Gain healing and speed when the exit gates become powered.",
        owner = "Meg Thomas",
        isBaseGame = true
    )

    val dejaVu = survivorPerk(
        id = "deja_vu",
        name = "Deja Vu",
        category = PerkCategory.GENERATOR,
        description = "Reveals generators positioned close together.",
        owner = "General Survivor Perk",
        isBaseGame = true
    )

    val proveThyself = survivorPerk(
        id = "prove_thyself",
        name = "Prove Thyself",
        category = PerkCategory.GENERATOR,
        description = "Improves cooperative repair efficiency.",
        owner = "Dwight Fairfield",
        isBaseGame = true
    )

    val wellMakeIt = survivorPerk(
        id = "well_make_it",
        name = "We'll Make It",
        category = PerkCategory.HEALING,
        description = "Improves healing speed after safely unhooking a Survivor.",
        owner = "General Survivor Perk",
        isBaseGame = true
    )

    val botanyKnowledge = survivorPerk(
        id = "botany_knowledge",
        name = "Botany Knowledge",
        category = PerkCategory.HEALING,
        description = "Improves healing efficiency.",
        owner = "Claudette Morel",
        isBaseGame = true
    )

    val empathy = survivorPerk(
        id = "empathy",
        name = "Empathy",
        category = PerkCategory.INFORMATION,
        description = "Reveals injured or dying Survivors within range.",
        owner = "Claudette Morel",
        isBaseGame = true
    )

    val kindred = survivorPerk(
        id = "kindred",
        name = "Kindred",
        category = PerkCategory.INFORMATION,
        description = "Provides aura information while a Survivor is hooked.",
        owner = "General Survivor Perk",
        isBaseGame = true
    )

    val quickAndQuiet = survivorPerk(
        id = "quick_and_quiet",
        name = "Quick & Quiet",
        category = PerkCategory.STEALTH,
        description = "Suppresses noise from rushed actions after its cooldown.",
        owner = "Meg Thomas",
        isBaseGame = true
    )

    val lightweight = survivorPerk(
        id = "lightweight",
        name = "Lightweight",
        category = PerkCategory.STEALTH,
        description = "Makes scratch marks harder for the Killer to track.",
        owner = "General Survivor Perk",
        isBaseGame = true
    )

    val distortion = survivorPerk(
        id = "distortion",
        name = "Distortion",
        category = PerkCategory.STEALTH,
        description = "Helps hide your aura from the Killer.",
        owner = "Jeff Johansen",
        isBaseGame = false
    )

    val ironWill = survivorPerk(
        id = "iron_will",
        name = "Iron Will",
        category = PerkCategory.STEALTH,
        description = "Reduces grunts of pain while injured.",
        owner = "Jake Park",
        isBaseGame = true
    )

    val headOn = survivorPerk(
        id = "head_on",
        name = "Head On",
        category = PerkCategory.MEME,
        description = "Allows a rushed locker exit to stun the Killer.",
        owner = "Jane Romero",
        isBaseGame = false
    )

    val flashbang = survivorPerk(
        id = "flashbang",
        name = "Flashbang",
        category = PerkCategory.SUPPORT,
        description = "Create a flash grenade after completing generator progress.",
        owner = "Leon S. Kennedy",
        isBaseGame = false
    )

    val deception = survivorPerk(
        id = "deception",
        name = "Deception",
        category = PerkCategory.MEME,
        description = "Fake a locker entry to confuse the Killer.",
        owner = "Élodie Rakoto",
        isBaseGame = false
    )

    val offTheRecord = survivorPerk(
        id = "off_the_record",
        name = "Off the Record",
        category = PerkCategory.SECOND_CHANCE,
        description = "Provides protection and stealth after being unhooked.",
        owner = "Zarina Kassir",
        isBaseGame = false
    )

    val finesse = survivorPerk(
        id = "finesse",
        name = "Finesse",
        category = PerkCategory.CHASE,
        description = "Improves vault performance under certain conditions.",
        owner = "Lara Croft",
        isBaseGame = false
    )

    val balancedLanding = survivorPerk(
        id = "balanced_landing",
        name = "Balanced Landing",
        category = PerkCategory.CHASE,
        description = "Reduces stagger and grants speed after falling.",
        owner = "Nea Karlsson",
        isBaseGame = true
    )

    val fiveMovesAhead = survivorPerk(
        id = "five_moves_ahead",
        name = "Five Moves Ahead",
        category = PerkCategory.CHASE,
        description = "Supports proactive pathing during a chase.",
        owner = "Survivor Perk",
        isBaseGame = false
    )

    val builtToLast = survivorPerk(
        id = "built_to_last",
        name = "Built to Last",
        category = PerkCategory.GENERATOR,
        description = "Restores item charges after hiding in a locker.",
        owner = "Felix Richter",
        isBaseGame = false
    )

    val overzealous = survivorPerk(
        id = "overzealous",
        name = "Overzealous",
        category = PerkCategory.GENERATOR,
        description = "Improves repair speed after cleansing or blessing a Totem.",
        owner = "Haddie Kaur",
        isBaseGame = false
    )

    val stakeOut = survivorPerk(
        id = "stake_out",
        name = "Stake Out",
        category = PerkCategory.GENERATOR,
        description = "Converts skill checks into stronger repair progress.",
        owner = "David Tapp",
        isBaseGame = false
    )

    val hyperfocus = survivorPerk(
        id = "hyperfocus",
        name = "Hyperfocus",
        category = PerkCategory.GENERATOR,
        description = "Rewards consecutive Great Skill Checks.",
        owner = "Rebecca Chambers",
        isBaseGame = false
    )

    val desperateMeasures = survivorPerk(
        id = "desperate_measures",
        name = "Desperate Measures",
        category = PerkCategory.HEALING,
        description = "Improves healing and unhooking speed when teammates are injured.",
        owner = "Felix Richter",
        isBaseGame = false
    )

    val aftercare = survivorPerk(
        id = "aftercare",
        name = "Aftercare",
        category = PerkCategory.SUPPORT,
        description = "Creates aura-reading connections with assisted teammates.",
        owner = "Jeff Johansen",
        isBaseGame = false
    )

    val babysitter = survivorPerk(
        id = "babysitter",
        name = "Babysitter",
        category = PerkCategory.SUPPORT,
        description = "Helps protect Survivors after they are unhooked.",
        owner = "Steve Harrington",
        isBaseGame = false
    )

    val leader = survivorPerk(
        id = "leader",
        name = "Leader",
        category = PerkCategory.SUPPORT,
        description = "Improves several actions performed by nearby teammates.",
        owner = "Dwight Fairfield",
        isBaseGame = true
    )

    val danceWithMe = survivorPerk(
        id = "dance_with_me",
        name = "Dance With Me",
        category = PerkCategory.STEALTH,
        description = "Suppresses scratch marks after certain rushed actions.",
        owner = "Kate Denson",
        isBaseGame = false
    )

    val luckyBreak = survivorPerk(
        id = "lucky_break",
        name = "Lucky Break",
        category = PerkCategory.STEALTH,
        description = "Temporarily suppresses blood and scratch marks after injury.",
        owner = "Yui Kimura",
        isBaseGame = false
    )

    val calmSpirit = survivorPerk(
        id = "calm_spirit",
        name = "Calm Spirit",
        category = PerkCategory.STEALTH,
        description = "Suppresses screaming and reduces wildlife reactions.",
        owner = "Jake Park",
        isBaseGame = true
    )

    val urbanEvasion = survivorPerk(
        id = "urban_evasion",
        name = "Urban Evasion",
        category = PerkCategory.STEALTH,
        description = "Increases crouching movement speed.",
        owner = "Nea Karlsson",
        isBaseGame = true
    )

    val blastMine = survivorPerk(
        id = "blast_mine",
        name = "Blast Mine",
        category = PerkCategory.MEME,
        description = "Traps a generator and blinds or stuns the Killer.",
        owner = "Jill Valentine",
        isBaseGame = false
    )

    val diversion = survivorPerk(
        id = "diversion",
        name = "Diversion",
        category = PerkCategory.MEME,
        description = "Throw a pebble to create a false notification.",
        owner = "Adam Francis",
        isBaseGame = false
    )

    val powerStruggle = survivorPerk(
        id = "power_struggle",
        name = "Power Struggle",
        category = PerkCategory.SECOND_CHANCE,
        description = "Drop a pallet while being carried under certain conditions.",
        owner = "Élodie Rakoto",
        isBaseGame = false
    )

    val chemicalTrap = survivorPerk(
        id = "chemical_trap",
        name = "Chemical Trap",
        category = PerkCategory.MEME,
        description = "Trap a dropped pallet to slow the Killer after it is broken.",
        owner = "Ellen Ripley",
        isBaseGame = false
    )

    val decisiveStrike = survivorPerk(
        id = "decisive_strike",
        name = "Decisive Strike",
        category = PerkCategory.SECOND_CHANCE,
        description = "Provides a chance to escape the Killer after being unhooked.",
        owner = "Laurie Strode",
        isBaseGame = false
    )

    val unbreakable = survivorPerk(
        id = "unbreakable",
        name = "Unbreakable",
        category = PerkCategory.SECOND_CHANCE,
        description = "Allows recovery from the dying state once per trial.",
        owner = "William 'Bill' Overbeck",
        isBaseGame = true
    )

    val deliverance = survivorPerk(
        id = "deliverance",
        name = "Deliverance",
        category = PerkCategory.SECOND_CHANCE,
        description = "Allows a guaranteed self-unhook after a safe rescue.",
        owner = "Adam Francis",
        isBaseGame = false
    )

    val deadHard = survivorPerk(
        id = "dead_hard",
        name = "Dead Hard",
        category = PerkCategory.SECOND_CHANCE,
        description = "Provides brief protection during a chase.",
        owner = "David King",
        isBaseGame = true
    )

    val bond = survivorPerk(
        id = "bond",
        name = "Bond",
        category = PerkCategory.INFORMATION,
        description = "Reveals nearby teammates.",
        owner = "Dwight Fairfield",
        isBaseGame = true
    )

    val allPerks = listOf(
        windowsOfOpportunity,
        lithe,
        resilience,
        adrenaline,
        dejaVu,
        proveThyself,
        wellMakeIt,
        botanyKnowledge,
        empathy,
        kindred,
        quickAndQuiet,
        lightweight,
        distortion,
        ironWill,
        headOn,
        flashbang,
        deception,
        offTheRecord,
        finesse,
        balancedLanding,
        fiveMovesAhead,
        builtToLast,
        overzealous,
        stakeOut,
        hyperfocus,
        desperateMeasures,
        aftercare,
        babysitter,
        leader,
        danceWithMe,
        luckyBreak,
        calmSpirit,
        urbanEvasion,
        blastMine,
        diversion,
        powerStruggle,
        chemicalTrap,
        decisiveStrike,
        unbreakable,
        deliverance,
        deadHard,
        bond
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

    fun getSurvivorPerks(): List<Perk> {
        return allPerks.filter {
            it.role == PerkRole.SURVIVOR
        }
    }
}