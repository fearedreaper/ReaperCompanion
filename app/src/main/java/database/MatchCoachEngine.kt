package com.example.reapercompanion.database

import com.example.reapercompanion.models.MatchCoachRecommendation
import com.example.reapercompanion.models.Perk

object MatchCoachEngine {

    fun getSupportedKillers(): List<String> = listOf(
        "The Trapper",
        "The Wraith",
        "The Hillbilly",
        "The Nurse",
        "The Shape",
        "The Hag",
        "The Doctor",
        "The Huntress",
        "The Cannibal",
        "The Nightmare",
        "The Pig",
        "The Clown",
        "The Spirit",
        "The Legion",
        "The Plague",
        "The Ghost Face",
        "The Demogorgon",
        "The Oni",
        "The Deathslinger",
        "The Executioner",
        "The Blight",
        "The Twins",
        "The Trickster",
        "The Nemesis",
        "The Cenobite",
        "The Artist",
        "The Onryo",
        "The Dredge",
        "The Mastermind",
        "The Knight",
        "The Skull Merchant",
        "The Singularity",
        "The Xenomorph",
        "The Good Guy",
        "The Unknown",
        "The Lich",
        "The Dark Lord",
        "The Houndmaster",
        "The Ghoul",
        "The Animatronic",
        "The Krasue",
        "The First",
        "The Slasher"
    )

    fun getSupportedMaps(): List<String> = listOf(
        "Midwich Elementary School",
        "Gideon Meat Plant",
        "Raccoon City Police Department",
        "Mount Ormond Resort",
        "Badham Preschool"
    )

    fun generateRecommendation(
        opponentName: String,
        mapName: String
    ): MatchCoachRecommendation {
        val survivorPerks = PerkDatabase.getSurvivorPerks()

        val opponentProfile = getOpponentProfile(
            opponentName = normalize(opponentName)
        )

        val mapProfile = getMapProfile(
            mapName = normalize(mapName)
        )

        val contextAdvice = MatchupContextEngine.generateContextAdvice(
            opponentName = opponentName,
            mapName = mapName
        )

        val preferredPerkIds = (
                opponentProfile.recommendedPerkIds +
                        mapProfile.recommendedPerkIds +
                        generalRecommendedPerkIds
                ).distinct()

        val recommendedPerks = selectPerks(
            allPerks = survivorPerks,
            preferredIds = preferredPerkIds,
            count = 4
        )

        val difficultyScore = (
                opponentProfile.difficultyScore +
                        mapProfile.difficultyModifier -
                        contextAdvice.scoreModifier
                ).coerceIn(1, 100)

        val displayOpponent = opponentName.ifBlank {
            "Unknown Killer"
        }

        val displayMap = mapName.ifBlank {
            "Unknown Realm"
        }

        val difficulty = difficultyLabel(
            score = difficultyScore
        )

        val threatLevel = threatLabel(
            score = difficultyScore
        )

        val reaperScore = (
                100 -
                        difficultyScore / 3 +
                        recommendedPerks.size * 2
                ).coerceIn(70, 98)

        val reaperVoiceMessage =
            ReaperVoiceEngine.createCoachMessage(
                opponentName = displayOpponent,
                mapName = displayMap,
                threatLevel = threatLevel,
                difficulty = difficulty,
                score = reaperScore
            )

        return MatchCoachRecommendation(
            title = "Survivor Match Plan",
            opponentName = displayOpponent,
            mapName = displayMap,
            difficulty = difficulty,
            score = reaperScore,
            threatLevel = threatLevel,
            recommendedPerks = recommendedPerks,
            chaseAdvice = (
                    contextAdvice.chaseAdjustments +
                            opponentProfile.chaseAdvice +
                            mapProfile.chaseAdvice
                    ).distinct().take(6),
            objectiveAdvice = (
                    contextAdvice.objectiveAdjustments +
                            opponentProfile.objectiveAdvice +
                            mapProfile.objectiveAdvice +
                            generalObjectiveAdvice
                    ).distinct().take(6),
            endgameAdvice = (
                    contextAdvice.endgameAdjustments +
                            opponentProfile.endgameAdvice +
                            mapProfile.endgameAdvice +
                            generalEndgameAdvice
                    ).distinct().take(5),
            warnings = (
                    contextAdvice.priorityWarnings +
                            opponentProfile.warnings +
                            mapProfile.warnings
                    ).distinct().take(6),
            summary =
                "$reaperVoiceMessage\n\n" +
                        "${contextAdvice.killerMapSummary} " +
                        "${opponentProfile.summary} ${mapProfile.summary}"
        )
    }

