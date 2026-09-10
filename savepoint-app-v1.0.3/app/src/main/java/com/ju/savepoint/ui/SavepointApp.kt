package com.ju.savepoint.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ju.savepoint.data.LogStore
import com.ju.savepoint.data.SampleData
import com.ju.savepoint.model.Game
import com.ju.savepoint.model.LogEntry
import com.ju.savepoint.ui.theme.Accent
import com.ju.savepoint.ui.theme.AccentBlue
import com.ju.savepoint.ui.theme.Bg
import com.ju.savepoint.ui.theme.Surface2
import com.ju.savepoint.ui.theme.TextSecondary

private data class NavItem(val label: String, val icon: ImageVector)

@Composable
fun SavepointApp() {
    val context = LocalContext.current
    var selected by remember { mutableIntStateOf(0) }
    var selectedGame by remember { androidx.compose.runtime.mutableStateOf<Game?>(null) }
    var showLog by remember { androidx.compose.runtime.mutableStateOf(false) }
    var logs by remember { androidx.compose.runtime.mutableStateOf(LogStore.load(context).ifEmpty { SampleData.initialLogs }) }

    val nav = listOf(
        NavItem("Jogos", Icons.Rounded.SportsEsports),
        NavItem("Buscar", Icons.Outlined.Search),
        NavItem("Registrar", Icons.Outlined.AddCircleOutline),
        NavItem("Atividade", Icons.Outlined.Bolt),
        NavItem("Perfil", Icons.Outlined.PersonOutline)
    )

    Scaffold(
        containerColor = Bg,
        bottomBar = {
            AppBottomBar(
                items = nav,
                selected = selected,
                onSelect = {
                    if (it == 2) showLog = true else selected = it
                }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when (selected) {
                0 -> HomeScreen(onGame = { selectedGame = it }, logs = logs)
                1 -> SearchScreen(onGame = { selectedGame = it })
                3 -> ActivityScreen(onGame = { id -> selectedGame = SampleData.games.firstOrNull { it.id == id } })
                4 -> ProfileScreen(logs = logs, onGame = { selectedGame = it })
            }
        }
    }

    selectedGame?.let { game ->
        GameDetailScreen(
            game = game,
            existing = logs.firstOrNull { it.gameId == game.id },
            onBack = { selectedGame = null },
            onLog = { showLog = true }
        )
    }

    if (showLog) {
        LogGameSheet(
            preselected = selectedGame,
            existing = selectedGame?.let { game -> logs.firstOrNull { it.gameId == game.id } },
            onDismiss = { showLog = false },
            onSave = { entry ->
                logs = logs.filterNot { it.gameId == entry.gameId } + entry
                LogStore.save(context, logs)
                showLog = false
            }
        )
    }
}

@Composable
private fun AppBottomBar(items: List<NavItem>, selected: Int, onSelect: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Surface2)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            val active = index == selected
            val isAdd = index == 2
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSelect(index) }
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                if (active || isAdd) {
                    Box(
                        Modifier
                            .background(if (isAdd) Accent.copy(alpha = 0.12f) else AccentBlue.copy(alpha = 0.10f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Icon(item.icon, item.label, tint = if (isAdd) Accent else AccentBlue, modifier = Modifier.size(26.dp))
                    }
                } else {
                    Icon(item.icon, item.label, tint = TextSecondary, modifier = Modifier.size(26.dp))
                }
            }
        }
    }
}
