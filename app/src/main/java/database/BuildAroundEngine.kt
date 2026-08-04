package com.example.reapercompanion.database

import com.example.reapercompanion.models.BuildRecommendation
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkRole

object BuildAroundEngine {

    fun generateBuildAround(
        selectedPerk: Perk
    ): BuildRecommendation {
        val allPerks = when (selectedPerk.role) {
            PerkRole.SURVIVOR -> PerkDatabase.getSurvivorPerks()
            PerkRole.KILLER -> KillerPerkDatabase.allPerks
        }

        val synergies = PerkSynergyDatabase
            .getPartnersFor(selectedPerk.id)

        val partnerIds = synergies
            .map { synergy ->
                if (synergy.perkId == selectedPerk.id) {
                    synergy.partnerPerkId
                } else {
                    synergy.perkId
                }
            }

        val selectedPartners = partnerIds
            .mapNotNull { partnerId ->
                allPerks.firstOrNull { perk ->
                    perk.id == partnerId
                }
            }
            .distinctBy { perk ->
                perk.id
            }
            .take(3)

        val fallbackPerks = allPerks
            .filter { perk ->
                perk.id != selectedPerk.id &&
                        selectedPartners.none { selected ->
                            selected.id == perk.id
                        }
            }
            .take(3 - selectedPartners.size)

        val finalPerks = listOf(selectedPerk) +
                selectedPartners +
                fallbackPerks

        val averageSynergyScore =
            if (synergies.isEmpty()) {
                75
            } else {
                synergies
                    .take(3)
                    .map { it.score }
                    .average()
                    .times(10)
                    .toInt()
                    .coerceIn(75, 100)
            }

        val roleLabel = when (selectedPerk.role) {
            PerkRole.SURVIVOR -> "Survivor"
            PerkRole.KILLER -> "Killer"
        }

        return BuildRecommendation(
            name = "Built Around ${selectedPerk.name}",
            goal = "BUILD AROUND A PERK",
            score = averageSynergyScore,
            difficulty = if (averageSynergyScore >= 90) {
                "Medium"
            } else {
                "Easy"
            },
            perks = finalPerks,
            alternatives = allPerks
                .filter { perk ->
                    finalPerks.none { selected ->
                        selected.id == perk.id
                    }
                }
                .take(4),
            strengths = listOf(
                "Keeps ${selectedPerk.name} as the core perk",
                "Uses the strongest available synergy partners",
                "Creates a balanced $roleLabel loadout",
                "Easy to expand as new perks are added"
            ),
            explanation =
                "This build is generated around ${selectedPerk.name}. " +
                        "The engine selects the highest-scoring synergy partners " +
                        "currently available in the Reaper Companion database."
        )
    }
}