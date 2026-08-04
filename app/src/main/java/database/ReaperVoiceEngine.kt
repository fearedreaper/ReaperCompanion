package com.example.reapercompanion.database

object ReaperVoiceEngine {

    fun createCoachMessage(
        opponentName: String,
        mapName: String,
        threatLevel: String,
        difficulty: String,
        score: Int
    ): String {
        val killer = normalize(opponentName)
        val map = normalize(mapName)

        val opening = openingLine(
            killer = killer,
            threatLevel = threatLevel
        )

        val matchupLine = matchupLine(
            killer = killer,
            map = map
        )

        val scoreLine = scoreLine(
            score = score,
            difficulty = difficulty
        )

        val closing = closingLine(
            killer = killer,
            threatLevel = threatLevel
        )

        return buildString {
            append(opening)
            append("\n\n")
            append(matchupLine)
            append("\n\n")
            append(scoreLine)
            append("\n\n")
            append(closing)
        }
    }

    private fun openingLine(
        killer: String,
        threatLevel: String
    ): String {
        return when {
            killer.contains("nurse") ->
                "Do not challenge her where she is strongest. Every broken sightline forces another prediction."

            killer.contains("blight") ->
                "Speed is only dangerous when the route stays clean. Break his rhythm and force bad angles."

            killer.contains("huntress") ->
                "The hatchet is not the threat until you give her a clear lane. Make every throw uncertain."

            killer.contains("spirit") ->
                "She wins by reading panic. Change your movement, hide your intent, and never repeat the same answer."

            killer.contains("trapper") ->
                "His power is not the trap itself. It is convincing you that every path is unsafe."

            killer.contains("wraith") ->
                "The chase begins before he uncloaks. Your position decides whether you survive the first hit."

            killer.contains("ghost face") ->
                "Do not let him watch for free. Find him first, break the stalk, and force a normal chase."

            killer.contains("xenomorph") ->
                "Respect the tail, control the tunnels, and make every turret placement matter."

            killer.contains("doctor") ->
                "Do not wait for the shock to remove your options. Leave early and keep control of the route."

            killer.contains("plague") ->
                "Cleansing is a team decision, not a personal reset. One bad fountain can decide the match."

            threatLevel.equals(
                other = "EXTREME",
                ignoreCase = true
            ) ->
                "This matchup punishes hesitation. Every rotation needs a purpose."

            threatLevel.equals(
                other = "HIGH",
                ignoreCase = true
            ) ->
                "This Killer can take control quickly, but disciplined decisions will slow the pressure."

            else ->
                "Stay patient. The Killer only controls the match when your team starts making rushed decisions."
        }
    }

    private fun matchupLine(
        killer: String,
        map: String
    ): String {
        return when {
            killer.contains("nurse") &&
                    map.contains("midwich") ->
                "Use classrooms, corners, and floor changes. Long hallways are invitations for clean blink predictions."

            killer.contains("nurse") &&
                    map.contains("ormond") ->
                "The lodge is your strongest shield. Cross open snow only when her attention is committed elsewhere."

            killer.contains("huntress") &&
                    map.contains("ormond") ->
                "Ormond gives her long sightlines. Move from cover to cover and never stand exposed at a generator."

            killer.contains("trapper") &&
                    (
                            map.contains("gideon") ||
                                    map.contains("meat plant")
                            ) ->
                "Gideon turns every staircase and pallet corridor into a possible trap route. Slow down before narrow transitions."

            killer.contains("blight") &&
                    map.contains("rpd") ->
                "RPD can disrupt rushes, but the long halls become highways. Force him through rooms and doorways."

            killer.contains("spirit") &&
                    map.contains("midwich") ->
                "Midwich amplifies sound. Heal away from objectives and mix walking with sudden direction changes."

            killer.contains("ghost face") &&
                    map.contains("rpd") ->
                "Every doorway can become a stalking angle. Keep the camera moving and never sit exposed in a long corridor."

            killer.contains("doctor") &&
                    (
                            map.contains("gideon") ||
                                    map.contains("meat plant")
                            ) ->
                "Gideon's narrow loops favor shock timing. Pre-drop when necessary and leave before the route disappears."

            map.contains("midwich") ->
                "Midwich rewards players who know the stairs and break sightlines before committing to hallways."

            map.contains("rpd") ||
                    map.contains("police") ->
                "RPD is a navigation test. Information and route discipline matter as much as mechanical skill."

            map.contains("ormond") ->
                "Ormond gives space, but exposed crossings are dangerous. Plan the next structure before leaving cover."

            map.contains("gideon") ||
                    map.contains("meat plant") ->
                "Gideon offers many resources, but wasting two pallets in one chase can destroy the next player's route."

            map.contains("badham") ||
                    map.contains("preschool") ->
                "Badham rewards structure-to-structure movement. Do not stay trapped at one building after its resources are gone."

            else ->
                "Use the map to deny the Killer's strongest approach. Safe positioning before contact is more valuable than panic afterward."
        }
    }

    private fun scoreLine(
        score: Int,
        difficulty: String
    ): String {
        val safeScore = score.coerceIn(0, 100)

        return when (safeScore) {
            in 90..100 ->
                "Reaper Score: $safeScore. This plan is highly reliable, but execution still matters. Difficulty: $difficulty."

            in 80..89 ->
                "Reaper Score: $safeScore. The matchup is manageable with strong positioning and clean teamwork. Difficulty: $difficulty."

            in 70..79 ->
                "Reaper Score: $safeScore. The plan is solid, but mistakes will be punished quickly. Difficulty: $difficulty."

            else ->
                "Reaper Score: $safeScore. Expect a difficult match and prioritize consistency over risky plays. Difficulty: $difficulty."
        }
    }

    private fun closingLine(
        killer: String,
        threatLevel: String
    ): String {
        return when {
            killer.contains("nurse") ->
                "Force uncertainty. She cannot blink perfectly when she does not know where you committed."

            killer.contains("blight") ->
                "Make him turn. Every correction costs momentum."

            killer.contains("huntress") ->
                "Cover first. Distance second. Predictable vaults last."

            killer.contains("spirit") ->
                "Silence your route, change your rhythm, and make every phase a guess."

            killer.contains("trapper") ->
                "Check the ground, remember the setup, and refuse to enter his strongest territory."

            threatLevel.equals(
                other = "EXTREME",
                ignoreCase = true
            ) ->
                "Do not chase hero plays. Win the match through clean decisions."

            threatLevel.equals(
                other = "HIGH",
                ignoreCase = true
            ) ->
                "Stay spread, preserve resources, and do not give the Killer free momentum."

            else ->
                "Play the plan, protect the team, and make the Killer earn every hook."
        }
    }

    private fun normalize(
        value: String
    ): String {
        return value
            .trim()
            .lowercase()
            .replace("ō", "o")
            .replace("–", "-")
    }
}