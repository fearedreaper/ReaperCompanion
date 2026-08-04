package com.example.reapercompanion.database

import com.example.reapercompanion.models.KillerBuildRecommendation

object KillerBuildDatabase {

    val genRegression = KillerBuildRecommendation(
        name = "Generator Destroyer",
        goal = "GEN REGRESSION",
        score = 97,
        difficulty = "Medium",
        perks = listOf(
            KillerPerkDatabase.scourgeHookPainResonance,
            KillerPerkDatabase.popGoesTheWeasel,
            KillerPerkDatabase.deadlock,
            KillerPerkDatabase.corruptIntervention
        ),
        alternatives = listOf(
            KillerPerkDatabase.hexRuin,
            KillerPerkDatabase.nowhereToHide,
            KillerPerkDatabase.lethalPursuer,
            KillerPerkDatabase.tinkerer
        ),
        strengths = listOf(
            "Excellent generator slowdown",
            "Strong early-game control",
            "Reliable pressure",
            "Works on nearly every Killer"
        ),
        explanation =
            "This build controls generator progress from the opening moments of the trial. Corrupt Intervention slows the early game, while Pain Resonance, Pop Goes the Weasel, and Deadlock punish generator progress throughout the match."
    )

    val antiLoop = KillerBuildRecommendation(
        name = "Loop Breaker",
        goal = "ANTI-LOOP",
        score = 94,
        difficulty = "Hard",
        perks = listOf(
            KillerPerkDatabase.bamboozle,
            KillerPerkDatabase.enduring,
            KillerPerkDatabase.spiritFury,
            KillerPerkDatabase.brutalStrength
        ),
        alternatives = listOf(
            KillerPerkDatabase.nowhereToHide,
            KillerPerkDatabase.popGoesTheWeasel,
            KillerPerkDatabase.deadlock,
            KillerPerkDatabase.corruptIntervention
        ),
        strengths = listOf(
            "Ends chases quickly",
            "Destroys pallets efficiently",
            "Punishes greedy looping",
            "Maintains chase momentum"
        ),
        explanation =
            "This build reduces the value Survivors gain from pallets and windows. Bamboozle blocks strong vaults, while Enduring and Spirit Fury punish pallet stuns and Brutal Strength clears resources faster."
    )

    val auraReading = KillerBuildRecommendation(
        name = "Relentless Hunter",
        goal = "AURA READING",
        score = 95,
        difficulty = "Easy",
        perks = listOf(
            KillerPerkDatabase.lethalPursuer,
            KillerPerkDatabase.nowhereToHide,
            KillerPerkDatabase.barbecueAndChilli,
            KillerPerkDatabase.nursesCalling
        ),
        alternatives = listOf(
            KillerPerkDatabase.deadlock,
            KillerPerkDatabase.corruptIntervention,
            KillerPerkDatabase.popGoesTheWeasel,
            KillerPerkDatabase.tinkerer
        ),
        strengths = listOf(
            "Constant Survivor tracking",
            "Excellent map awareness",
            "Strong snowball potential",
            "Beginner friendly"
        ),
        explanation =
            "This build provides information during every stage of the trial. Lethal Pursuer starts the first chase quickly, while the other perks reveal Survivors after hooks, generator kicks, and healing actions."
    )

    val stealth = KillerBuildRecommendation(
        name = "Silent Terror",
        goal = "STEALTH",
        score = 90,
        difficulty = "Medium",
        perks = listOf(
            KillerPerkDatabase.trailOfTorment,
            KillerPerkDatabase.tinkerer,
            KillerPerkDatabase.darkDevotion,
            KillerPerkDatabase.nowhereToHide
        ),
        alternatives = listOf(
            KillerPerkDatabase.lethalPursuer,
            KillerPerkDatabase.nursesCalling,
            KillerPerkDatabase.deadlock,
            KillerPerkDatabase.popGoesTheWeasel
        ),
        strengths = listOf(
            "Creates surprise attacks",
            "Hides the Terror Radius",
            "Provides generator information",
            "Strong on mobile Killers"
        ),
        explanation =
            "This build repeatedly hides your Terror Radius and creates unpredictable approaches. Tinkerer and Trail of Torment grant stealth, Dark Devotion causes confusion, and Nowhere to Hide exposes nearby targets."
    )