    private fun normalize(value: String): String {
        return value
            .trim()
            .lowercase()
            .replace("ō", "o")
            .replace("–", "-")
    }

    private fun selectPerks(
        allPerks: List<Perk>,
        preferredIds: List<String>,
        count: Int
    ): List<Perk> {
        val preferred = preferredIds
            .mapNotNull { id ->
                allPerks.firstOrNull { perk ->
                    perk.id == id
                }
            }
            .distinctBy { perk ->
                perk.id
            }

        val fallback = allPerks.filter { perk ->
            preferred.none { selected ->
                selected.id == perk.id
            }
        }

        return (preferred + fallback)
            .distinctBy { perk ->
                perk.id
            }
            .take(count)
    }

    private fun getOpponentProfile(
        opponentName: String
    ): OpponentProfile {
        return when {
            opponentName.contains("nurse") ->
                blinkProfile()

            opponentName.contains("blight") ->
                rushProfile(
                    difficulty = 91,
                    summary =
                        "Tight geometry and sharp direction changes reduce the value of his rushes."
                )

            opponentName.contains("hillbilly") ->
                rushProfile(
                    difficulty = 78,
                    summary =
                        "Curved structures and early path changes make chainsaw approaches less reliable."
                )

            opponentName.contains("oni") ->
                rushProfile(
                    difficulty = 84,
                    summary =
                        "Denying early injuries delays his strongest mobility and snowball pressure."
                )

            opponentName.contains("mastermind") ||
                    opponentName.contains("wesker") ->
                rushProfile(
                    difficulty = 83,
                    summary =
                        "Create space, avoid narrow lanes, and manage infection before it becomes critical."
                )

            opponentName.contains("houndmaster") ->
                rushProfile(
                    difficulty = 82,
                    summary =
                        "Break targeting angles and avoid predictable routes where the companion can cut you off."
                )

            opponentName.contains("slasher") ||
                    opponentName.contains("jason") ->
                rushProfile(
                    difficulty = 86,
                    summary =
                        "Keep rotating through safe resources and avoid being isolated in dead zones."
                )

            opponentName.contains("huntress") ->
                rangedProfile(
                    difficulty = 82,
                    summary =
                        "Solid cover repeatedly denies clean hatchet angles."
                )

            opponentName.contains("deathslinger") ->
                rangedProfile(
                    difficulty = 79,
                    summary =
                        "Break sightlines and avoid narrow windows where the Redeemer is easy to line up."
                )

            opponentName.contains("trickster") ->
                rangedProfile(
                    difficulty = 77,
                    summary =
                        "Tall cover and fast corner changes prevent sustained knife exposure."
                )

            opponentName.contains("artist") ->
                rangedProfile(
                    difficulty = 84,
                    summary =
                        "Leave predictable loops before Dire Crows lock down your next route."
                )

            opponentName.contains("executioner") ||
                    opponentName.contains("pyramid") ->
                rangedProfile(
                    difficulty = 83,
                    summary =
                        "Delay vaults and pallet drops so punishment trails are harder to predict."
                )

            opponentName.contains("nemesis") ->
                rangedProfile(
                    difficulty = 80,
                    summary =
                        "Preserve distance and avoid feeding easy tentacle hits at windows and pallets."
                )

            opponentName.contains("unknown") ->
                rangedProfile(
                    difficulty = 86,
                    summary =
                        "Use cover, watch hallucinations, and avoid standing where ricochets can reach you."
                )

            opponentName.contains("animatronic") ||
                    opponentName.contains("springtrap") ->
                rangedProfile(
                    difficulty = 85,
                    summary =
                        "Respect the projectile threat and use security routes before committing to exposed actions."
                )

            opponentName.contains("first") ||
                    opponentName.contains("henry") ->
                rangedProfile(
                    difficulty = 90,
                    summary =
                        "Break psychic sightlines and avoid predictable grouping around objectives."
                )

            opponentName.contains("spirit") ->
                stealthProfile(
                    difficulty = 87,
                    summary =
                        "Varied movement and quiet resets make phase predictions less reliable."
                )

            opponentName.contains("wraith") ->
                stealthProfile(
                    difficulty = 70,
                    summary =
                        "Strong positioning before the uncloak matters more than reacting afterward."
                )

            opponentName.contains("ghost face") ||
                    opponentName.contains("ghostface") ->
                stealthProfile(
                    difficulty = 76,
                    summary =
                        "Frequent camera checks and early reveals deny exposed ambushes."
                )

            opponentName.contains("shape") ||
                    opponentName.contains("myers") ->
                stealthProfile(
                    difficulty = 74,
                    summary =
                        "Break stalking sightlines and track his tier progression."
                )

            opponentName.contains("pig") ->
                stealthProfile(
                    difficulty = 72,
                    summary =
                        "Watch for crouched approaches and manage reverse bear trap timing carefully."
                )

            opponentName.contains("onryo") ||
                    opponentName.contains("sadako") ->
                stealthProfile(
                    difficulty = 79,
                    summary =
                        "Control Condemned early and avoid giving free television pressure."
                )

            opponentName.contains("good guy") ||
                    opponentName.contains("chucky") ->
                stealthProfile(
                    difficulty = 84,
                    summary =
                        "Use strong visibility and avoid low-cover corners where his size hides the approach."
                )

            opponentName.contains("dark lord") ||
                    opponentName.contains("dracula") ->
                stealthProfile(
                    difficulty = 87,
                    summary =
                        "Read his current form and rotate before each mobility option closes the route."
                )

            opponentName.contains("ghoul") ||
                    opponentName.contains("kaneki") ->
                stealthProfile(
                    difficulty = 86,
                    summary =
                        "Avoid predictable injured routes and deny rapid follow-up pressure."
                )

            opponentName.contains("trapper") ->
                trapProfile(
                    difficulty = 67,
                    summary =
                        "Ground awareness removes much of his control."
                )

            opponentName.contains("hag") ->
                trapProfile(
                    difficulty = 75,
                    summary =
                        "Crouch through trapped areas and trigger marks only when the team can capitalize."
                )

            opponentName.contains("knight") ->
                trapProfile(
                    difficulty = 80,
                    summary =
                        "Leave loops early when guards cut off the safe side."
                )

            opponentName.contains("skull merchant") ->
                trapProfile(
                    difficulty = 78,
                    summary =
                        "Rotate before drone zones become fortified and avoid feeding repeated scans."
                )

            opponentName.contains("singularity") ->
                trapProfile(
                    difficulty = 82,
                    summary =
                        "Use EMPs proactively and deny reliable camera coverage around objectives."
                )

            opponentName.contains("xenomorph") ->
                trapProfile(
                    difficulty = 84,
                    summary =
                        "Set flame turrets early and avoid low loops while the tail attack is available."
                )

            opponentName.contains("doctor") ->
                disruptionProfile(
                    difficulty = 74,
                    summary =
                        "Pre-drop when necessary and avoid giving him easy shock timing at narrow loops."
                )

            opponentName.contains("clown") ->
                disruptionProfile(
                    difficulty = 73,
                    summary =
                        "Leave weakened loops early instead of waiting for bottles to remove every option."
                )

            opponentName.contains("legion") ->
                disruptionProfile(
                    difficulty = 69,
                    summary =
                        "Spread out and avoid unnecessary healing cycles during repeated Frenzy pressure."
                )

            opponentName.contains("plague") ->
                disruptionProfile(
                    difficulty = 78,
                    summary =
                        "Coordinate cleansing so Corrupt Purge is not available at the worst moment."
                )

            opponentName.contains("cenobite") ||
                    opponentName.contains("pinhead") ->
                disruptionProfile(
                    difficulty = 81,
                    summary =
                        "Assign the box early and solve it away from active objectives."
                )

            opponentName.contains("dredge") ->
                disruptionProfile(
                    difficulty = 80,
                    summary =
                        "Lock key lockers and plan safe movement during Nightfall."
                )

            opponentName.contains("twins") ->
                disruptionProfile(
                    difficulty = 76,
                    summary =
                        "Stay spread out and avoid giving Victor easy chained pressure."
                )

            opponentName.contains("nightmare") ||
                    opponentName.contains("freddy") ->
                disruptionProfile(
                    difficulty = 71,
                    summary =
                        "Track dream-state hazards and avoid predictable routes through snares."
                )

            opponentName.contains("demogorgon") ->
                disruptionProfile(
                    difficulty = 77,
                    summary =
                        "Close important portals and dodge Shred by changing direction late."
                )

            opponentName.contains("lich") ||
                    opponentName.contains("vecna") ->
                disruptionProfile(
                    difficulty = 88,
                    summary =
                        "Identify the active spell and use magical items to answer the correct threat."
                )

            opponentName.contains("krasue") ->
                disruptionProfile(
                    difficulty = 84,
                    summary =
                        "Adapt to her current form and avoid exposed transitions between safe structures."
                )

            else ->
                defaultOpponentProfile()
        }
    }

