package com.example.reapercompanion.itemcoach

data class RankedRecommendation(
    val tier: RecommendationTier,
    val title: String,
    val item: String,
    val addOns: List<String>,
    val perks: List<String>,
    val whyThisWorks: String,
    val executionTip: String
)

enum class RecommendationTier {
    REAPER_CHOICE,
    STRONG_ALTERNATIVE,
    BUDGET
}

data class ItemCoachRecommendationSet(
    val goalId: String,
    val goalTitle: String,
    val recommendations: List<RankedRecommendation>,
    val nextUnlock: List<String>
)