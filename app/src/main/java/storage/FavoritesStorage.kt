package com.example.reapercompanion.storage

import android.content.Context
import com.example.reapercompanion.models.FavoriteBuild
import org.json.JSONArray
import org.json.JSONObject

object FavoritesStorage {

    private const val PREFERENCES_NAME = "reaper_companion_favorites"
    private const val FAVORITES_KEY = "saved_builds"

    fun saveFavorite(
        context: Context,
        build: FavoriteBuild
    ) {
        val favorites = loadFavorites(context).toMutableList()

        val alreadySaved = favorites.any {
            it.name == build.name &&
                    it.goal == build.goal &&
                    it.perks == build.perks
        }

        if (!alreadySaved) {
            favorites.add(build)
            saveAllFavorites(context, favorites)
        }
    }

    fun loadFavorites(
        context: Context
    ): List<FavoriteBuild> {
        val preferences = context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

        val savedJson = preferences.getString(
            FAVORITES_KEY,
            null
        ) ?: return emptyList()

        return try {
            val jsonArray = JSONArray(savedJson)
            val favorites = mutableListOf<FavoriteBuild>()

            for (index in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(index)
                val perksArray = jsonObject.getJSONArray("perks")
                val perks = mutableListOf<String>()

                for (perkIndex in 0 until perksArray.length()) {
                    perks.add(perksArray.getString(perkIndex))
                }

                favorites.add(
                    FavoriteBuild(
                        name = jsonObject.getString("name"),
                        goal = jsonObject.getString("goal"),
                        score = jsonObject.getInt("score"),
                        difficulty = jsonObject.getString("difficulty"),
                        perks = perks
                    )
                )
            }

            favorites
        } catch (_: Exception) {
            emptyList()
        }
    }

    fun deleteFavorite(
        context: Context,
        build: FavoriteBuild
    ) {
        val updatedFavorites = loadFavorites(context).filterNot {
            it.name == build.name &&
                    it.goal == build.goal &&
                    it.perks == build.perks
        }

        saveAllFavorites(
            context = context,
            favorites = updatedFavorites
        )
    }

    fun isFavorite(
        context: Context,
        build: FavoriteBuild
    ): Boolean {
        return loadFavorites(context).any {
            it.name == build.name &&
                    it.goal == build.goal &&
                    it.perks == build.perks
        }
    }

    private fun saveAllFavorites(
        context: Context,
        favorites: List<FavoriteBuild>
    ) {
        val jsonArray = JSONArray()

        favorites.forEach { build ->
            val perksArray = JSONArray()

            build.perks.forEach { perk ->
                perksArray.put(perk)
            }

            val buildObject = JSONObject().apply {
                put("name", build.name)
                put("goal", build.goal)
                put("score", build.score)
                put("difficulty", build.difficulty)
                put("perks", perksArray)
            }

            jsonArray.put(buildObject)
        }

        context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(
                FAVORITES_KEY,
                jsonArray.toString()
            )
            .apply()
    }
}