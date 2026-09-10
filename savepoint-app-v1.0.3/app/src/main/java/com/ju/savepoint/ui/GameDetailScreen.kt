package com.ju.savepoint.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PlaylistAdd
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ju.savepoint.model.Game
import com.ju.savepoint.model.LogEntry
import com.ju.savepoint.ui.theme.Accent
import com.ju.savepoint.ui.theme.Surface
import com.ju.savepoint.ui.theme.Surface2
import com.ju.savepoint.ui.theme.TextSecondary

@Composable
fun GameDetailScreen(game: Game, existing: LogEntry?, onBack: () -> Unit, onLog: () -> Unit) {
    Box(Modifier.fillMaxSize().background(com.ju.savepoint.ui.theme.Bg)) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Row(Modifier.fillMaxWidth().padding(top = 12.dp, start = 10.dp)) {
                IconButton(onClick = onBack) { Icon(Icons.Outlined.ArrowBack, "Voltar") }
            }
            Row(Modifier.padding(horizontal = 22.dp, vertical = 8.dp)) {
                GamePoster(game, 132.dp)
                Column(Modifier.padding(start = 18.dp).weight(1f)) {
                    Text(game.title, style = MaterialTheme.typography.headlineLarge)
                    Text("${game.year}  •  ${game.studio}", color = TextSecondary, modifier = Modifier.padding(top = 4.dp))
                    StarRating(game.communityRating, 18.dp, showNumber = true)
                    Text("${game.ratingsCount} avaliações", color = TextSecondary, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 3.dp))
                    Row(Modifier.padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                        game.genres.take(2).forEach { Pill(it) }
                    }
                }
            }
            Column(Modifier.padding(22.dp)) {
                if (existing != null) {
                    Text("SUA NOTA", color = TextSecondary, style = MaterialTheme.typography.labelLarge)
                    Row(Modifier.fillMaxWidth().background(Surface, RoundedCornerShape(14.dp)).padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        StarRating(existing.rating, 22.dp)
                        Text(existing.status.label, color = Accent, style = MaterialTheme.typography.labelLarge)
                    }
                    Spacer(Modifier.height(14.dp))
                }
                Button(
                    onClick = onLog,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = com.ju.savepoint.ui.theme.Bg),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Outlined.RateReview, null)
                    Text(if (existing == null) "  Registrar e avaliar" else "  Editar registro", style = MaterialTheme.typography.titleMedium)
                }
                Row(Modifier.fillMaxWidth().padding(top = 14.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MiniAction("Curtir", Icons.Outlined.FavoriteBorder, Modifier.weight(1f))
                    MiniAction("Lista", Icons.Outlined.PlaylistAdd, Modifier.weight(1f))
                }
                Text("SOBRE", color = TextSecondary, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(top = 28.dp, bottom = 8.dp))
                Text(game.summary, style = MaterialTheme.typography.bodyLarge)
                Text("PLATAFORMAS", color = TextSecondary, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(top = 24.dp, bottom = 8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { game.platforms.forEach { Pill(it) } }
                Text("CRÍTICAS POPULARES", color = TextSecondary, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(top = 28.dp, bottom = 8.dp))
                repeat(3) { idx ->
                    Column(Modifier.fillMaxWidth().background(Surface, RoundedCornerShape(12.dp)).padding(14.dp)) {
                        Text(listOf("pixelmoth", "save_slot_7", "joana.exe")[idx], style = MaterialTheme.typography.titleMedium)
                        StarRating(listOf(4.5f, 4f, 5f)[idx], 14.dp)
                        Text(listOf("O mundo parece ter sido feito para você se perder nele.", "Muito bom, mas a segunda metade perde um pouco de ritmo.", "Entrou direto na lista de favoritos.")[idx], color = TextSecondary, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 6.dp))
                    }
                    Spacer(Modifier.height(10.dp))
                }
                Spacer(Modifier.height(30.dp))
            }
        }
    }
}

@Composable
private fun MiniAction(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Row(modifier.background(Surface2, RoundedCornerShape(12.dp)).padding(14.dp), horizontalArrangement = Arrangement.Center) {
        Icon(icon, null, tint = TextSecondary)
        Text("  $label", color = TextSecondary)
    }
}
