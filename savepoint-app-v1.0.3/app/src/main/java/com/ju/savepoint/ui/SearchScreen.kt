package com.ju.savepoint.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.ju.savepoint.data.SampleData
import com.ju.savepoint.model.Game
import com.ju.savepoint.ui.theme.Border
import com.ju.savepoint.ui.theme.Surface2
import com.ju.savepoint.ui.theme.TextSecondary

@Composable
fun SearchScreen(onGame: (Game) -> Unit) {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    val results = if (query.text.isBlank()) emptyList() else SampleData.games.filter {
        it.title.contains(query.text, true) || it.genres.any { g -> g.contains(query.text, true) } || it.studio.contains(query.text, true)
    }

    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 28.dp)) {
        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Buscar jogos, estúdios ou gêneros") },
                leadingIcon = { Icon(Icons.Outlined.Search, null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(22.dp),
                shape = RoundedCornerShape(22.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Surface2,
                    unfocusedContainerColor = Surface2,
                    focusedBorderColor = Border,
                    unfocusedBorderColor = Border
                )
            )
        }

        if (query.text.isNotBlank()) {
            item {
                Text("RESULTADOS", color = TextSecondary, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(horizontal = 22.dp, vertical = 8.dp))
            }
            items(results) { game -> SearchResultRow(game, onGame) }
            if (results.isEmpty()) item { Text("Nenhum jogo encontrado.", color = TextSecondary, modifier = Modifier.padding(22.dp)) }
        } else {
            item {
                Text("DESCOBRIR", color = TextSecondary, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(horizontal = 22.dp, vertical = 10.dp))
            }
            item {
                LazyRow(contentPadding = PaddingValues(horizontal = 22.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(SampleData.games.take(5)) { GamePoster(it, 104.dp) { onGame(it) } }
                }
            }
            item {
                Column(Modifier.padding(top = 22.dp)) {
                    listOf(
                        "Data de lançamento", "Gênero", "Plataforma", "Estúdio ou publisher", "Mais populares", "Melhores avaliados", "Mais aguardados", "Top 250 da comunidade", "Listas em destaque"
                    ).forEach { BrowseRow(it) }
                }
            }
        }
    }
}

@Composable
private fun SearchResultRow(game: Game, onGame: (Game) -> Unit) {
    Row(
        Modifier.fillMaxWidth().clickable { onGame(game) }.padding(horizontal = 22.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GamePoster(game, 58.dp)
        Column(Modifier.weight(1f).padding(start = 12.dp)) {
            Text(game.title, style = MaterialTheme.typography.titleMedium)
            Text("${game.year}  •  ${game.studio}", color = TextSecondary, style = MaterialTheme.typography.labelMedium)
            StarRating(game.communityRating, 14.dp, showNumber = true)
        }
        Icon(Icons.Outlined.ChevronRight, null, tint = TextSecondary)
    }
}

@Composable
private fun BrowseRow(label: String) {
    Row(
        Modifier.fillMaxWidth().clickable { }.padding(horizontal = 22.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = TextSecondary, style = MaterialTheme.typography.bodyLarge)
        Icon(Icons.Outlined.ChevronRight, null, tint = TextSecondary)
    }
}
