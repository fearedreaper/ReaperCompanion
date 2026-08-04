package com.example.reapercompanion.database

import com.example.reapercompanion.models.BuildStyle
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkCategory
import com.example.reapercompanion.models.PerkRole

object BuildExplanationEngine {

    fun explainBuild(
        selectedPerk: Perk,
        perks: List<Perk>,
        style: BuildStyle
    ): String {
        val otherPerks = perks
            .filter { perk ->
                perk.id != selectedPerk.id
            }
            .distinctBy { perk ->
                perk.id
            }

        val coreExplanation = explainCorePerk(
            perk = selectedPerk
        )

        val partnerExplanations = otherPerks
            .take(3)
            .map { perk ->
                explainPartner(
                    corePerk = selectedPerk,
                    partnerPerk = perk
                )
            }

        val styleExplanation = explainStyle(
            style = style,
            role = selectedPerk.role
        )

        val closingExplanation = explainCombination(
            selectedPerk = selectedPerk,
            perks = perks,
            style = style
        )

        return buildString {
            append(coreExplanation)

            if (partnerExplanations.isNotEmpty()) {
                append("\n\n")

                partnerExplanations.forEachIndexed { index, explanation ->
                    append(explanation)

                    if (index != partnerExplanations.lastIndex) {
                        append("\n\n")
                    }
                }
            }

            append("\n\n")
            append(styleExplanation)

            append("\n\n")
            append(closingExplanation)
        }
    }

    private fun explainCorePerk(
        perk: Perk
    ): String {
        val purpose = categoryPurpose(
            category = perk.category,
            role = perk.role
        )

        return "${perk.name} is the core of this build. " +
                "It provides $purpose and defines how the rest of the loadout is selected."
    }

    private fun explainPartner(
        corePerk: Perk,
        partnerPerk: Perk
    ): String {
        val knownSynergy = PerkSynergyDatabase
            .getPartnersFor(corePerk.id)
            .firstOrNull { synergy ->
                val partnerId =
                    if (synergy.perkId == corePerk.id) {
                        synergy.partnerPerkId
                    } else {
                        synergy.perkId
                    }

                partnerId == partnerPerk.id
            }

        if (knownSynergy != null) {
            return "${partnerPerk.name}: ${knownSynergy.reason}"
        }

        val categoryReason = categoryPairReason(
            coreCategory = corePerk.category,
            partnerCategory = partnerPerk.category,
            role = corePerk.role
        )

        return "${partnerPerk.name} supports ${corePerk.name} by $categoryReason"
    }

    private fun explainStyle(
        style: BuildStyle,
        role: PerkRole
    ): String {
        val roleWord = when (role) {
            PerkRole.SURVIVOR -> "Survivor"
            PerkRole.KILLER -> "Killer"
        }

        return when (style) {
            BuildStyle.BALANCED ->
                "The Balanced style spreads value across several parts of the match, giving this $roleWord build dependable information, pressure, and utility."

            BuildStyle.AGGRESSIVE ->
                "The Aggressive style favors perks that create momentum quickly, shorten downtime, and reward active pressure."

            BuildStyle.SAFE ->
                "The Safe style prioritizes consistency, protection, and reliable value over risky or highly conditional effects."

            BuildStyle.BEGINNER ->
                "The Beginner style favors perks with clear triggers and dependable effects, making the build easier to understand and use."

            BuildStyle.MEME ->
                "The Meme style prioritizes unusual interactions, surprise value, and entertaining plays over maximum consistency."
        }
    }

    private fun explainCombination(
        selectedPerk: Perk,
        perks: List<Perk>,
        style: BuildStyle
    ): String {
        val categories = perks
            .map { perk ->
                perk.category
            }
            .distinct()

        val categorySummary = categories
            .take(4)
            .joinToString(", ") { category ->
                category.name
                    .lowercase()
                    .replace("_", " ")
            }

        val styleName = style.name
            .lowercase()
            .replaceFirstChar { character ->
                character.uppercase()
            }

        return "Together, these perks create a $styleName build around ${selectedPerk.name}. " +
                "The loadout covers $categorySummary, so each perk contributes a different layer of value instead of competing for the same job."
    }

