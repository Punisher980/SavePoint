package com.ju.savepoint.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ju.savepoint.data.SampleData
import com.ju.savepoint.model.Game
import com.ju.savepoint.model.LogEntry
import com.ju.savepoint.model.PlayStatus
import com.ju.savepoint.ui.theme.Accent
import com.ju.savepoint.ui.theme.Bg
import com.ju.savepoint.ui.theme.Border
import com.ju.savepoint.ui.theme.Surface
import com.ju.savepoint.ui.theme.Surface2
import com.ju.savepoint.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogGameSheet(preselected: Game?, existing: LogEntry?, onDismiss: () -> Unit, onSave: (LogEntry) -> Unit) {
    var game by remember { mutableStateOf(preselected ?: SampleData.games.first()) }
    var rating by remember { mutableFloatStateOf(existing?.rating ?: 0f) }
    var liked by remember { mutableStateOf(existing?.liked ?: false) }
    var review by remember { mutableStateOf(existing?.review ?: "") }
    var tags by remember { mutableStateOf(existing?.tags?.joinToString(", ") ?: "") }
    var firstTime by remember { mutableStateOf(existing?.firstTime ?: true) }
    var spoiler by remember { mutableStateOf(existing?.spoiler ?: false) }
    var status by remember { mutableStateOf(existing?.status ?: PlayStatus.COMPLETED) }
    var platform by remember { mutableStateOf(existing?.platform ?: game.platforms.first()) }

    ModalBottomSheet(onDismissRequest = onDismiss, containerColor = Surface, tonalElevation = 0.dp) {
        Column(Modifier.padding(horizontal = 20.dp).padding(bottom = 28.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Registrar jogo", style = MaterialTheme.typography.headlineMedium)
                IconButton(onClick = onDismiss) { Icon(Icons.Outlined.Close, null) }
            }

            if (preselected == null) {
                Text("ESCOLHA O JOGO", color = TextSecondary, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
                Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SampleData.games.take(7).forEach { candidate -> Pill(candidate.title, candidate.id == game.id) { game = candidate; platform = candidate.platforms.first() } }
                }
            } else {
                Row(Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    GamePoster(game, 60.dp)
                    Column(Modifier.padding(start = 12.dp)) { Text(game.title, style = MaterialTheme.typography.titleLarge); Text(game.year.toString(), color = TextSecondary) }
                }
            }

            HorizontalDivider(color = Border, modifier = Modifier.padding(vertical = 12.dp))
            Text("NOTA", color = TextSecondary, style = MaterialTheme.typography.labelLarge)
            Row(Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row {
                    repeat(5) { i ->
                        Icon(
                            Icons.Rounded.Star,
                            null,
                            tint = if (rating >= i + 1f) Accent else Border,
                            modifier = Modifier.padding(end = 5.dp).clickable {
                                rating = when {
                                    rating == i + 1f -> i + .5f
                                    else -> i + 1f
                                }
                            }
                        )
                    }
                }
                IconButton(onClick = { liked = !liked }) {
                    Icon(if (liked) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder, null, tint = if (liked) Color(0xFFFF6D85) else TextSecondary)
                }
            }
            Text("Toque novamente na mesma estrela para usar meia estrela.", color = TextSecondary, style = MaterialTheme.typography.labelMedium)

            OutlinedTextField(review, { review = it }, label = { Text("Crítica") }, placeholder = { Text("O que você achou?") }, modifier = Modifier.fillMaxWidth().padding(top = 14.dp), minLines = 4, shape = RoundedCornerShape(12.dp))
            OutlinedTextField(tags, { tags = it }, label = { Text("Tags") }, placeholder = { Text("cozy, difícil, multiplayer...") }, modifier = Modifier.fillMaxWidth().padding(top = 10.dp), singleLine = true, shape = RoundedCornerShape(12.dp))

            Text("STATUS", color = TextSecondary, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) { PlayStatus.entries.forEach { Pill(it.label, status == it) { status = it } } }

            Text("PLATAFORMA", color = TextSecondary, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) { game.platforms.forEach { Pill(it, platform == it) { platform = it } } }

            SettingToggle("Primeira vez jogando", firstTime) { firstTime = it }
            SettingToggle("A crítica contém spoilers", spoiler) { spoiler = it }

            Button(
                onClick = {
                    onSave(LogEntry(game.id, rating, liked, review.trim(), tags.split(",").map { it.trim() }.filter { it.isNotBlank() }, firstTime, spoiler, status, platform, "Hoje"))
                },
                enabled = rating > 0f,
                modifier = Modifier.fillMaxWidth().padding(top = 18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Bg),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Salvar no diário", style = MaterialTheme.typography.titleMedium) }
        }
    }
}

@Composable
private fun SettingToggle(label: String, value: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = TextSecondary)
        Switch(checked = value, onCheckedChange = onChange)
    }
}
