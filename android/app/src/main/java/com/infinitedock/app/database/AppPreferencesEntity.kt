package com.infinitedock.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_preferences")
data class AppPreferencesEntity(
    @PrimaryKey
    val id: Int = 1,
    val selectedThemeId: String = "voltstrike", // "crimson_core", "voltstrike", "inferno_pulse", "tidal_surge", "verdant_bloom", "shadow_protocol", "aurora_dawn"
    val animationIntensity: String = "MEDIUM", // "OFF", "LOW", "MEDIUM", "HIGH"
    val isDockEnabled: Boolean = false,
    val dockSizeDp: Int = 56,
    val dockAlpha: Float = 0.9f,
    val isDockHorizontal: Boolean = true,
    val isPairedWithCompanion: Boolean = false,
    val companionDeviceName: String? = null
)
