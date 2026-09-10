package com.ju.savepoint.data

import android.content.Context
import com.ju.savepoint.model.LogEntry
import com.ju.savepoint.model.PlayStatus
import org.json.JSONArray
import org.json.JSONObject

object LogStore {
    private const val PREFS = "savepoint_prefs"
    private const val KEY_LOGS = "logs"

    fun load(context: Context): List<LogEntry> {
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY_LOGS, null) ?: return emptyList()

        return runCatching {
            val array = JSONArray(raw)
            buildList {
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    add(
                        LogEntry(
                            gameId = obj.getInt("gameId"),
                            rating = obj.getDouble("rating").toFloat(),
                            liked = obj.optBoolean("liked", false),
                            review = obj.optString("review", ""),
                            tags = obj.optJSONArray("tags")?.let { tagsArray ->
                                buildList { for (j in 0 until tagsArray.length()) add(tagsArray.getString(j)) }
                            } ?: emptyList(),
                            firstTime = obj.optBoolean("firstTime", true),
                            spoiler = obj.optBoolean("spoiler", false),
                            status = runCatching { PlayStatus.valueOf(obj.optString("status")) }.getOrDefault(PlayStatus.COMPLETED),
                            platform = obj.optString("platform", "PC"),
                            dateLabel = obj.optString("dateLabel", "Hoje")
                        )
                    )
                }
            }
        }.getOrDefault(emptyList())
    }

    fun save(context: Context, logs: List<LogEntry>) {
        val array = JSONArray()
        logs.forEach { log ->
            array.put(
                JSONObject().apply {
                    put("gameId", log.gameId)
                    put("rating", log.rating.toDouble())
                    put("liked", log.liked)
                    put("review", log.review)
                    put("tags", JSONArray(log.tags))
                    put("firstTime", log.firstTime)
                    put("spoiler", log.spoiler)
                    put("status", log.status.name)
                    put("platform", log.platform)
                    put("dateLabel", log.dateLabel)
                }
            )
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LOGS, array.toString())
            .apply()
    }
}
