package com.example.reapercompanion.database

data class ReaperMap(
    val name: String,
    val difficultyModifier: Int,
    val recommendedPerkIds: List<String>,
    val chaseAdvice: List<String>,
    val objectiveAdvice: List<String>,
    val endgameAdvice: List<String>,
    val warnings: List<String>,
    val summary: String
)

private enum class ReaperMapType {
    OPEN,
    INDOOR,
    STRUCTURE_HEAVY,
    MULTI_LEVEL,
    BALANCED
}

object MapDatabase {

    private val maps = mutableMapOf<String, ReaperMap>()

    fun add(map: ReaperMap) {
        maps[normalize(map.name)] = map
    }

    fun get(name: String): ReaperMap? {
        return maps[normalize(name)]
    }

    fun getAll(): List<ReaperMap> {
        return maps.values.sortedBy { it.name }
    }

    fun getAllNames(): List<String> {
        return getAll().map { it.name }
    }

    private fun normalize(value: String): String {
        return value
            .trim()
            .lowercase()
            .replace("ō", "o")
            .replace("’", "'")
            .replace("–", "-")
    }

    private fun addMap(
        name: String,
        type: ReaperMapType,
        difficultyModifier: Int
    ) {
        val profile = profileFor(type)

        add(
            ReaperMap(
                name = name,
                difficultyModifier = difficultyModifier,
                recommendedPerkIds = profile.recommendedPerkIds,
                chaseAdvice = profile.chaseAdvice,
                objectiveAdvice = profile.objectiveAdvice,
                endgameAdvice = profile.endgameAdvice,
                warnings = profile.warnings,
                summary = profile.summary
            )
        )
    }

    private fun profileFor(
        type: ReaperMapType
    ): ReaperMap {
        return when (type) {
            ReaperMapType.OPEN ->
                ReaperMap(
                    name = "",
                    difficultyModifier = 4,
                    recommendedPerkIds = listOf(
                        "windows_of_opportunity",
                        "lithe",
                        "deja_vu"
                    ),
                    chaseAdvice = listOf(
                        "Plan the next structure before crossing open ground.",
                        "Use solid cover to break line of sight.",
                        "Avoid long straight routes against ranged or mobility Killers."
                    ),
                    objectiveAdvice = listOf(
                        "Complete exposed generators while the Killer is occupied.",
                        "Prevent a dangerous final three-generator cluster."
                    ),
                    endgameAdvice = listOf(
                        "Locate both exit gates before the final generator completes."
                    ),
                    warnings = listOf(
                        "Open sightlines reduce reaction time.",
                        "Outer areas can become severe dead zones."
                    ),
                    summary =
                        "An open map that rewards early routing and disciplined generator pressure."
                )

            ReaperMapType.INDOOR ->
                ReaperMap(
                    name = "",
                    difficultyModifier = 7,
                    recommendedPerkIds = listOf(
                        "windows_of_opportunity",
                        "lithe",
                        "kindred"
                    ),
                    chaseAdvice = listOf(
                        "Use rooms and corners to break line of sight.",
                        "Memorize stairs, corridors, and major landmarks.",
                        "Avoid long hallways without a nearby side route."
                    ),
                    objectiveAdvice = listOf(
                        "Track generators by landmark rooms.",
                        "Split the team across separate sections."
                    ),
                    endgameAdvice = listOf(
                        "Begin moving toward an exit before the final generator completes."
                    ),
                    warnings = listOf(
                        "Navigation mistakes cost critical time.",
                        "Stealth Killers gain value from blocked visibility."
                    ),
                    summary =
                        "An indoor map where route memory and landmark knowledge are essential."
                )

            ReaperMapType.STRUCTURE_HEAVY ->
                ReaperMap(
                    name = "",
                    difficultyModifier = 5,
                    recommendedPerkIds = listOf(
                        "windows_of_opportunity",
                        "lithe",
                        "balanced_landing"
                    ),
                    chaseAdvice = listOf(
                        "Connect major structures instead of repeating one loop.",
                        "Leave a building early when both exits become controlled.",
                        "Preserve strong windows and pallets for later chases."
                    ),
                    objectiveAdvice = listOf(
                        "Complete difficult building generators early.",
                        "Spread generator progress between major structures."
                    ),
                    endgameAdvice = listOf(
                        "Use buildings to conceal gate and rescue approaches."
                    ),
                    warnings = listOf(
                        "Buildings can become traps after key resources are spent.",
                        "Routes between structures may be exposed."
                    ),
                    summary =
                        "A structure-heavy map that rewards efficient building-to-building rotations."
                )

            ReaperMapType.MULTI_LEVEL ->
                ReaperMap(
                    name = "",
                    difficultyModifier = 6,
                    recommendedPerkIds = listOf(
                        "windows_of_opportunity",
                        "balanced_landing",
                        "kindred"
                    ),
                    chaseAdvice = listOf(
                        "Use elevation changes to interrupt pursuit.",
                        "Learn the safest stairs, drops, and floor connections.",
                        "Avoid becoming trapped on the wrong level."
                    ),
                    objectiveAdvice = listOf(
                        "Track which floor teammates occupy.",
                        "Complete difficult central generators early."
                    ),
                    endgameAdvice = listOf(
                        "Leave lower or upper sections before endgame pressure closes in."
                    ),
                    warnings = listOf(
                        "Wrong-level rotations waste valuable time.",
                        "Narrow stair routes can be controlled easily."
                    ),
                    summary =
                        "A multi-level map where vertical navigation determines the match."
                )

            ReaperMapType.BALANCED ->
                ReaperMap(
                    name = "",
                    difficultyModifier = 3,
                    recommendedPerkIds = listOf(
                        "windows_of_opportunity",
                        "lithe",
                        "deja_vu"
                    ),
                    chaseAdvice = listOf(
                        "Identify the next safe tile before leaving the current one.",
                        "Break line of sight whenever possible.",
                        "Preserve the strongest central resources."
                    ),
                    objectiveAdvice = listOf(
                        "Spread generator pressure across the map.",
                        "Track the final three generators before endgame."
                    ),
                    endgameAdvice = listOf(
                        "Confirm gate locations before the final generator completes."
                    ),
                    warnings = listOf(
                        "Repeated routes become predictable.",
                        "Careless pallet use creates dead zones."
                    ),
                    summary =
                        "A balanced map that rewards awareness, efficient routing, and resource discipline."
                )
        }
    }