    private fun blinkProfile(): OpponentProfile {
        return OpponentProfile(
            difficultyScore = 94,
            recommendedPerkIds = listOf(
                "lithe",
                "windows_of_opportunity",
                "quick_and_quiet",
                "distortion"
            ),
            chaseAdvice = listOf(
                "Break line of sight before she begins charging a blink.",
                "Change direction after disappearing behind walls.",
                "Avoid straight routes through open areas.",
                "Use unpredictable pathing rather than relying only on pallets."
            ),
            objectiveAdvice = listOf(
                "Spread out so one blink chain cannot pressure several Survivors.",
                "Prioritize generators near strong sightline blockers."
            ),
            endgameAdvice = listOf(
                "Open gates carefully because she can cross the map quickly.",
                "Do not group tightly around a hooked teammate."
            ),
            warnings = listOf(
                "Traditional looping is less reliable against blink mobility.",
                "Predictable healing locations are easy to revisit."
            ),
            summary =
                "She is strongest when your route is visible and predictable."
        )
    }

    private fun rangedProfile(
        difficulty: Int,
        summary: String
    ): OpponentProfile {
        return OpponentProfile(
            difficultyScore = difficulty,
            recommendedPerkIds = listOf(
                "windows_of_opportunity",
                "lithe",
                "resilience",
                "iron_will"
            ),
            chaseAdvice = listOf(
                "Keep solid cover between yourself and the Killer.",
                "Do not vault predictably while the ranged attack is readied.",
                "Change timing at pallets instead of dropping instantly.",
                "Avoid long, exposed sightlines."
            ),
            objectiveAdvice = listOf(
                "Favor generators with nearby walls and tall cover.",
                "Move early when the terror cue approaches an exposed objective."
            ),
            endgameAdvice = listOf(
                "Use cover while opening gates.",
                "Approach rescues from protected angles."
            ),
            warnings = listOf(
                "Windows and pallets can become easy ranged-hit opportunities.",
                "Open spaces heavily favor the Killer."
            ),
            summary = summary
        )
    }

