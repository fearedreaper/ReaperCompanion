package com.example.reapercompanion.models

data class MatchCoachRecommendation(
    val title: String,
    val opponentName: String,
    val mapName: String,
    val difficulty: String,
    val score: Int,
    val threatLevel: String,
    val recommendedPerks: List<Perk>,
    val chaseAdvice: List<String>,
    val objectiveAdvice: List<String>,
    val endgameAdvice: List<String>,
    val warnings: List<String>,
    val summary: String
)