    val hexBuild = KillerBuildRecommendation(
        name = "Totem Nightmare",
        goal = "HEX BUILD",
        score = 91,
        difficulty = "Hard",
        perks = listOf(
            KillerPerkDatabase.hexRuin,
            KillerPerkDatabase.hexUndying,
            KillerPerkDatabase.hexDevourHope,
            KillerPerkDatabase.noWayOut
        ),
        alternatives = listOf(
            KillerPerkDatabase.corruptIntervention,
            KillerPerkDatabase.deadlock,
            KillerPerkDatabase.lethalPursuer,
            KillerPerkDatabase.nursesCalling
        ),
        strengths = listOf(
            "Powerful passive slowdown",
            "Dangerous late-game potential",
            "Forces Survivors to hunt Totems",
            "Creates unpredictable matches"
        ),
        explanation =
            "Ruin pressures unattended generators while Undying helps protect your Hex setup. Devour Hope can become extremely dangerous if Survivors fail to cleanse it, and No Way Out provides additional endgame control."
    )

    val endgame = KillerBuildRecommendation(
        name = "No Escape",
        goal = "ENDGAME",
        score = 92,
        difficulty = "Medium",
        perks = listOf(
            KillerPerkDatabase.noWayOut,
            KillerPerkDatabase.rememberMe,
            KillerPerkDatabase.bloodWarden,
            KillerPerkDatabase.deadlock
        ),
        alternatives = listOf(
            KillerPerkDatabase.hexDevourHope,
            KillerPerkDatabase.corruptIntervention,
            KillerPerkDatabase.popGoesTheWeasel,
            KillerPerkDatabase.tinkerer
        ),
        strengths = listOf(
            "Strong exit-gate control",
            "Punishes premature gate opening",
            "Creates late-game comeback potential",
            "Delays Survivor escapes"
        ),
        explanation =
            "This build becomes strongest after the generators are completed. No Way Out and Remember Me delay the gates, while Blood Warden can trap Survivors inside the trial and turn a losing match into a comeback."
    )

    val beginner = KillerBuildRecommendation(
        name = "Reliable Hunter",
        goal = "BEGINNER",
        score = 88,
        difficulty = "Easy",
        perks = listOf(
            KillerPerkDatabase.brutalStrength,
            KillerPerkDatabase.enduring,
            KillerPerkDatabase.tinkerer,
            KillerPerkDatabase.nursesCalling
        ),
        alternatives = listOf(
            KillerPerkDatabase.corruptIntervention,
            KillerPerkDatabase.deadlock,
            KillerPerkDatabase.bamboozle,
            KillerPerkDatabase.nowhereToHide
        ),
        strengths = listOf(
            "Simple effects",
            "Useful on many Killers",
            "Improves chase consistency",
            "Provides clear information"
        ),
        explanation =
            "This beginner-friendly build uses straightforward perks that provide value without complicated conditions. It improves pallet interactions, generator awareness, and Survivor tracking."
    )

    val memeBuild = KillerBuildRecommendation(
        name = "Trial of Confusion",
        goal = "MEME BUILD",
        score = 82,
        difficulty = "Hard",
        perks = listOf(
            KillerPerkDatabase.darkDevotion,
            KillerPerkDatabase.trailOfTorment,
            KillerPerkDatabase.bloodWarden,
            KillerPerkDatabase.hexDevourHope
        ),
        alternatives = listOf(
            KillerPerkDatabase.tinkerer,
            KillerPerkDatabase.rememberMe,
            KillerPerkDatabase.hexUndying,
            KillerPerkDatabase.noWayOut
        ),
        strengths = listOf(
            "Unpredictable Terror Radius",
            "Surprise attacks",
            "Chaotic endgame moments",
            "Entertaining high-risk plays"
        ),
        explanation =
            "This chaotic build focuses on confusing Survivors with stealth and unexpected late-game pressure. It is less consistent than a competitive build, but it can produce memorable matches."
    )

    fun getBuild(goal: String): KillerBuildRecommendation {
        return when (goal) {
            "GEN REGRESSION" -> genRegression
            "ANTI-LOOP" -> antiLoop
            "AURA READING" -> auraReading
            "STEALTH" -> stealth
            "HEX BUILD" -> hexBuild
            "ENDGAME" -> endgame
            "BEGINNER" -> beginner
            "MEME BUILD" -> memeBuild
            else -> genRegression
        }
    }
}