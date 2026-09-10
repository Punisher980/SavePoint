package com.ju.savepoint.data

import androidx.compose.ui.graphics.Color
import com.ju.savepoint.model.ActivityItem
import com.ju.savepoint.model.Game
import com.ju.savepoint.model.LogEntry
import com.ju.savepoint.model.PlayStatus

object SampleData {
    val games = listOf(
        Game(1, "Neon Reverie", 2026, "Glass Harbor", listOf("RPG", "Sci-fi"), listOf("PC", "PS5", "Xbox"), 4.4f, 18420, "Uma cidade vertical, memórias gravadas em fitas e escolhas que mudam o mapa.", Color(0xFF1F3C88), Color(0xFF9C3DCC), "NR"),
        Game(2, "Ashen Crown", 2025, "Northkeep", listOf("Action RPG", "Soulslike"), listOf("PC", "PS5", "Xbox"), 4.7f, 52188, "Um reino queimado onde cada chefe deixa uma cicatriz permanente no mundo.", Color(0xFF5B2A19), Color(0xFFE36B2C), "AC"),
        Game(3, "Low Tide", 2024, "Morrow Studio", listOf("Adventure", "Mystery"), listOf("PC", "Switch"), 4.1f, 7932, "Mistério costeiro íntimo sobre uma cidade que desaparece quando a maré sobe.", Color(0xFF12384B), Color(0xFF2CA6A4), "LT"),
        Game(4, "Rift Circuit", 2026, "Velocity Dept.", listOf("Racing", "Arcade"), listOf("PC", "PS5", "Xbox"), 4.2f, 12044, "Corridas ilegais entre dimensões, carros absurdos e trilha eletrônica nervosa.", Color(0xFF3D144C), Color(0xFFFF4E8A), "RC"),
        Game(5, "Moss & Moon", 2023, "Pebble Lane", listOf("Cozy", "Simulation"), listOf("PC", "Switch", "Mobile"), 4.5f, 33510, "Construa uma vila minúscula para criaturas noturnas e veja as estações respirarem.", Color(0xFF234A37), Color(0xFF77B77B), "M&M"),
        Game(6, "Static Bloom", 2025, "PALM//ERROR", listOf("Horror", "Puzzle"), listOf("PC", "PS5"), 4.0f, 14401, "Terror analógico em uma estação de TV abandonada que insiste em transmitir você.", Color(0xFF2E3340), Color(0xFFB83F5A), "SB"),
        Game(7, "Sunken Atlas", 2022, "Blue Room", listOf("Exploration", "Indie"), listOf("PC", "Switch"), 4.3f, 9870, "Explore ilhas que aparecem apenas no fundo do oceano durante eclipses.", Color(0xFF164A5A), Color(0xFF46B6C7), "SA"),
        Game(8, "Gravel Saints", 2026, "Motor Hymn", listOf("Action", "Open world"), listOf("PC", "PS5", "Xbox"), 4.6f, 28802, "Estradas infinitas, motocicletas improvisadas e uma guerra de rádio no deserto.", Color(0xFF533C28), Color(0xFFD8A246), "GS"),
        Game(9, "Paper Kingdom", 2021, "Tiny Monarch", listOf("Strategy", "Indie"), listOf("PC", "Switch", "Mobile"), 3.9f, 6550, "Administre um reino desenhado à mão onde decretos alteram as próprias regras do jogo.", Color(0xFF5D4B3A), Color(0xFFE8CC8A), "PK"),
        Game(10, "Afterimage Motel", 2024, "Room 404", listOf("Narrative", "Horror"), listOf("PC", "PS5"), 4.2f, 11100, "Um motel existe por uma noite a cada dez anos. Você acabou de fazer check-in.", Color(0xFF38283F), Color(0xFF8D6BC0), "AM"),
        Game(11, "Orbital Kids", 2025, "Warm Signal", listOf("Platformer", "Co-op"), listOf("PC", "Switch", "PS5"), 4.4f, 17650, "Plataforma cooperativo sobre duas crianças consertando uma estação espacial enorme.", Color(0xFF1E3557), Color(0xFFF2C14E), "OK"),
        Game(12, "Blackwater FM", 2023, "Dead Air", listOf("Narrative", "Thriller"), listOf("PC", "Xbox"), 4.0f, 8421, "Apresente um programa de rádio de madrugada enquanto crimes acontecem ao vivo pela cidade.", Color(0xFF151A22), Color(0xFF44638B), "FM")
    )

    val initialLogs = listOf(
        LogEntry(2, 5f, true, "Combate impecável e um mundo que recompensa curiosidade.", listOf("favorito"), true, false, PlayStatus.COMPLETED, "PS5", "08 set"),
        LogEntry(5, 4.5f, true, "Meu jogo de fim de noite.", listOf("cozy"), true, false, PlayStatus.PLAYING, "Switch", "06 set"),
        LogEntry(6, 4f, false, "Áudio excelente. Algumas partes me pegaram desprevenido.", listOf("terror"), true, false, PlayStatus.COMPLETED, "PC", "01 set"),
        LogEntry(4, 4f, true, "Arcade puro, sem freio.", listOf("corrida"), true, false, PlayStatus.COMPLETED, "PC", "27 ago")
    )

    val incoming = listOf(
        ActivityItem("andreattigui", "começou a seguir você", age = "2 sem"),
        ActivityItem("Abyner Simoes", "começou a seguir você", age = "3 sem")
    )

    val friendsActivity = listOf(
        ActivityItem("andreattigui", "avaliou Ashen Crown", 2, 4.5f, "12 min"),
        ActivityItem("maria.ctrl", "adicionou Moss & Moon à lista Cozy para zerar", 5, null, "1 h"),
        ActivityItem("gg_ueda", "terminou Rift Circuit", 4, 4f, "3 h"),
        ActivityItem("erison.exe", "escreveu uma crítica de Static Bloom", 6, 4.5f, "ontem")
    )
}