    private fun rushProfile(
        difficulty: Int,
        summary: String
    ): OpponentProfile {
        return OpponentProfile(
            difficultyScore = difficulty,
            recommendedPerkIds = listOf(
                "windows_of_opportunity",
                "lithe",
                "resilience",
                "quick_and_quiet"
            ),
            chaseAdvice = listOf(
                "Use tight corners to interrupt the power path.",
                "Avoid long straight lanes.",
                "Rotate toward cluttered tiles with several obstacles.",
                "Change direction before the Killer reaches full speed."
            ),
            objectiveAdvice = listOf(
                "Repair away from open travel lanes.",
                "Split pressure because the Killer rotates quickly."
            ),
            endgameAdvice = listOf(
                "Expect fast gate rotations.",
                "Leave unsafe rescues early rather than feeding a snowball."
            ),
            warnings = listOf(
                "Isolated Survivors can be reached very quickly.",
                "Open areas reduce your reaction time."
            ),
            summary = summary
        )
    }

    private fun stealthProfile(
        difficulty: Int,
        summary: String
    ): OpponentProfile {
        return OpponentProfile(
            difficultyScore = difficulty,
            recommendedPerkIds = listOf(
                "windows_of_opportunity",
                "spine_chill",
                "lightweight",
                "lithe"
            ),
            chaseAdvice = listOf(
                "Position near safety before the Killer fully commits.",
                "Use multiple escape routes instead of one obvious lane.",
                "Check behind you frequently and listen for subtle audio cues."
            ),
            objectiveAdvice = listOf(
                "Repair from positions with strong visibility.",
                "Avoid generators surrounded by dead zones."
            ),
            endgameAdvice = listOf(
                "Check for hidden gate pressure before committing.",
                "Do not heal in exposed areas."
            ),
            warnings = listOf(
                "The first hit may arrive with little warning.",
                "Poor pre-positioning is difficult to recover from."
            ),
            summary = summary
        )
    }

