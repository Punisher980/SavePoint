package com.ju.savepoint.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Bg = Color(0xFF080C11)
val Surface = Color(0xFF10161E)
val Surface2 = Color(0xFF151D27)
val Border = Color(0xFF273241)
val TextPrimary = Color(0xFFF4F7FB)
val TextSecondary = Color(0xFF97A6B8)
val Accent = Color(0xFF38E683)
val AccentBlue = Color(0xFF3AC6FF)
val Warning = Color(0xFFFFB648)

private val SavepointScheme = darkColorScheme(
    primary = Accent,
    secondary = AccentBlue,
    background = Bg,
    surface = Surface,
    surfaceVariant = Surface2,
    onPrimary = Bg,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline = Border
)

@Composable
fun SavepointTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SavepointScheme,
        typography = AppTypography,
        content = content
    )
}
