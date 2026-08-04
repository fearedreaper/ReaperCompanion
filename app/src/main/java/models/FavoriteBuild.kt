package com.example.reapercompanion.models

data class FavoriteBuild(
    val name: String,
    val goal: String,
    val score: Int,
    val difficulty: String,
    val perks: List<String>
)