    private fun trapProfile(
        difficulty: Int,
        summary: String
    ): OpponentProfile {
        return OpponentProfile(
            difficultyScore = difficulty,
            recommendedPerkIds = listOf(
                "windows_of_opportunity",
                "kindred",
                "deja_vu",
                "lithe"
            ),
            chaseAdvice = listOf(
                "Watch narrow paths, pallets, windows, and stairs.",
                "Leave a loop when the Killer begins controlling both exits.",
                "Remember unsafe zones and rotate elsewhere."
            ),
            objectiveAdvice = listOf(
                "Pressure generators away from fortified areas.",
                "Disable or counter map objects only when it is safe."
            ),
            endgameAdvice = listOf(
                "Check exit routes before committing.",
                "Approach basement rescues carefully."
            ),
            warnings = listOf(
                "Previously safe routes may become controlled later.",
                "Basement and narrow loops can become severe traps."
            ),
            summary = summary
        )
    }

    private fun disruptionProfile(
        difficulty: Int,
        summary: String
    ): OpponentProfile {
        return OpponentProfile(
            difficultyScore = difficulty,
            recommendedPerkIds = listOf(
                "windows_of_opportunity",
                "kindred",
                "resilience",
                "adrenaline"
            ),
            chaseAdvice = listOf(
                "Identify which part of the power is limiting your route.",
                "Leave compromised loops before every option is removed.",
                "Do not repeat the same counterplay in every chase."
            ),
            objectiveAdvice = listOf(
                "Assign one teammate to interact with the Killer's secondary objective.",
                "Keep generator pressure spread across the map."
            ),
            endgameAdvice = listOf(
                "Resolve lingering power effects before a rescue.",
                "Confirm teammate positions before committing."
            ),
            warnings = listOf(
                "Ignoring the secondary objective can create team-wide pressure.",
                "Grouping often increases the power's value."
            ),
            summary = summary
        )
    }

    private fun getMapProfile(
        mapName: String
    ): MapProfile {
        return when {
            mapName.contains("midwich") ->
                MapProfile(
                    difficultyModifier = 8,
                    recommendedPerkIds = listOf(
                        "windows_of_opportunity",
                        "lithe",
                        "kindred"
                    ),
                    chaseAdvice = listOf(
                        "Use classrooms and corners to break line of sight.",
                        "Learn the stair locations before pressure begins."
                    ),
                    objectiveAdvice = listOf(
                        "Track which floor teammates occupy.",
                        "Complete difficult central generators early."
                    ),
                    endgameAdvice = listOf(
                        "Start moving toward a gate before the final generator finishes."
                    ),
                    warnings = listOf(
                        "Hallways create predictable movement.",
                        "Missing a staircase wastes critical time."
                    ),
                    summary =
                        "Midwich rewards map knowledge and repeated sightline breaks."
                )

            mapName.contains("gideon") ||
                    mapName.contains("meat plant") ->
                MapProfile(
                    difficultyModifier = 4,
                    recommendedPerkIds = listOf(
                        "windows_of_opportunity",
                        "lithe",
                        "balanced_landing"
                    ),
                    chaseAdvice = listOf(
                        "Chain pallets carefully instead of spending several at once.",
                        "Use floor changes to interrupt pursuit."
                    ),
                    objectiveAdvice = listOf(
                        "Watch generators directly above and below active chases.",
                        "Preserve routes between floors."
                    ),
                    endgameAdvice = listOf(
                        "Plan the gate route before the last generator completes."
                    ),
                    warnings = listOf(
                        "Poor pallet management creates severe dead zones."
                    ),
                    summary =
                        "Gideon offers resources, but careless use empties the map quickly."
                )

            mapName.contains("rpd") ||
                    mapName.contains("police") ->
                MapProfile(
                    difficultyModifier = 7,
                    recommendedPerkIds = listOf(
                        "windows_of_opportunity",
                        "deja_vu",
                        "kindred"
                    ),
                    chaseAdvice = listOf(
                        "Use connected rooms to break line of sight.",
                        "Avoid long halls without a nearby side room."
                    ),
                    objectiveAdvice = listOf(
                        "Use information perks to reduce navigation time.",
                        "Remember stairs and major connecting corridors."
                    ),
                    endgameAdvice = listOf(
                        "Begin moving toward a gate before the last generator finishes."
                    ),
                    warnings = listOf(
                        "Navigation mistakes cost more time than expected.",
                        "Some hallways offer very limited escape options."
                    ),
                    summary =
                        "RPD is a navigation test where information perks save time."
                )

            mapName.contains("ormond") ->
                MapProfile(
                    difficultyModifier = 3,
                    recommendedPerkIds = listOf(
                        "windows_of_opportunity",
                        "lithe",
                        "balanced_landing"
                    ),
                    chaseAdvice = listOf(
                        "Use the main building to break sightlines.",
                        "Avoid being caught far from structures."
                    ),
                    objectiveAdvice = listOf(
                        "Pressure generators on opposite sides.",
                        "Prevent a final cluster near one area."
                    ),
                    endgameAdvice = listOf(
                        "Use open visibility to track gate pressure."
                    ),
                    warnings = listOf(
                        "Large open areas favor ranged and mobile Killers."
                    ),
                    summary =
                        "Ormond provides space, but exposed crossings require timing."
                )

            mapName.contains("badham") ||
                    mapName.contains("preschool") ->
                MapProfile(
                    difficultyModifier = 5,
                    recommendedPerkIds = listOf(
                        "windows_of_opportunity",
                        "lithe",
                        "balanced_landing"
                    ),
                    chaseAdvice = listOf(
                        "Transition between buildings instead of staying at one loop.",
                        "Preserve strong windows for later."
                    ),
                    objectiveAdvice = listOf(
                        "Avoid clustering final generators near the school.",
                        "Track basement placement before risky rescues."
                    ),
                    endgameAdvice = listOf(
                        "Use buildings to conceal gate approaches."
                    ),
                    warnings = listOf(
                        "Key structures become dangerous after resources are removed."
                    ),
                    summary =
                        "Badham rewards efficient structure-to-structure routing."
                )

            else ->
                defaultMapProfile()
        }
    }

