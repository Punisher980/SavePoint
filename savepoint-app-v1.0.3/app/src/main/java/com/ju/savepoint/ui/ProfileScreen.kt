package com.ju.savepoint.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import com.ju.savepoint.data.SampleData
import com.ju.savepoint.model.Game
import com.ju.savepoint.model.LogEntry
import com.ju.savepoint.ui.theme.Accent
import com.ju.savepoint.ui.theme.Surface
import com.ju.savepoint.ui.theme.Surface2
import com.ju.savepoint.ui.theme.TextSecondary

@Composable
fun ProfileScreen(logs: List<LogEntry>, onGame: (Game) -> Unit) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 30.dp)) {
        item {
            Row(Modifier.fillMaxWidth().padding(22.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(86.dp).background(Surface2, CircleShape), contentAlignment = Alignment.Center) {
                    Text("JC", style = MaterialTheme.typography.headlineMedium)
                }
                Column(Modifier.padding(start = 16.dp)) {
                    Text("Julio", style = MaterialTheme.typography.headlineMedium)
                    Text("@player_one", color = TextSecondary)
                    Text("Jogando, avaliando, acumulando backlog.", color = TextSecondary, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 5.dp))
                }
            }
        }
        item {
            Row(Modifier.fillMaxWidth().background(Surface).padding(vertical = 18.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                IconStat(Icons.Outlined.DoneAll, logs.count { it.status.label == "Concluído" }.toString(), "zerados")
                IconStat(Icons.Outlined.BookmarkBorder, "18", "backlog")
                IconStat(Icons.Outlined.FavoriteBorder, logs.count { it.liked }.toString(), "favoritos")
                IconStat(Icons.Outlined.FormatListBulleted, "6", "listas")
            }
        }
        item {
            Text("ATIVIDADE RECENTE", color = TextSecondary, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(22.dp, 22.dp, 22.dp, 10.dp))
            LazyRow(contentPadding = PaddingValues(horizontal = 22.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(logs.reversed()) { log ->
                    val game = SampleData.games.first { it.id == log.gameId }
                    Column { GamePoster(game, 92.dp) { onGame(game) }; StarRating(log.rating, 14.dp) }
                }
            }
        }
        item {
            Text("DISTRIBUIÇÃO DE NOTAS", color = TextSecondary, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(22.dp, 26.dp, 22.dp, 10.dp))
            RatingHistogram(logs)
        }
        item {
            Column(Modifier.padding(22.dp)) {
                SectionHeader("Suas listas", "Ver tudo")
                Spacer(Modifier.height(12.dp))
                GameListCard("Favoritos absolutos", "9 jogos", SampleData.games.take(4), onGame)
                Spacer(Modifier.height(12.dp))
                GameListCard("Quero jogar em seguida", "18 jogos", SampleData.games.drop(4).take(4), onGame)
            }
        }
    }
}

@Composable
private fun RatingHistogram(logs: List<LogEntry>) {
    val buckets = (1..10).map { half -> logs.count { (it.rating * 2).toInt() == half } }
    val max = (buckets.maxOrNull() ?: 1).coerceAtLeast(1)
    Canvas(Modifier.fillMaxWidth().height(120.dp).padding(horizontal = 26.dp)) {
        val gap = 7.dp.toPx()
        val w = (size.width - gap * 9) / 10
        buckets.forEachIndexed { i, count ->
            val h = if (count == 0) 5.dp.toPx() else (count.toFloat() / max) * size.height * .78f
            drawRoundRect(
                color = if (count > 0) Accent.copy(alpha = .65f) else Surface2,
                topLeft = Offset(i * (w + gap), size.height - h),
                size = Size(w, h),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(5.dp.toPx(), 5.dp.toPx())
            )
        }
    }
}
