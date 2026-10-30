package com.infinitedock.app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.infinitedock.app.database.AppDatabase
import com.infinitedock.app.database.AppPreferencesEntity
import com.infinitedock.app.database.AppRepository
import com.infinitedock.app.database.ShortcutEntity
import com.infinitedock.app.theme.AnimationIntensity
import com.infinitedock.app.theme.ThemeRegistry
import com.infinitedock.app.theme.ThemeSpec
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MainUiState(
    val currentTheme: ThemeSpec = ThemeRegistry.Voltstrike,
    val animationIntensity: AnimationIntensity = AnimationIntensity.MEDIUM,
    val isDockEnabled: Boolean = false,
    val dockSizeDp: Int = 56,
    val dockAlpha: Float = 0.9f,
    val isPairedWithCompanion: Boolean = false,
    val companionDeviceName: String? = null,
    val dockShortcuts: List<ShortcutEntity> = emptyList(),
    val desktopShortcuts: List<ShortcutEntity> = emptyList()
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AppRepository

    val uiState: StateFlow<MainUiState>
    val preferencesFlow: StateFlow<AppPreferencesEntity?>
    val dockShortcutsFlow: StateFlow<List<ShortcutEntity>>
    val desktopShortcutsFlow: StateFlow<List<ShortcutEntity>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = AppRepository(database)

        preferencesFlow = repository.preferences.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppPreferencesEntity()
        )

        dockShortcutsFlow = repository.dockShortcuts.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        desktopShortcutsFlow = repository.desktopShortcuts.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        uiState = repository.preferences.map { prefs ->
            val p = prefs ?: AppPreferencesEntity()
            MainUiState(
                currentTheme = ThemeRegistry.getThemeById(p.selectedThemeId),
                animationIntensity = AnimationIntensity.fromString(p.animationIntensity),
                isDockEnabled = p.isDockEnabled,
                dockSizeDp = p.dockSizeDp,
                dockAlpha = p.dockAlpha,
                isPairedWithCompanion = p.isPairedWithCompanion,
                companionDeviceName = p.companionDeviceName
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainUiState()
        )
    }

    fun selectTheme(themeId: String) {
        viewModelScope.launch {
            repository.updateTheme(themeId)
        }
    }

    fun setAnimationIntensity(intensity: AnimationIntensity) {
        viewModelScope.launch {
            repository.updateAnimationIntensity(intensity.name)
        }
    }

    fun updateDockSettings(enabled: Boolean, sizeDp: Int, alpha: Float) {
        viewModelScope.launch {
            repository.updateDockSettings(enabled, sizeDp, alpha)
        }
    }

    fun setDockEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateDockEnabled(enabled)
        }
    }

    fun addShortcut(shortcut: ShortcutEntity) {
        viewModelScope.launch {
            repository.addShortcut(shortcut)
        }
    }

    fun deleteShortcut(shortcut: ShortcutEntity) {
        viewModelScope.launch {
            repository.deleteShortcut(shortcut)
        }
    }
}
