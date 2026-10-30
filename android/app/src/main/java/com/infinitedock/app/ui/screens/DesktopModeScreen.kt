package com.infinitedock.app.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infinitedock.app.database.ShortcutEntity
import com.infinitedock.app.theme.LocalAnimationIntensity
import com.infinitedock.app.theme.LocalThemeSpec
import com.infinitedock.app.ui.MainUiState
import com.infinitedock.app.ui.components.AnimatedThemeBackground

@Composable
fun DesktopModeScreen(
    uiState: MainUiState,
    shortcuts: List<ShortcutEntity>,
    onExitDesktop: () -> Unit
) {
    val theme = LocalThemeSpec.current
    val intensity = LocalAnimationIntensity.current
    val context = LocalContext.current

    val installedApps = remember(shortcuts) {
        val pm = context.packageManager
        shortcuts.map { entity ->
            DesktopAppItem(
                entity = entity,
                hasInstalledApp = try {
                    pm.getLaunchIntentForPackage(entity.packageName) != null
                } catch (e: Exception) {
                    false
                }
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Wallpaper / Theme Animation Background
        AnimatedThemeBackground(
            themeSpec = theme,
            intensity = intensity,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Desktop Top Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(theme.surfaceColor.copy(alpha = 0.9f))
                    .border(
                        BorderStroke(1.dp, theme.gridLineColor),
                        CutCornerShape(bottomEnd = 8.dp)
                    )
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(50))
                            .background(theme.primaryColor)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "INFINITE DESKTOP OS",
                        color = theme.textColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                // Exit Desktop Button
                Box(
                    modifier = Modifier
                        .clip(CutCornerShape(4.dp))
                        .background(theme.primaryColor.copy(alpha = 0.2f))
                        .border(1.dp, theme.primaryColor, CutCornerShape(4.dp))
                        .clickable { onExitDesktop() }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "EXIT DESKTOP ✖",
                        color = theme.primaryColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            // Desktop App Icon Grid Workspace
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 90.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(installedApps) { app ->
                    DesktopIconCell(
                        app = app,
                        onClick = { launchApp(context, app.entity.packageName) }
                    )
                }
            }

            // Bottom Taskbar / Dock
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(theme.surfaceColor.copy(alpha = 0.95f))
                    .border(
                        BorderStroke(1.5.dp, theme.primaryColor.copy(alpha = 0.6f)),
                        CutCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    )
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Start Menu Badge Button
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CutCornerShape(6.dp))
                            .background(theme.primaryColor)
                            .clickable {
                                Toast.makeText(context, "InfiniteDock System Menu", Toast.LENGTH_SHORT).show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "❖",
                            color = theme.backgroundColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Taskbar Shortcuts
                    shortcuts.take(4).forEach { shortcut ->
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CutCornerShape(4.dp))
                                .background(theme.primaryColor.copy(alpha = 0.15f))
                                .border(1.dp, theme.gridLineColor, CutCornerShape(4.dp))
                                .clickable { launchApp(context, shortcut.packageName) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = shortcut.displayName.firstOrNull()?.toString() ?: "A",
                                color = theme.primaryColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                // Clock / Status System Tray
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "12:00",
                        color = theme.textColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = if (uiState.isPairedWithCompanion) "LINKED" else "OFFLINE",
                        color = theme.secondaryTextColor,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

private data class DesktopAppItem(
    val entity: ShortcutEntity,
    val hasInstalledApp: Boolean
)

@Composable
private fun DesktopIconCell(
    app: DesktopAppItem,
    onClick: () -> Unit
) {
    val theme = LocalThemeSpec.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(84.dp)
            .clip(CutCornerShape(8.dp))
            .background(theme.cardBackgroundColor.copy(alpha = 0.7f))
            .border(1.dp, theme.gridLineColor, CutCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CutCornerShape(6.dp))
                .background(theme.primaryColor.copy(alpha = 0.2f))
                .border(1.dp, theme.primaryColor, CutCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = app.entity.displayName.firstOrNull()?.uppercase() ?: "?",
                color = theme.primaryColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = app.entity.displayName,
            color = theme.textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

private fun launchApp(context: Context, packageName: String) {
    try {
        val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            context.startActivity(launchIntent)
        } else {
            Toast.makeText(context, "App $packageName not installed", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Could not launch package: $packageName", Toast.LENGTH_SHORT).show()
    }
}
