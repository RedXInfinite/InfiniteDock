package com.infinitedock.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shortcuts")
data class ShortcutEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val activityName: String = "",
    val displayName: String,
    val iconUri: String? = null,
    val position: Int = 0,
    val shortcutType: String = "APP", // "APP", "SYSTEM", "ACTION"
    val isDockShortcut: Boolean = true,
    val isDesktopShortcut: Boolean = true
)
