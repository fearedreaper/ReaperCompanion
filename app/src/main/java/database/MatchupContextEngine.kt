package com.example.reapercompanion.database

data class MatchupContextAdvice(
    val title: String,
    val killerMapSummary: String,
    val chaseAdjustments: List<String>,
    val objectiveAdjustments: List<String>,
    val endgameAdjustments: List<String>,
    val priorityWarnings: List<String>,
    val scoreModifier: Int
)

object MatchupContextEngine {

    private fun normalize(
        value: String
    ): String {
        return value
            .trim()
            .lowercase()
            .replace("ō", "o")
            .replace("–", "-")
    }

    fun generateContextAdvice(
        opponentName: String,
        mapName: String
    ): MatchupContextAdvice {
        val killer = normalize(opponentName)
        val map = normalize(mapName)

        return when {

            killer.contains("nurse") &&
                    map.contains("midwich") ->
                MatchupContextAdvice(
                    title = "Nurse on Midwich",
                    killerMapSummary =
                        "Midwich gives the Nurse many short blink routes, but classrooms and corners also create repeated opportunities to break line of sight.",
                    chaseAdjustments = listOf(
                        "Move through classrooms instead of staying in long hallways.",
                        "Change floors after breaking line of sight when stairs are nearby.",
                        "Avoid waiting at pallets because blink pressure ignores traditional loop safety.",
                        "Double back only after the Nurse loses visual information."
                    ),
                    objectiveAdjustments = listOf(
                        "Prioritize generators inside rooms with several exits.",
                        "Avoid repairing in long hallways with no nearby cover.",
                        "Track which floor the Nurse is pressuring before committing to a rescue."
                    ),
                    endgameAdjustments = listOf(
                        "Begin moving toward an exit gate before the final generator completes.",
                        "Do not group tightly near hooks because the Nurse can blink between targets quickly."
                    ),
                    priorityWarnings = listOf(
                        "Long hallways strongly favor blink prediction.",
                        "Poor staircase knowledge can trap Survivors on the wrong floor."
                    ),
                    scoreModifier = -8
                )

            killer.contains("nurse") &&
                    map.contains("ormond") ->
                MatchupContextAdvice(
                    title = "Nurse on Ormond",
                    killerMapSummary =
                        "Ormond gives Survivors more space, but open areas make blink paths easier to predict unless the main building is used well.",
                    chaseAdjustments = listOf(
                        "Rotate toward the main building before the chase begins.",
                        "Use elevation changes to disrupt blink timing.",
                        "Cross open snowfields only when the Nurse is committed elsewhere.",
                        "Break line of sight with exterior walls before changing direction."
                    ),
                    objectiveAdjustments = listOf(
                        "Spread generators across both sides of the map.",
                        "Avoid clustering the team around the main building.",
                        "Repair exposed generators only when the Nurse is far away."
                    ),
                    endgameAdjustments = listOf(
                        "Use the wide map to separate gate pressure.",
                        "Do not heal directly beside an exit switch."
                    ),
                    priorityWarnings = listOf(
                        "Open snowfields provide very little protection.",
                        "Long-distance blink rotations can punish isolated Survivors."
                    ),
                    scoreModifier = -5
                )

            killer.contains("huntress") &&
                    map.contains("ormond") ->
                MatchupContextAdvice(
                    title = "Huntress on Ormond",
                    killerMapSummary =
                        "Ormond's open sightlines increase hatchet pressure, while the main building provides the safest cover.",
                    chaseAdjustments = listOf(
                        "Route toward tall cover before the Huntress begins winding up.",
                        "Avoid predictable vaults in open structures.",
                        "Use the main building to deny long throwing lanes.",
                        "Change direction after moving behind solid cover."
                    ),
                    objectiveAdjustments = listOf(
                        "Prioritize generators near the main building and tall obstacles.",
                        "Avoid repairing exposed edge generators without an escape plan.",
                        "Spread out so one hatchet angle cannot pressure multiple Survivors."
                    ),
                    endgameAdjustments = listOf(
                        "Use nearby cover while opening gates.",
                        "Approach rescues from angles that block direct hatchet paths."
                    ),
                    priorityWarnings = listOf(
                        "Open snowfields heavily favor ranged attacks.",
                        "Standing still at gate switches creates easy hatchet opportunities."
                    ),
                    scoreModifier = -6
                )

            killer.contains("trapper") &&
                    (
                            map.contains("gideon") ||
                                    map.contains("meat plant")
                            ) ->
                MatchupContextAdvice(
                    title = "Trapper on Gideon",
                    killerMapSummary =
                        "Gideon's narrow pallet routes and stairways give the Trapper strong control over predictable movement.",
                    chaseAdjustments = listOf(
                        "Check the ground before entering pallet corridors.",
                        "Do not assume stairways are safe.",
                        "Leave heavily trapped loops instead of trying to force them.",
                        "Remember which floor contains active traps."
                    ),
                    objectiveAdjustments = listOf(
                        "Pressure generators away from the basement side.",
                        "Disarm safe traps when the Killer is occupied elsewhere.",
                        "Avoid allowing several final generators to remain on one floor."
                    ),
                    endgameAdjustments = listOf(
                        "Check both exit gate paths for traps.",
                        "Approach basement rescues slowly and deliberately."
                    ),
                    priorityWarnings = listOf(
                        "Dropped pallets can hide traps.",
                        "Basement access may become extremely dangerous."
                    ),
                    scoreModifier = -4
                )

            killer.contains("blight") &&
                    map.contains("rpd") ->
                MatchupContextAdvice(
                    title = "Blight on RPD",
                    killerMapSummary =
                        "RPD's tight rooms can interrupt rushes, but long corridors give the Blight powerful travel lanes.",
                    chaseAdjustments = listOf(
                        "Move through connected rooms instead of long hallways.",
                        "Use doorways to force awkward collision angles.",
                        "Change floors when the Blight begins controlling one corridor.",
                        "Avoid running straight through central halls."
                    ),
                    objectiveAdjustments = listOf(
                        "Repair generators inside rooms with multiple exits.",
                        "Use information perks to reduce navigation mistakes.",
                        "Avoid grouping around central objectives."
                    ),
                    endgameAdjustments = listOf(
                        "Move toward gates before the final generator completes.",
                        "Expect rapid rotations through major hallways."
                    ),
                    priorityWarnings = listOf(
                        "Long corridors strongly favor rush attacks.",
                        "Navigation mistakes can leave you isolated."
                    ),
                    scoreModifier = -5
                )

            killer.contains("spirit") &&
                    map.contains("midwich") ->
                MatchupContextAdvice(
                    title = "Spirit on Midwich",
                    killerMapSummary =
                        "Midwich amplifies sound tracking because movement is concentrated through hallways and classrooms.",
                    chaseAdjustments = listOf(
                        "Mix walking and running after entering classrooms.",
                        "Change floors after creating misleading scratch marks.",
                        "Avoid injured movement through long hallways.",
                        "Do not remain stationary at obvious pallet positions."
                    ),
                    objectiveAdjustments = listOf(
                        "Heal away from generators when possible.",
                        "Avoid stacking multiple Survivors in one classroom.",
                        "Choose objectives with several escape routes."
                    ),
                    endgameAdjustments = listOf(
                        "Use false scratch trails before approaching gates.",
                        "Stay spread during rescues."
                    ),
                    priorityWarnings = listOf(
                        "Injured breathing is easier to track indoors.",
                        "Repeated hallway routes become predictable."
                    ),
                    scoreModifier = -6
                )

            killer.contains("wraith") &&
                    map.contains("badham") ->
                MatchupContextAdvice(
                    title = "Wraith on Badham",
                    killerMapSummary =
                        "Badham's buildings provide strong escape routes, but the Wraith can approach hidden through obstructed sightlines.",
                    chaseAdjustments = listOf(
                        "Position near a building before repairing.",
                        "Use windows immediately after the Wraith begins uncloaking.",
                        "Avoid being caught between structures.",
                        "Keep scanning entrances and corners."
                    ),
                    objectiveAdjustments = listOf(
                        "Repair from angles that allow you to watch approach routes.",
                        "Avoid isolated generators in dead zones.",
                        "Spread objectives between buildings."
                    ),
                    endgameAdjustments = listOf(
                        "Check for a cloaked Wraith near gate switches.",
                        "Use structures to conceal rescue approaches."
                    ),
                    priorityWarnings = listOf(
                        "Blocked sightlines make stealth approaches harder to notice.",
                        "Open spaces between buildings are dangerous."
                    ),
                    scoreModifier = -2
                )

            killer.contains("xenomorph") &&
                    map.contains("gideon") ->
                MatchupContextAdvice(
                    title = "Xenomorph on Gideon",
                    killerMapSummary =
                        "Gideon's low loops and narrow corridors increase tail attack pressure, while flame turret placement becomes especially important.",
                    chaseAdjustments = listOf(
                        "Avoid crouching behind low pallets when the tail is ready.",
                        "Use floor transitions to create distance.",
                        "Pre-plan routes between turret locations.",
                        "Do not greed narrow pallet loops."
                    ),
                    objectiveAdjustments = listOf(
                        "Place turrets near active generators.",
                        "Protect strong routes between floors.",
                        "Repair away from tunnels when possible."
                    ),
                    endgameAdjustments = listOf(
                        "Move turrets toward likely gate approaches.",
                        "Avoid clustering near narrow exits."
                    ),
                    priorityWarnings = listOf(
                        "Low cover provides limited protection from the tail.",
                        "Tunnel access allows fast floor-to-floor pressure."
                    ),
                    scoreModifier = -5
                )

            killer.contains("ghost face") &&
                    map.contains("rpd") ->
                MatchupContextAdvice(
                    title = "Ghost Face on RPD",
                    killerMapSummary =
                        "RPD's corners and obstructed rooms create many stalking angles and surprise approaches.",
                    chaseAdjustments = listOf(
                        "Check doorways before entering long corridors.",
                        "Use camera movement to reveal stalking attempts.",
                        "Do not remain visible through windows for extended periods.",
                        "Rotate through rooms with multiple exits."
                    ),
                    objectiveAdjustments = listOf(
                        "Repair from positions that let you monitor entrances.",
                        "Avoid isolated central generators.",
                        "Move after revealing Ghost Face nearby."
                    ),
                    endgameAdjustments = listOf(
                        "Check corners before touching gate switches.",
                        "Do not approach hooks through a single predictable corridor."
                    ),
                    priorityWarnings = listOf(
                        "Dark corners make stalking harder to notice.",
                        "Long hallways can expose Survivors for extended periods."
                    ),
                    scoreModifier = -4
                )

            killer.contains("doctor") &&
                    map.contains("gideon") ->
                MatchupContextAdvice(
                    title = "Doctor on Gideon",
                    killerMapSummary =
                        "Gideon's narrow loops make shock timing easier, forcing Survivors to leave pallets and windows earlier.",
                    chaseAdjustments = listOf(
                        "Pre-drop when a shock would otherwise deny the pallet.",
                        "Change floors before madness pressure becomes overwhelming.",
                        "Avoid waiting directly beside windows.",
                        "Rotate between pallet zones instead of staying at one tile."
                    ),
                    objectiveAdjustments = listOf(
                        "Spread out to reduce Static Blast value.",
                        "Repair on different floors.",
                        "Lower madness before high-risk rescues."
                    ),
                    endgameAdjustments = listOf(
                        "Reset madness before opening gates.",
                        "Avoid stacking several Survivors near one exit."
                    ),
                    priorityWarnings = listOf(
                        "Narrow corridors simplify shock placement.",
                        "High madness can disrupt critical endgame actions."
                    ),
                    scoreModifier = -4
                )

            killer.contains("plague") &&
                    map.contains("midwich") ->
                MatchupContextAdvice(
                    title = "Plague on Midwich",
                    killerMapSummary =
                        "Midwich's narrow rooms make Corrupt Purge difficult to dodge and can spread infection quickly between objectives.",
                    chaseAdjustments = listOf(
                        "Avoid narrow hallways while Corrupt Purge is active.",
                        "Use classrooms to block projectile paths.",
                        "Do not run directly behind infected teammates.",
                        "Change floors to escape active pressure."
                    ),
                    objectiveAdjustments = listOf(
                        "Coordinate cleansing instead of cleansing individually.",
                        "Avoid infecting several generators on the same floor.",
                        "Spread the team across separate rooms."
                    ),
                    endgameAdjustments = listOf(
                        "Cleanse only when the team can handle Corrupt Purge.",
                        "Approach rescues from different directions."
                    ),
                    priorityWarnings = listOf(
                        "Indoor corridors increase projectile pressure.",
                        "Poor cleansing timing can hand the Plague a powerful endgame."
                    ),
                    scoreModifier = -5
                )

            else ->
                generateGenericContext(
                    opponentName = opponentName,
                    mapName = mapName
                )
        }
    }

