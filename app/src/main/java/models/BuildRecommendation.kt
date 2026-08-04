package com.example.reapercompanion.models

data class BuildRecommendation(
    val name: String,
    val goal: String,
    val score: Int,
    val difficulty: String,
    val perks: List<Perk>,
    val alternatives: List<Perk>,
    val strengths: List<String>,
    val explanation: String
)