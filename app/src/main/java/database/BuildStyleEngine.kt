package com.example.reapercompanion.database

import com.example.reapercompanion.models.BuildRecommendation
import com.example.reapercompanion.models.BuildStyle
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkCategory
import com.example.reapercompanion.models.PerkRole

object BuildStyleEngine {

    fun generateStyledBuild(
        selectedPerk: Perk,
        style: BuildStyle
    ): BuildRecommendation {
        val allPerks = when (selectedPerk.role) {
            PerkRole.SURVIVOR -> PerkDatabase.getSurvivorPerks()
            PerkRole.KILLER -> KillerPerkDatabase.allPerks
        }

        val synergyScores = mutableMapOf<String, Int>()

        PerkSynergyDatabase
            .getPartnersFor(selectedPerk.id)
            .forEach { synergy ->
                val partnerId =
                    if (synergy.perkId == selectedPerk.id) {
                        synergy.partnerPerkId
                    } else {
                        synergy.perkId
                    }

                synergyScores[partnerId] =
                    maxOf(
                        synergyScores[partnerId] ?: 0,
                        synergy.score
                    )
            }

        val candidatePerks = allPerks
            .filter { perk ->
                perk.id != selectedPerk.id
            }
            .map { perk ->
                val synergyScore = synergyScores[perk.id] ?: 0
                val styleScore = getStyleScore(
                    perk = perk,
                    style = style
                )

                ScoredPerk(
                    perk = perk,
                    totalScore = synergyScore * 10 + styleScore
                )
            }
            .sortedByDescending { scoredPerk ->
                scoredPerk.totalScore
            }

        val selectedPartners = candidatePerks
            .map { scoredPerk ->
                scoredPerk.perk
            }
            .distinctBy { perk ->
                perk.id
            }
            .take(3)

        val finalPerks = listOf(selectedPerk) + selectedPartners

        val alternatives = candidatePerks
            .map { scoredPerk ->
                scoredPerk.perk
            }
            .filter { perk ->
                finalPerks.none { selected ->
                    selected.id == perk.id
                }
            }
            .distinctBy { perk ->
                perk.id
            }
            .take(4)

        val synergyAverage = selectedPartners
            .map { perk ->
                synergyScores[perk.id] ?: 5
            }
            .average()
            .times(10)
            .toInt()

        val styleBonus = when (style) {
            BuildStyle.BALANCED -> 4
            BuildStyle.AGGRESSIVE -> 3
            BuildStyle.SAFE -> 4
            BuildStyle.BEGINNER -> 2
            BuildStyle.MEME -> 0
        }

        val buildScore = (synergyAverage + styleBonus)
            .coerceIn(75, 100)

        return BuildRecommendation(
            name = buildName(
                selectedPerk = selectedPerk,
                style = style
            ),
            goal = styleLabel(style),
            score = buildScore,
            difficulty = difficultyForStyle(style),
            perks = finalPerks,
            alternatives = alternatives,
            strengths = strengthsForStyle(
                selectedPerk = selectedPerk,
                style = style
            ),
            explanation = BuildExplanationEngine.explainBuild(
                selectedPerk = selectedPerk,
                perks = finalPerks,
                style = style
            )
        )
    }

    private fun getStyleScore(
        perk: Perk,
        style: BuildStyle
    ): Int {
        return when (style) {
            BuildStyle.BALANCED ->
                balancedScore(perk)

            BuildStyle.AGGRESSIVE ->
                aggressiveScore(perk)

            BuildStyle.SAFE ->
                safeScore(perk)

            BuildStyle.BEGINNER ->
                beginnerScore(perk)

            BuildStyle.MEME ->
                memeScore(perk)
        }
    }

    private fun balancedScore(
        perk: Perk
    ): Int {
        return when (perk.category) {
            PerkCategory.INFORMATION -> 18
            PerkCategory.SUPPORT -> 17
            PerkCategory.GENERATOR -> 16
            PerkCategory.CHASE -> 15
            PerkCategory.HEALING -> 14
            PerkCategory.STEALTH -> 13
            PerkCategory.ENDGAME -> 12
            PerkCategory.SECOND_CHANCE -> 12
            PerkCategory.MEME -> 6
        }
    }

    private fun aggressiveScore(
        perk: Perk
    ): Int {
        return when (perk.category) {
            PerkCategory.CHASE -> 24
            PerkCategory.GENERATOR -> 20
            PerkCategory.INFORMATION -> 18
            PerkCategory.ENDGAME -> 17
            PerkCategory.SECOND_CHANCE -> 15
            PerkCategory.STEALTH -> 13
            PerkCategory.SUPPORT -> 10
            PerkCategory.HEALING -> 9
            PerkCategory.MEME -> 8
        }
    }

