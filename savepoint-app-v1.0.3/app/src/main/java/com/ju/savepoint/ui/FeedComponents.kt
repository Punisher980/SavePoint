package com.ju.savepoint.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ju.savepoint.model.Game
import com.ju.savepoint.model.LogEntry
import com.ju.savepoint.ui.theme.Surface
import com.ju.savepoint.ui.theme.Surface2
import com.ju.savepoint.ui.theme.TextSecondary

@Composable
fun ReviewCard(game: Game, log: LogEntry, onGame: (Game) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Surface, RoundedCornerShape(14.dp))
            .clickable { onGame(game) }
            .padding(12.dp)
    ) {
        GamePoster(game, 78.dp)
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(game.title, style = MaterialTheme.typography.titleLarge)
            StarRating(log.rating, 16.dp)
            if (log.review.isNotBlank()) {
                Text(log.review, color = TextSecondary, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 8.dp))
            }
            Row(Modifier.padding(top = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                if (log.liked) Icon(Icons.Outlined.FavoriteBorder, null, tint = Color(0xFFFF6D85), modifier = Modifier.height(16.dp))
                Text("  ${log.platform}  •  ${log.status.label}", color = TextSecondary, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
fun GameListCard(title: String, subtitle: String, games: List<Game>, onGame: (Game) -> Unit) {
    Column(Modifier.fillMaxWidth().background(Surface, RoundedCornerShape(14.dp)).padding(14.dp)) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        Text(subtitle, color = TextSecondary, style = MaterialTheme.typography.labelMedium)
        Row(Modifier.padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            games.take(4).forEach { GamePoster(it, 58.dp) { onGame(it) } }
        }
    }
}

@Composable
fun JournalRow(game: Game, log: LogEntry, onGame: (Game) -> Unit) {
    Row(
        Modifier.fillMaxWidth().clickable { onGame(game) }.background(Surface2, RoundedCornerShape(12.dp)).padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GamePoster(game, 62.dp)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(game.title, fontWeight = FontWeight.SemiBold)
            Text("${log.dateLabel}  •  ${log.platform}", color = TextSecondary, style = MaterialTheme.typography.labelMedium)
            StarRating(log.rating, 15.dp)
        }
    }
}