    private fun generateGenericContext(
        opponentName: String,
        mapName: String
    ): MatchupContextAdvice {
        val killerType = detectKillerType(opponentName)
        val mapType = detectMapType(mapName)

        val chaseAdvice = mutableListOf<String>()
        val objectiveAdvice = mutableListOf<String>()
        val endgameAdvice = mutableListOf<String>()
        val warnings = mutableListOf<String>()

        when (killerType) {
            KillerType.RANGED -> {
                chaseAdvice +=
                    "Use solid cover and avoid predictable vault timing."
                warnings +=
                    "Open sightlines increase ranged pressure."
            }

            KillerType.MOBILITY -> {
                chaseAdvice +=
                    "Use tight geometry and avoid long straight routes."
                warnings +=
                    "Fast rotations can punish isolated Survivors."
            }

            KillerType.STEALTH -> {
                chaseAdvice +=
                    "Position near safety before the Killer reaches you."
                warnings +=
                    "The first hit may arrive with little warning."
            }

            KillerType.CONTROL -> {
                chaseAdvice +=
                    "Leave loops early when both exits become controlled."
                warnings +=
                    "Previously safe routes may become dangerous later."
            }

            KillerType.DISRUPTION -> {
                chaseAdvice +=
                    "Identify the power's secondary objective and respond early."
                warnings +=
                    "Ignoring the Killer's extra mechanic can create team-wide pressure."
            }

            KillerType.STANDARD -> {
                chaseAdvice +=
                    "Preserve strong pallets and rotate toward connected tiles."
                warnings +=
                    "Repeating the same route makes future chases predictable."
            }
        }

        when (mapType) {
            MapType.INDOOR -> {
                chaseAdvice +=
                    "Use corners and rooms to break line of sight."
                objectiveAdvice +=
                    "Remember stairs, corridors, and major connecting rooms."
                endgameAdvice +=
                    "Begin moving toward a gate before the final generator completes."
                warnings +=
                    "Navigation mistakes can waste critical time."
            }

            MapType.OPEN -> {
                chaseAdvice +=
                    "Plan crossings between structures before entering open ground."
                objectiveAdvice +=
                    "Avoid exposed generators without nearby cover."
                endgameAdvice +=
                    "Use long visibility to monitor gate pressure."
                warnings +=
                    "Open areas reduce your options during chase."
            }

            MapType.PALLET_HEAVY -> {
                chaseAdvice +=
                    "Preserve pallets and avoid spending several in one chase."
                objectiveAdvice +=
                    "Track which areas are becoming dead zones."
                endgameAdvice +=
                    "Plan routes through remaining resources before the final generator."
                warnings +=
                    "Poor pallet management weakens the entire team."
            }

            MapType.BUILDING_HEAVY -> {
                chaseAdvice +=
                    "Connect buildings rather than staying at one structure."
                objectiveAdvice +=
                    "Spread generators between major buildings."
                endgameAdvice +=
                    "Use structures to conceal gate and rescue approaches."
                warnings +=
                    "Routes between buildings may become exposed."
            }

            MapType.UNKNOWN -> {
                chaseAdvice +=
                    "Identify the strongest nearby structure before committing."
                objectiveAdvice +=
                    "Track generator placement and avoid a dangerous final cluster."
                endgameAdvice +=
                    "Locate both exit gates before endgame."
                warnings +=
                    "Unknown layouts increase the value of information perks."
            }
        }

        return MatchupContextAdvice(
            title = "$opponentName on $mapName",
            killerMapSummary =
                "This matchup combines ${killerType.label} Killer pressure with ${mapType.label} map geometry.",
            chaseAdjustments = chaseAdvice.distinct(),
            objectiveAdjustments = objectiveAdvice.distinct(),
            endgameAdjustments = endgameAdvice.distinct(),
            priorityWarnings = warnings.distinct(),
            scoreModifier = genericScoreModifier(
                killerType = killerType,
                mapType = mapType
            )
        )
    }