    private fun categoryPurpose(
        category: PerkCategory,
        role: PerkRole
    ): String {
        return when (role) {
            PerkRole.SURVIVOR -> {
                when (category) {
                    PerkCategory.CHASE ->
                        "stronger chase movement and escape options"

                    PerkCategory.GENERATOR ->
                        "objective progress and generator efficiency"

                    PerkCategory.HEALING ->
                        "faster recovery and stronger healing support"

                    PerkCategory.SUPPORT ->
                        "teamwide utility and rescue value"

                    PerkCategory.STEALTH ->
                        "quieter movement and better tracking avoidance"

                    PerkCategory.INFORMATION ->
                        "reliable information about the trial"

                    PerkCategory.ENDGAME ->
                        "strong value during the final stage of the match"

                    PerkCategory.SECOND_CHANCE ->
                        "protection and recovery after dangerous situations"

                    PerkCategory.MEME ->
                        "unusual and entertaining interactions"
                }
            }

            PerkRole.KILLER -> {
                when (category) {
                    PerkCategory.CHASE ->
                        "stronger chase control and faster downs"

                    PerkCategory.GENERATOR ->
                        "generator slowdown and objective pressure"

                    PerkCategory.HEALING ->
                        "pressure against Survivor healing"

                    PerkCategory.SUPPORT ->
                        "utility that strengthens the rest of the loadout"

                    PerkCategory.STEALTH ->
                        "surprise attacks and Terror Radius control"

                    PerkCategory.INFORMATION ->
                        "Survivor tracking and map awareness"

                    PerkCategory.ENDGAME ->
                        "stronger control after the generators are completed"

                    PerkCategory.SECOND_CHANCE ->
                        "comeback potential after losing pressure"

                    PerkCategory.MEME ->
                        "chaotic and unpredictable interactions"
                }
            }
        }
    }

    private fun categoryPairReason(
        coreCategory: PerkCategory,
        partnerCategory: PerkCategory,
        role: PerkRole
    ): String {
        if (coreCategory == partnerCategory) {
            return when (coreCategory) {
                PerkCategory.CHASE ->
                    "doubling down on chase strength and reducing weak moments during pursuit."

                PerkCategory.GENERATOR ->
                    "adding another layer of objective control and generator pressure."

                PerkCategory.HEALING ->
                    "strengthening recovery and healing efficiency."

                PerkCategory.SUPPORT ->
                    "adding more team or loadout utility."

                PerkCategory.STEALTH ->
                    "making tracking and prediction more difficult."

                PerkCategory.INFORMATION ->
                    "providing more consistent information throughout the trial."

                PerkCategory.ENDGAME ->
                    "stacking value for the final stage of the match."

                PerkCategory.SECOND_CHANCE ->
                    "adding another recovery or protection option."

                PerkCategory.MEME ->
                    "creating additional unusual and entertaining interactions."
            }
        }

        return when (role) {
            PerkRole.SURVIVOR ->
                survivorCategoryPairReason(
                    coreCategory = coreCategory,
                    partnerCategory = partnerCategory
                )

            PerkRole.KILLER ->
                killerCategoryPairReason(
                    coreCategory = coreCategory,
                    partnerCategory = partnerCategory
                )
        }
    }

    private fun survivorCategoryPairReason(
        coreCategory: PerkCategory,
        partnerCategory: PerkCategory
    ): String {
        return when (partnerCategory) {
            PerkCategory.CHASE ->
                "adding mobility or stronger escape tools when pressure begins."

            PerkCategory.GENERATOR ->
                "converting survival time into more objective progress."

            PerkCategory.HEALING ->
                "improving recovery after chases and rescues."

            PerkCategory.SUPPORT ->
                "adding value for teammates without removing the core playstyle."

            PerkCategory.STEALTH ->
                "making it easier to avoid detection and reset safely."

            PerkCategory.INFORMATION ->
                "providing better decisions before committing to an action."

            PerkCategory.ENDGAME ->
                "giving the build a stronger finish once the final generator is completed."

            PerkCategory.SECOND_CHANCE ->
                "adding protection when a chase or rescue goes badly."

            PerkCategory.MEME ->
                "adding an unpredictable interaction that can create surprise value."
        }
    }

    private fun killerCategoryPairReason(
        coreCategory: PerkCategory,
        partnerCategory: PerkCategory
    ): String {
        return when (partnerCategory) {
            PerkCategory.CHASE ->
                "helping end pursuits faster and preserve map pressure."

            PerkCategory.GENERATOR ->
                "slowing objectives while the core perk creates pressure elsewhere."

            PerkCategory.HEALING ->
                "punishing Survivors who attempt to recover."

            PerkCategory.SUPPORT ->
                "strengthening the overall loadout with flexible utility."

            PerkCategory.STEALTH ->
                "creating less predictable approaches and surprise attacks."

            PerkCategory.INFORMATION ->
                "revealing where to apply the next wave of pressure."

            PerkCategory.ENDGAME ->
                "adding comeback potential after the generators are completed."

            PerkCategory.SECOND_CHANCE ->
                "providing recovery value after losing momentum."

            PerkCategory.MEME ->
                "adding a chaotic interaction that can disrupt Survivor expectations."
        }
    }
}