    private fun safeScore(
        perk: Perk
    ): Int {
        return when (perk.category) {
            PerkCategory.SECOND_CHANCE -> 24
            PerkCategory.HEALING -> 22
            PerkCategory.INFORMATION -> 20
            PerkCategory.STEALTH -> 18
            PerkCategory.SUPPORT -> 17
            PerkCategory.CHASE -> 14
            PerkCategory.GENERATOR -> 12
            PerkCategory.ENDGAME -> 12
            PerkCategory.MEME -> 5
        }
    }

    private fun beginnerScore(
        perk: Perk
    ): Int {
        val baseGameBonus =
            if (perk.isBaseGame) {
                10
            } else {
                0
            }

        val categoryScore = when (perk.category) {
            PerkCategory.INFORMATION -> 22
            PerkCategory.GENERATOR -> 20
            PerkCategory.SUPPORT -> 18
            PerkCategory.HEALING -> 17
            PerkCategory.CHASE -> 15
            PerkCategory.STEALTH -> 14
            PerkCategory.ENDGAME -> 12
            PerkCategory.SECOND_CHANCE -> 11
            PerkCategory.MEME -> 4
        }

        return categoryScore + baseGameBonus
    }

    private fun memeScore(
        perk: Perk
    ): Int {
        return when (perk.category) {
            PerkCategory.MEME -> 30
            PerkCategory.STEALTH -> 21
            PerkCategory.ENDGAME -> 19
            PerkCategory.CHASE -> 17
            PerkCategory.INFORMATION -> 14
            PerkCategory.SECOND_CHANCE -> 13
            PerkCategory.SUPPORT -> 11
            PerkCategory.GENERATOR -> 8
            PerkCategory.HEALING -> 7
        }
    }

    private fun buildName(
        selectedPerk: Perk,
        style: BuildStyle
    ): String {
        return when (style) {
            BuildStyle.BALANCED ->
                "Balanced ${selectedPerk.name} Build"

            BuildStyle.AGGRESSIVE ->
                "Aggressive ${selectedPerk.name} Build"

            BuildStyle.SAFE ->
                "Safe ${selectedPerk.name} Build"

            BuildStyle.BEGINNER ->
                "Beginner ${selectedPerk.name} Build"

            BuildStyle.MEME ->
                "Chaos ${selectedPerk.name} Build"
        }
    }

    private fun styleLabel(
        style: BuildStyle
    ): String {
        return when (style) {
            BuildStyle.BALANCED -> "BALANCED"
            BuildStyle.AGGRESSIVE -> "AGGRESSIVE"
            BuildStyle.SAFE -> "SAFE"
            BuildStyle.BEGINNER -> "BEGINNER"
            BuildStyle.MEME -> "MEME"
        }
    }

    private fun difficultyForStyle(
        style: BuildStyle
    ): String {
        return when (style) {
            BuildStyle.BALANCED -> "Medium"
            BuildStyle.AGGRESSIVE -> "Hard"
            BuildStyle.SAFE -> "Easy"
            BuildStyle.BEGINNER -> "Easy"
            BuildStyle.MEME -> "Hard"
        }
    }

    private fun strengthsForStyle(
        selectedPerk: Perk,
        style: BuildStyle
    ): List<String> {
        return when (style) {
            BuildStyle.BALANCED -> listOf(
                "Keeps ${selectedPerk.name} as the core perk",
                "Balances information, pressure, and utility",
                "Useful across many situations",
                "Reliable for general matches"
            )

            BuildStyle.AGGRESSIVE -> listOf(
                "Keeps ${selectedPerk.name} as the core perk",
                "Prioritizes chase and pressure",
                "Rewards active play",
                "Built to create momentum quickly"
            )

            BuildStyle.SAFE -> listOf(
                "Keeps ${selectedPerk.name} as the core perk",
                "Prioritizes survival and consistency",
                "Reduces risky situations",
                "Useful for solo and cautious play"
            )

            BuildStyle.BEGINNER -> listOf(
                "Keeps ${selectedPerk.name} as the core perk",
                "Uses simple and reliable effects",
                "Favors accessible perks",
                "Easy to understand and use"
            )

            BuildStyle.MEME -> listOf(
                "Keeps ${selectedPerk.name} as the core perk",
                "Creates unusual interactions",
                "Prioritizes entertainment",
                "High-risk and unpredictable"
            )
        }
    }

    private fun explanationForStyle(
        selectedPerk: Perk,
        style: BuildStyle
    ): String {
        val styleDescription = when (style) {
            BuildStyle.BALANCED ->
                "a balanced mix of utility, information, and pressure"

            BuildStyle.AGGRESSIVE ->
                "an aggressive setup focused on momentum and active pressure"

            BuildStyle.SAFE ->
                "a safer setup focused on consistency and protection"

            BuildStyle.BEGINNER ->
                "a beginner-friendly setup with straightforward value"

            BuildStyle.MEME ->
                "an unpredictable setup designed for unusual and entertaining plays"
        }

        return "This build keeps ${selectedPerk.name} as the core perk and surrounds it with $styleDescription. " +
                "The engine combines known perk synergies with category preferences for the selected build style."
    }

    private data class ScoredPerk(
        val perk: Perk,
        val totalScore: Int
    )
}