    private fun detectKillerType(
        opponentName: String
    ): KillerType {
        return when {
            opponentName.containsAny(
                "huntress",
                "deathslinger",
                "trickster",
                "artist",
                "executioner",
                "nemesis",
                "unknown",
                "animatronic",
                "first"
            ) -> KillerType.RANGED

            opponentName.containsAny(
                "blight",
                "hillbilly",
                "oni",
                "mastermind",
                "houndmaster",
                "slasher"
            ) -> KillerType.MOBILITY

            opponentName.containsAny(
                "wraith",
                "spirit",
                "ghost face",
                "shape",
                "pig",
                "onryo",
                "good guy",
                "dark lord",
                "ghoul"
            ) -> KillerType.STEALTH

            opponentName.containsAny(
                "trapper",
                "hag",
                "knight",
                "skull merchant",
                "singularity",
                "xenomorph"
            ) -> KillerType.CONTROL

            opponentName.containsAny(
                "doctor",
                "clown",
                "legion",
                "plague",
                "cenobite",
                "dredge",
                "twins",
                "nightmare",
                "demogorgon",
                "lich",
                "krasue"
            ) -> KillerType.DISRUPTION

            else -> KillerType.STANDARD
        }
    }

    private fun detectMapType(
        mapName: String
    ): MapType {
        return when {
            mapName.containsAny(
                "midwich",
                "rpd",
                "police",
                "gideon",
                "meat plant",
                "lery",
                "hawkins"
            ) -> MapType.INDOOR

            mapName.containsAny(
                "ormond",
                "coldwind",
                "red forest",
                "nostromo",
                "greenville"
            ) -> MapType.OPEN

            mapName.containsAny(
                "gideon",
                "game",
                "shattered square"
            ) -> MapType.PALLET_HEAVY

            mapName.containsAny(
                "badham",
                "preschool",
                "macmillan",
                "yamaoka"
            ) -> MapType.BUILDING_HEAVY

            else -> MapType.UNKNOWN
        }
    }

