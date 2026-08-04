package com.example.reapercompanion.models

data class ComparedBuild(
    val recommendation: BuildRecommendation,
    val timestamp: Long = System.currentTimeMillis()
)