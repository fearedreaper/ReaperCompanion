package com.example.reapercompanion.models

data class PerkSynergy(
    val perkId: String,
    val partnerPerkId: String,
    val score: Int,
    val reason: String
)