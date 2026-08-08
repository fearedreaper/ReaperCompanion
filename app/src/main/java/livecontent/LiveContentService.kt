package com.example.reapercompanion.livecontent

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object LiveContentService {

    suspend fun fetchLiveContent(): LiveContent {
        return withContext(Dispatchers.IO) {
            try {
                val connection =
                    URL(
                        LiveContentRemoteSource.LIVE_CONTENT_URL
                    ).openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.connectTimeout = 10_000
                connection.readTimeout = 10_000
                connection.useCaches = false

                val responseCode = connection.responseCode

                if (responseCode !in 200..299) {
                    connection.disconnect()
                    LiveContentRepository.getLiveContent()
                } else {
                    val responseText =
                        connection.inputStream
                            .bufferedReader()
                            .use { reader ->
                                reader.readText()
                            }

                    connection.disconnect()

                    parseLiveContent(responseText)
                }
            } catch (exception: Exception) {
                LiveContentRepository.getLiveContent()
            }
        }
    }

    private fun parseLiveContent(
        jsonText: String
    ): LiveContent {
        val root = JSONObject(jsonText)

        val announcementsArray =
            root.optJSONArray("announcements")

        val eventsArray =
            root.optJSONArray("events")
                ?: root.optJSONArray("rewards")

        val codesArray =
            root.optJSONArray("codes")

        val updatesArray =
            root.optJSONArray("updates")

        val announcements =
            buildList {
                if (announcementsArray != null) {
                    for (
                    index in
                    0 until announcementsArray.length()
                    ) {
                        val item =
                            announcementsArray
                                .getJSONObject(index)

                        add(
                            LiveAnnouncement(
                                id = item.optString("id"),
                                title = item.optString("title"),
                                titleEs =
                                    item.optString("titleEs"),
                                message =
                                    item.optString("message"),
                                messageEs =
                                    item.optString("messageEs"),
                                active =
                                    item.optBoolean(
                                        "active",
                                        true
                                    )
                            )
                        )
                    }
                }
            }

        val events =
            buildList {
                if (eventsArray != null) {
                    for (
                    index in
                    0 until eventsArray.length()
                    ) {
                        val item =
                            eventsArray
                                .getJSONObject(index)

                        add(
                            LiveEvent(
                                id = item.optString("id"),
                                title = item.optString("title"),
                                titleEs =
                                    item.optString("titleEs"),
                                description =
                                    item.optString("description"),
                                descriptionEs =
                                    item.optString(
                                        "descriptionEs"
                                    ),
                                expires =
                                    item.optString(
                                        "expires",
                                        "Limited Time"
                                    ),
                                expiresEs =
                                    item.optString("expiresEs"),
                                active =
                                    item.optBoolean(
                                        "active",
                                        true
                                    )
                            )
                        )
                    }
                }
            }

        val codes =
            buildList {
                if (codesArray != null) {
                    for (
                    index in
                    0 until codesArray.length()
                    ) {
                        val item =
                            codesArray
                                .getJSONObject(index)

                        add(
                            LiveCode(
                                id = item.optString("id"),
                                code = item.optString("code"),
                                reward =
                                    item.optString("reward"),
                                rewardEs =
                                    item.optString("rewardEs"),
                                expires =
                                    item.optString(
                                        "expires",
                                        "No expiration announced"
                                    ),
                                expiresEs =
                                    item.optString("expiresEs"),
                                active =
                                    item.optBoolean(
                                        "active",
                                        true
                                    )
                            )
                        )
                    }
                }
            }

        val updates =
            buildList {
                if (updatesArray != null) {
                    for (
                    index in
                    0 until updatesArray.length()
                    ) {
                        val item =
                            updatesArray
                                .getJSONObject(index)

                        add(
                            LiveUpdate(
                                id = item.optString("id"),
                                title = item.optString("title"),
                                titleEs =
                                    item.optString("titleEs"),
                                description =
                                    item.optString("description"),
                                descriptionEs =
                                    item.optString(
                                        "descriptionEs"
                                    ),
                                date = item.optString("date"),
                                dateEs =
                                    item.optString("dateEs"),
                                category =
                                    item.optString(
                                        "category",
                                        "UPDATE"
                                    ),
                                categoryEs =
                                    item.optString(
                                        "categoryEs",
                                        "ACTUALIZACIÓN"
                                    ),
                                url = item.optString("url"),
                                active =
                                    item.optBoolean(
                                        "active",
                                        true
                                    )
                            )
                        )
                    }
                }
            }

        return LiveContent(
            announcements = announcements,
            events = events,
            codes = codes,
            updates = updates
        )
    }
}