    private fun defaultOpponentProfile(): OpponentProfile {
        return OpponentProfile(
            difficultyScore = 76,
            recommendedPerkIds = listOf(
                "windows_of_opportunity",
                "lithe",
                "kindred",
                "adrenaline"
            ),
            chaseAdvice = listOf(
                "Identify your next safe tile before the chase reaches you.",
                "Avoid dropping strong pallets without forcing commitment.",
                "Break line of sight whenever possible."
            ),
            objectiveAdvice = listOf(
                "Spread the team across multiple generators.",
                "Avoid creating an easy three-generator cluster."
            ),
            endgameAdvice = listOf(
                "Confirm teammate locations before a late rescue.",
                "Leave when another rescue would only give the Killer more downs."
            ),
            warnings = listOf(
                "Do not repeat the same looping pattern.",
                "Avoid actions in locations with only one escape route."
            ),
            summary =
                "Use information, efficient routing, and disciplined resource management."
        )
    }

    private fun defaultMapProfile(): MapProfile {
        return MapProfile(
            difficultyModifier = 0,
            recommendedPerkIds = listOf(
                "windows_of_opportunity",
                "deja_vu"
            ),
            chaseAdvice = listOf(
                "Identify the strongest nearby structure before committing."
            ),
            objectiveAdvice = listOf(
                "Monitor generator placement and prevent a dangerous final cluster."
            ),
            endgameAdvice = listOf(
                "Locate both exit gates before the final generator completes."
            ),
            warnings = listOf(
                "Unknown layouts increase the value of information perks."
            ),
            summary =
                "Map awareness determines how consistently this plan performs."
        )
    }

    private fun difficultyLabel(score: Int): String {
        return when (score) {
            in 1..55 -> "Easy"
            in 56..72 -> "Medium"
            in 73..88 -> "Hard"
            else -> "Expert"
        }
    }

    private fun threatLabel(score: Int): String {
        return when (score) {
            in 1..55 -> "LOW"
            in 56..72 -> "MODERATE"
            in 73..88 -> "HIGH"
            else -> "EXTREME"
        }
    }

    private val generalRecommendedPerkIds = listOf(
        "windows_of_opportunity",
        "lithe",
        "kindred",
        "deja_vu",
        "adrenaline",
        "resilience",
        "quick_and_quiet"
    )

    private val generalObjectiveAdvice = listOf(
        "Repair separate generators so one chase does not interrupt the team.",
        "Track the final three generators before endgame."
    )

    private val generalEndgameAdvice = listOf(
        "Heal before the final rescue when time allows.",
        "Avoid giving the Killer unnecessary late-game trades."
    )

    private data class OpponentProfile(
        val difficultyScore: Int,
        val recommendedPerkIds: List<String>,
        val chaseAdvice: List<String>,
        val objectiveAdvice: List<String>,
        val endgameAdvice: List<String>,
        val warnings: List<String>,
        val summary: String
    )

    private data class MapProfile(
        val difficultyModifier: Int,
        val recommendedPerkIds: List<String>,
        val chaseAdvice: List<String>,
        val objectiveAdvice: List<String>,
        val endgameAdvice: List<String>,
        val warnings: List<String>,
        val summary: String
    )
}