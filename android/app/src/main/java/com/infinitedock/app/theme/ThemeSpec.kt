package com.infinitedock.app.theme

import androidx.compose.ui.graphics.Color

enum class AnimationIntensity {
    OFF,
    LOW,
    MEDIUM,
    HIGH;

    companion object {
        fun fromString(value: String): AnimationIntensity {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: MEDIUM
        }
    }
}

data class ThemeSpec(
    val id: String,
    val name: String,
    val subtitle: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val accentColor: Color,
    val backgroundColor: Color,
    val surfaceColor: Color,
    val cardBackgroundColor: Color,
    val textColor: Color,
    val secondaryTextColor: Color,
    val gridLineColor: Color
)
