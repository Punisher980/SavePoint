package com.ju.savepoint.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ju.savepoint.data.SampleData
import com.ju.savepoint.model.Game
import com.ju.savepoint.model.LogEntry
import com.ju.savepoint.ui.theme.Accent
import com.ju.savepoint.ui.theme.TextSecondary

@Composable
fun HomeScreen(onGame: (Game) -> Unit, logs: List<LogEntry>) {
    var tab by remember { mutableIntStateOf(0) }
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 28.dp)
    ) {
        item {
            Column(Modifier.padding(top = 22.dp)) {
                Text("SAVEPOINT", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(horizontal = 22.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp)
                        .horizontalScroll(androidx.compose.foundation.rememberScrollState()),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("Jogos", "Críticas", "Listas", "Diário").forEachIndexed { index, label ->
                        Text(
                            label,
                            color = if (tab == index) Accent else TextSecondary,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(horizontal = 18.dp, vertical = 12.dp)
                                .clickable { tab = index }
                        )
                    }
                }
            }
        }

        when (tab) {
            0 -> {
                item { Spacer(Modifier.height(8.dp)); HomeGames(onGame) }
            }
            1 -> item { ReviewsFeed(logs, onGame) }
            2 -> item { ListsPreview(onGame) }
            3 -> item { JournalPreview(logs, onGame) }
        }
    }
}

@Composable
private fun HomeGames(onGame: (Game) -> Unit) {
    Column {
        SectionHeaderWithPadding("Popular esta semana", "Ver tudo")
        LazyRow(contentPadding = PaddingValues(horizontal = 22.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(SampleData.games.take(7)) { game -> GamePoster(game, 132.dp) { onGame(game) } }
        }
        Spacer(Modifier.height(28.dp))
        SectionHeaderWithPadding("Novidades de amigos", "Ver tudo")
        LazyRow(contentPadding = PaddingValues(horizontal = 22.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(SampleData.games.shuffled().take(5)) { game -> GamePoster(game, 112.dp) { onGame(game) } }
        }
        Spacer(Modifier.height(28.dp))
        SectionHeaderWithPadding("Em alta entre jogadores", "Explorar")
        LazyRow(contentPadding = PaddingValues(horizontal = 22.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(SampleData.games.sortedByDescending { it.communityRating }.take(6)) { game -> GamePoster(game, 112.dp) { onGame(game) } }
        }
    }
}

@Composable
private fun SectionHeaderWithPadding(title: String, action: String) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 14.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Text(action, color = TextSecondary, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun ReviewsFeed(logs: List<LogEntry>, onGame: (Game) -> Unit) {
    Column(Modifier.padding(22.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("Críticas recentes", style = MaterialTheme.typography.headlineMedium)
        logs.forEach { log ->
            val game = SampleData.games.first { it.id == log.gameId }
            ReviewCard(game, log, onGame)
        }
    }
}

@Composable
private fun ListsPreview(onGame: (Game) -> Unit) {
    Column(Modifier.padding(22.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Listas", style = MaterialTheme.typography.headlineMedium)
        GameListCard("Noites que pedem fone", "8 jogos", SampleData.games.take(4), onGame)
        GameListCard("Para terminar este ano", "12 jogos", SampleData.games.drop(4).take(4), onGame)
        GameListCard("Cozy, mas nem tanto", "6 jogos", SampleData.games.drop(2).take(4), onGame)
    }
}

@Composable
private fun JournalPreview(logs: List<LogEntry>, onGame: (Game) -> Unit) {
    Column(Modifier.padding(22.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Diário", style = MaterialTheme.typography.headlineMedium)
        logs.sortedByDescending { it.dateLabel }.forEach { log ->
            val game = SampleData.games.first { it.id == log.gameId }
            JournalRow(game, log, onGame)
        }
    }
}
