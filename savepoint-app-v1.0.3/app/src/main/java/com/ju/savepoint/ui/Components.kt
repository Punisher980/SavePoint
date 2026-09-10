package com.ju.savepoint.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ju.savepoint.model.Game
import com.ju.savepoint.ui.theme.Accent
import com.ju.savepoint.ui.theme.Border
import com.ju.savepoint.ui.theme.Surface2
import com.ju.savepoint.ui.theme.TextPrimary
import com.ju.savepoint.ui.theme.TextSecondary
import kotlin.math.floor

@Composable
fun SectionHeader(title: String, action: String? = null) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        action?.let { Text(it, color = TextSecondary, style = MaterialTheme.typography.labelLarge) }
    }
}

@Composable
fun GamePoster(game: Game, width: Dp = 126.dp, onClick: (() -> Unit)? = null) {
    Column(modifier = Modifier.width(width)) {
        Box(
            modifier = Modifier
                .width(width)
                .aspectRatio(0.72f)
                .clip(RoundedCornerShape(8.dp))
                .background(Brush.linearGradient(listOf(game.accentA, game.accentB)))
                .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
                .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(8.dp))
        ) {
            Canvas(Modifier.matchParentSize()) {
                drawCircle(Color.White.copy(alpha = 0.07f), size.minDimension * .42f, Offset(size.width * .78f, size.height * .22f))
                drawCircle(Color.Black.copy(alpha = 0.10f), size.minDimension * .52f, Offset(size.width * .16f, size.height * .82f))
                drawRect(Color.Black.copy(alpha = 0.16f), topLeft = Offset(0f, size.height * .68f), size = Size(size.width, size.height * .32f))
            }
            Column(
                Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(game.glyph, color = Color.White.copy(alpha = 0.78f), fontWeight = FontWeight.Black, fontSize = 12.sp)
                Text(game.title.uppercase(), color = Color.White, fontWeight = FontWeight.Black, fontSize = 15.sp, lineHeight = 15.sp, maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
        }
        Text(game.title, color = TextPrimary, style = MaterialTheme.typography.labelLarge, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 7.dp))
        Text(game.year.toString(), color = TextSecondary, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun StarRating(rating: Float, size: Dp = 18.dp, showNumber: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { i ->
            val fill = (rating - i).coerceIn(0f, 1f)
            Box(Modifier.size(size)) {
                Icon(Icons.Rounded.Star, null, tint = Border, modifier = Modifier.matchParentSize())
                if (fill > 0f) {
                    Box(Modifier.fillMaxWidth(fill).height(size).clip(RoundedCornerShape(2.dp))) {
                        Icon(Icons.Rounded.Star, null, tint = Accent, modifier = Modifier.size(size))
                    }
                }
            }
        }
        if (showNumber) Text(String.format(" %.1f", rating), color = TextSecondary, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun Pill(text: String, active: Boolean = false, onClick: (() -> Unit)? = null) {
    val mod = Modifier
        .clip(RoundedCornerShape(50))
        .background(if (active) Accent.copy(alpha = .13f) else Surface2)
        .border(1.dp, if (active) Accent.copy(alpha = .5f) else Border, RoundedCornerShape(50))
        .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
        .padding(horizontal = 12.dp, vertical = 7.dp)
    Text(text, color = if (active) Accent else TextSecondary, style = MaterialTheme.typography.labelLarge, modifier = mod)
}

@Composable
fun IconStat(icon: ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, null, tint = TextSecondary, modifier = Modifier.size(21.dp))
        Text(value, color = TextPrimary, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 5.dp))
        Text(label, color = TextSecondary, style = MaterialTheme.typography.labelMedium)
    }
}

fun Float.ratingText(): String = if (this == floor(this)) this.toInt().toString() else String.format("%.1f", this)