    private fun genericScoreModifier(
        killerType: KillerType,
        mapType: MapType
    ): Int {
        var modifier = 0

        if (
            killerType == KillerType.RANGED &&
            mapType == MapType.OPEN
        ) {
            modifier -= 5
        }

        if (
            killerType == KillerType.MOBILITY &&
            mapType == MapType.OPEN
        ) {
            modifier -= 4
        }

        if (
            killerType == KillerType.STEALTH &&
            mapType == MapType.INDOOR
        ) {
            modifier -= 4
        }

        if (
            killerType == KillerType.CONTROL &&
            mapType == MapType.PALLET_HEAVY
        ) {
            modifier -= 3
        }

        if (
            killerType == KillerType.RANGED &&
            mapType == MapType.INDOOR
        ) {
            modifier += 2
        }

        return modifier
    }

    private fun String.containsAny(
        vararg values: String
    ): Boolean {
        return values.any { value ->
            contains(value)
        }
    }

    private enum class KillerType(
        val label: String
    ) {
        RANGED("ranged"),
        MOBILITY("high-mobility"),
        STEALTH("stealth"),
        CONTROL("area-control"),
        DISRUPTION("disruption"),
        STANDARD("standard chase")
    }

    private enum class MapType(
        val label: String
    ) {
        INDOOR("indoor"),
        OPEN("open"),
        PALLET_HEAVY("pallet-heavy"),
        BUILDING_HEAVY("structure-heavy"),
        UNKNOWN("unfamiliar")
    }
}