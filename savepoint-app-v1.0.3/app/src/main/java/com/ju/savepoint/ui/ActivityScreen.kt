package com.ju.savepoint.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ju.savepoint.data.SampleData
import com.ju.savepoint.ui.theme.Accent
import com.ju.savepoint.ui.theme.Surface2
import com.ju.savepoint.ui.theme.TextSecondary

@Composable
fun ActivityScreen(onGame: (Int) -> Unit) {
    var tab by remember { mutableIntStateOf(0) }
    Column(Modifier.fillMaxSize()) {
        Text("Atividade", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(22.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("Amigos", "Você", "Recebidas").forEachIndexed { index, label ->
                Text(label, color = if (tab == index) Accent else TextSecondary, style = MaterialTheme.typography.titleMedium, modifier = Modifier.clickable { tab = index }.padding(16.dp))
            }
        }
        when (tab) {
            0 -> SampleData.friendsActivity.forEach { item ->
                ActivityRow(item.user, item.action, item.age, item.rating) { item.gameId?.let(onGame) }
            }
            1 -> listOf(
                Triple("Você", "avaliou Ashen Crown com 5 estrelas", "2 d"),
                Triple("Você", "adicionou Moss & Moon aos favoritos", "4 d"),
                Triple("Você", "registrou Static Bloom", "1 sem")
            ).forEach { ActivityRow(it.first, it.second, it.third, null, {}) }
            2 -> SampleData.incoming.forEach { ActivityRow(it.user, it.action, it.age, null, {}) }
        }
    }
}

@Composable
private fun ActivityRow(user: String, action: String, age: String, rating: Float?, onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth().clickable { onClick() }.padding(horizontal = 22.dp, vertical = 15.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(44.dp).background(Surface2, CircleShape), contentAlignment = Alignment.Center) {
            Text(user.take(1).uppercase(), style = MaterialTheme.typography.titleMedium)
        }
        Column(Modifier.weight(1f).padding(start = 12.dp)) {
            Text(user, style = MaterialTheme.typography.titleMedium)
            Text(action, color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
            rating?.let { StarRating(it, 13.dp) }
        }
        Text(age, color = TextSecondary, style = MaterialTheme.typography.labelMedium)
    }
}
