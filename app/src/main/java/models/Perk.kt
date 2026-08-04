package com.example.reapercompanion.models

enum class PerkRole {
    SURVIVOR,
    KILLER
}

enum class PerkCategory {
    CHASE,
    GENERATOR,
    HEALING,
    SUPPORT,
    STEALTH,
    INFORMATION,
    ENDGAME,
    SECOND_CHANCE,
    MEME
}

data class Perk(
    val id: String,
    val name: String,
    val role: PerkRole,
    val category: PerkCategory,
    val description: String,
    val owner: String,
    val isBaseGame: Boolean,

    // Local drawable (optional)
    val iconResource: Int? = null,

    // Online image (optional)
    val imageUrl: String? = null
)