    init {
        addMap("Coal Tower", ReaperMapType.BALANCED, 2)
        addMap("Groaning Storehouse", ReaperMapType.STRUCTURE_HEAVY, 3)
        addMap("Ironworks of Misery", ReaperMapType.STRUCTURE_HEAVY, 4)
        addMap("Shelter Woods", ReaperMapType.OPEN, 3)
        addMap("Suffocation Pit", ReaperMapType.BALANCED, 4)

        addMap("Azarov's Resting Place", ReaperMapType.OPEN, 5)
        addMap("Blood Lodge", ReaperMapType.OPEN, 3)
        addMap("Gas Heaven", ReaperMapType.OPEN, 3)
        addMap("Wreckers' Yard", ReaperMapType.BALANCED, 2)
        addMap("Wretched Shop", ReaperMapType.STRUCTURE_HEAVY, 3)

        addMap("Fractured Cowshed", ReaperMapType.OPEN, 4)
        addMap("Rancid Abattoir", ReaperMapType.STRUCTURE_HEAVY, 4)
        addMap("Rotten Fields", ReaperMapType.OPEN, 5)
        addMap("The Thompson House", ReaperMapType.STRUCTURE_HEAVY, 4)
        addMap("Torment Creek", ReaperMapType.BALANCED, 3)

        addMap("Disturbed Ward", ReaperMapType.STRUCTURE_HEAVY, 5)
        addMap("Father Campbell's Chapel", ReaperMapType.STRUCTURE_HEAVY, 4)
        addMap("Lampkin Lane", ReaperMapType.STRUCTURE_HEAVY, 5)
        addMap("The Pale Rose", ReaperMapType.OPEN, 5)
        addMap("Grim Pantry", ReaperMapType.STRUCTURE_HEAVY, 5)
        addMap("Treatment Theatre", ReaperMapType.INDOOR, 6)

        addMap("Mother's Dwelling", ReaperMapType.OPEN, 5)
        addMap("The Temple of Purgation", ReaperMapType.MULTI_LEVEL, 5)
        addMap("Badham Preschool", ReaperMapType.STRUCTURE_HEAVY, 5)
        addMap("The Game", ReaperMapType.MULTI_LEVEL, 4)
        addMap("Family Residence", ReaperMapType.STRUCTURE_HEAVY, 4)
        addMap("Sanctum of Wrath", ReaperMapType.MULTI_LEVEL, 5)

        addMap("Mount Ormond Resort", ReaperMapType.STRUCTURE_HEAVY, 3)
        addMap("Ormond Lake Mine", ReaperMapType.MULTI_LEVEL, 4)
        addMap("The Underground Complex", ReaperMapType.INDOOR, 6)
        addMap("Dead Dawg Saloon", ReaperMapType.STRUCTURE_HEAVY, 3)
        addMap("Midwich Elementary School", ReaperMapType.INDOOR, 8)

        addMap("Raccoon City Police Station", ReaperMapType.INDOOR, 7)
        addMap("Raccoon City Police Station East Wing", ReaperMapType.INDOOR, 7)
        addMap("Raccoon City Police Station West Wing", ReaperMapType.INDOOR, 7)

        addMap("Eyrie of Crows", ReaperMapType.OPEN, 4)
        addMap("Dead Sands", ReaperMapType.OPEN, 4)

        addMap("Garden of Joy", ReaperMapType.STRUCTURE_HEAVY, 5)
        addMap("Greenville Square", ReaperMapType.STRUCTURE_HEAVY, 4)
        addMap("Freddy Fazbear's Pizza", ReaperMapType.INDOOR, 6)
        addMap("Fallen Refuge", ReaperMapType.STRUCTURE_HEAVY, 5)

        addMap("The Shattered Square", ReaperMapType.STRUCTURE_HEAVY, 4)
        addMap("Forgotten Ruins", ReaperMapType.MULTI_LEVEL, 6)

        addMap("Toba Landing", ReaperMapType.OPEN, 4)
        addMap("Nostromo Wreckage", ReaperMapType.BALANCED, 5)

        addMap("Trickster's Delusion", ReaperMapType.STRUCTURE_HEAVY, 5)
    }
}