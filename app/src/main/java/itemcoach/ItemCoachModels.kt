package com.example.reapercompanion.itemcoach

data class ItemCoachGoal(
    val id: String,
    val title: String,
    val description: String
)

data class ItemCoachRecommendation(
    val goalId: String,
    val goalTitle: String,
    val recommendedItem: String,
    val recommendedAddOns: List<String>,
    val recommendedPerks: List<String>,
    val recommendedOffering: String?,
    val explanation: String,
    val warnings: List<String> = emptyList(),
    val alternatives: List<String> = emptyList()
)

object ItemCoachGoals {

    val all: List<ItemCoachGoal> = listOf(
        ItemCoachGoal(
            id = "blind_killer",
            title = "Blind the Killer",
            description = "Create a flashlight-focused loadout for blinds and saves."
        ),
        ItemCoachGoal(
            id = "heal_faster",
            title = "Heal Faster",
            description = "Build around quick healing and team recovery."
        ),
        ItemCoachGoal(
            id = "rush_generators",
            title = "Rush Generators",
            description = "Maximize repair efficiency and generator pressure."
        ),
        ItemCoachGoal(
            id = "sabotage_hooks",
            title = "Sabotage Hooks",
            description = "Prepare for hook denial and rescue plays."
        ),
        ItemCoachGoal(
            id = "support_teammates",
            title = "Support Teammates",
            description = "Improve information, healing, and rescue potential."
        ),
        ItemCoachGoal(
            id = "stealth",
            title = "Play Stealthy",
            description = "Reduce detection and avoid unnecessary chases."
        )
    )

    fun findById(
        id: String
    ): ItemCoachGoal? {
        return all.firstOrNull { goal ->
            goal.id == id
        }
    }
}