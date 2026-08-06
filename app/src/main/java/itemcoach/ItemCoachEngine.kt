package com.example.reapercompanion.itemcoach

// Replace your existing ItemCoachEngine.kt with this file.

object ItemCoachEngine {
    fun generate(goalId:String): ItemCoachRecommendationSet = when(goalId){
        "blind_killer" -> ItemCoachRecommendationSet(goalId,"Blind the Killer",listOf(
            RankedRecommendation(RecommendationTier.REAPER_CHOICE,"Reaper's Choice","Utility Flashlight",listOf("Odd Bulb","Intense Halogen"),listOf("Champion of Light","Background Player","Residual Manifest","Flashbang"),"Maximum blind consistency and pickup-save potential.","Wait until the Killer fully commits to the pickup animation before attempting the blind."),
            RankedRecommendation(RecommendationTier.STRONG_ALTERNATIVE,"Strong Alternative","Sport Flashlight",listOf("Focus Lens","Rubber Grip"),listOf("Champion of Light","Sprint Burst","Residual Manifest","Flashbang"),"Excellent substitute when top gear is unavailable.","Stay hidden until the pickup begins."),
            RankedRecommendation(RecommendationTier.BUDGET,"Budget Choice","Camping Flashlight",listOf("Battery","Wide Lens"),listOf("Kindred","Sprint Burst","Windows of Opportunity","Flashbang"),"Great beginner option.","Practice timing before attempting risky saves.")
        ),listOf("Utility Flashlight","Odd Bulb","Champion of Light"))
        else -> ItemCoachRecommendationSet(goalId,"Coming Soon", emptyList(), emptyList())
    }
}