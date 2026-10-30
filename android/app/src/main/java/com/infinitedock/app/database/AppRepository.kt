package com.infinitedock.app.database

import kotlinx.coroutines.flow.Flow

class AppRepository(private val database: AppDatabase) {

    val preferences: Flow<AppPreferencesEntity?> = database.appPreferencesDao().getPreferences()
    val allShortcuts: Flow<List<ShortcutEntity>> = database.shortcutDao().getAllShortcuts()
    val dockShortcuts: Flow<List<ShortcutEntity>> = database.shortcutDao().getDockShortcuts()
    val desktopShortcuts: Flow<List<ShortcutEntity>> = database.shortcutDao().getDesktopShortcuts()

    suspend fun getPreferencesSync(): AppPreferencesEntity {
        return database.appPreferencesDao().getPreferencesSync() ?: AppPreferencesEntity()
    }

    suspend fun updateTheme(themeId: String) {
        val current = getPreferencesSync()
        database.appPreferencesDao().savePreferences(current.copy(selectedThemeId = themeId))
    }

    suspend fun updateAnimationIntensity(intensity: String) {
        val current = getPreferencesSync()
        database.appPreferencesDao().savePreferences(current.copy(animationIntensity = intensity))
    }

    suspend fun updateDockSettings(enabled: Boolean, sizeDp: Int, alpha: Float) {
        val current = getPreferencesSync()
        database.appPreferencesDao().savePreferences(
            current.copy(
                isDockEnabled = enabled,
                dockSizeDp = sizeDp,
                dockAlpha = alpha
            )
        )
    }

    suspend fun updateDockEnabled(enabled: Boolean) {
        val current = getPreferencesSync()
        database.appPreferencesDao().savePreferences(current.copy(isDockEnabled = enabled))
    }

    suspend fun addShortcut(shortcut: ShortcutEntity) {
        database.shortcutDao().insertShortcut(shortcut)
    }

    suspend fun deleteShortcut(shortcut: ShortcutEntity) {
        database.shortcutDao().deleteShortcut(shortcut)
    }
}
