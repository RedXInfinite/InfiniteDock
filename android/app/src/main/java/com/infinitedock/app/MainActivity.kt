package com.infinitedock.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.infinitedock.app.theme.InfiniteDockTheme
import com.infinitedock.app.ui.MainViewModel
import com.infinitedock.app.ui.components.AnimatedThemeBackground
import com.infinitedock.app.ui.navigation.RemoteBottomNavigationBar
import com.infinitedock.app.ui.navigation.RemoteNavMode
import com.infinitedock.app.ui.screens.GameControllerScreen
import com.infinitedock.app.ui.screens.KeyboardModeScreen
import com.infinitedock.app.ui.screens.MouseModeScreen
import com.infinitedock.app.ui.screens.RemoteDockScreen
import com.infinitedock.app.ui.screens.SettingsConnectionScreen

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            val desktopShortcuts by viewModel.desktopShortcutsFlow.collectAsState()

            var currentNavMode by remember { mutableStateOf(RemoteNavMode.REMOTE_DOCK) }

            InfiniteDockTheme(
                themeSpec = uiState.currentTheme,
                animationIntensity = uiState.animationIntensity
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = uiState.currentTheme.backgroundColor,
                    bottomBar = {
                        RemoteBottomNavigationBar(
                            currentMode = currentNavMode,
                            onSelectMode = { currentNavMode = it }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AnimatedThemeBackground(
                            themeSpec = uiState.currentTheme,
                            intensity = uiState.animationIntensity,
                            modifier = Modifier.fillMaxSize()
                        )

                        when (currentNavMode) {
                            RemoteNavMode.REMOTE_DOCK -> {
                                RemoteDockScreen(
                                    uiState = uiState,
                                    shortcuts = desktopShortcuts
                                )
                            }
                            RemoteNavMode.MOUSE_MODE -> {
                                MouseModeScreen(
                                    uiState = uiState
                                )
                            }
                            RemoteNavMode.KEYBOARD_MODE -> {
                                KeyboardModeScreen(
                                    uiState = uiState
                                )
                            }
                            RemoteNavMode.GAME_CONTROLLER -> {
                                GameControllerScreen(
                                    uiState = uiState
                                )
                            }
                            RemoteNavMode.SETTINGS_PORTAL -> {
                                SettingsConnectionScreen(
                                    uiState = uiState,
                                    onSelectTheme = { themeId -> viewModel.selectTheme(themeId) },
                                    onSetAnimationIntensity = { intensity -> viewModel.setAnimationIntensity(intensity) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
