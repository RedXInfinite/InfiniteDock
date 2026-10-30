package com.infinitedock.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.infinitedock.app.theme.InfiniteDockTheme
import com.infinitedock.app.ui.MainViewModel
import com.infinitedock.app.ui.components.AnimatedThemeBackground
import com.infinitedock.app.ui.screens.ConnectionManagerScreen
import com.infinitedock.app.ui.screens.DesktopModeScreen
import com.infinitedock.app.ui.screens.FloatingDockSettingsScreen
import com.infinitedock.app.ui.screens.HomeScreen
import com.infinitedock.app.ui.screens.ThemeSelectorScreen

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            val desktopShortcuts by viewModel.desktopShortcutsFlow.collectAsState()

            InfiniteDockTheme(
                themeSpec = uiState.currentTheme,
                animationIntensity = uiState.animationIntensity
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = uiState.currentTheme.backgroundColor
                ) {
                    val navController = rememberNavController()

                    // Background Animation Container for main screens
                    AnimatedThemeBackground(
                        themeSpec = uiState.currentTheme,
                        intensity = uiState.animationIntensity,
                        modifier = Modifier.fillMaxSize()
                    )

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(
                                uiState = uiState,
                                onNavigateToThemeSelector = { navController.navigate("theme_selector") },
                                onNavigateToDockSettings = { navController.navigate("dock_settings") },
                                onNavigateToDesktopMode = { navController.navigate("desktop_mode") },
                                onNavigateToConnectionManager = { navController.navigate("connection_manager") }
                            )
                        }

                        composable("theme_selector") {
                            ThemeSelectorScreen(
                                uiState = uiState,
                                onSelectTheme = { themeId -> viewModel.selectTheme(themeId) },
                                onSetAnimationIntensity = { intensity -> viewModel.setAnimationIntensity(intensity) },
                                onBackClick = { navController.popBackStack() }
                            )
                        }

                        composable("dock_settings") {
                            FloatingDockSettingsScreen(
                                uiState = uiState,
                                onUpdateDockSettings = { enabled, sizeDp, alpha ->
                                    viewModel.updateDockSettings(enabled, sizeDp, alpha)
                                },
                                onBackClick = { navController.popBackStack() }
                            )
                        }

                        composable("desktop_mode") {
                            DesktopModeScreen(
                                uiState = uiState,
                                shortcuts = desktopShortcuts,
                                onExitDesktop = { navController.popBackStack() }
                            )
                        }

                        composable("connection_manager") {
                            ConnectionManagerScreen(
                                uiState = uiState,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
