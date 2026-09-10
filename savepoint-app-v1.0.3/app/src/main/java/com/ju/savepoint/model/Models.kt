package com.ju.savepoint.model

import androidx.compose.ui.graphics.Color

data class Game(
    val id: Int,
    val title: String,
    val year: Int,
    val studio: String,
    val genres: List<String>,
    val platforms: List<String>,
    val communityRating: Float,
    val ratingsCount: Int,
    val summary: String,
    val accentA: Color,
    val accentB: Color,
    val glyph: String
)

data class LogEntry(
    val gameId: Int,
    val rating: Float,
    val liked: Boolean = false,
    val review: String = "",
    val tags: List<String> = emptyList(),
    val firstTime: Boolean = true,
    val spoiler: Boolean = false,
    val status: PlayStatus = PlayStatus.COMPLETED,
    val platform: String = "PC",
    val dateLabel: String = "Hoje"
)

enum class PlayStatus(val label: String) {
    PLAYING("Jogando"),
    COMPLETED("Concluído"),
    DROPPED("Abandonado"),
    BACKLOG("Backlog")
}

data class ActivityItem(
    val user: String,
    val action: String,
    val gameId: Int? = null,
    val rating: Float? = null,
    val age: String
)
