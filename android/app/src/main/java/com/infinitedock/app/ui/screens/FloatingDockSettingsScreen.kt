package com.infinitedock.app.ui.screens

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.infinitedock.app.service.FloatingDockService
import com.infinitedock.app.theme.LocalThemeSpec
import com.infinitedock.app.ui.MainUiState
import com.infinitedock.app.ui.components.RetroButton
import com.infinitedock.app.ui.components.RetroCard
import com.infinitedock.app.ui.components.RetroHeader

@Composable
fun FloatingDockSettingsScreen(
    uiState: MainUiState,
    onUpdateDockSettings: (enabled: Boolean, sizeDp: Int, alpha: Float) -> Unit,
    onBackClick: () -> Unit
) {
    val theme = LocalThemeSpec.current
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasOverlayPermission by remember {
        mutableStateOf(Settings.canDrawOverlays(context))
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                hasOverlayPermission = Settings.canDrawOverlays(context)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        RetroHeader(
            title = "FLOATING DOCK CONFIG",
            subtitle = "OVERLAY SERVICE & BEHAVIOR",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Live Dock Preview Box
            RetroCard {
                Text(
                    text = "DOCK PREVIEW",
                    color = theme.secondaryTextColor,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(CutCornerShape(8.dp))
                        .background(theme.backgroundColor)
                        .border(1.dp, theme.gridLineColor, CutCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Render Mock Dock
                    Row(
                        modifier = Modifier
                            .height(uiState.dockSizeDp.dp)
                            .clip(CutCornerShape(8.dp))
                            .background(theme.surfaceColor.copy(alpha = uiState.dockAlpha))
                            .border(1.dp, theme.primaryColor, CutCornerShape(8.dp))
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val mockSymbols = listOf("⚙", "🌐", "▶", "⚡")
                        for (symbol in mockSymbols) {
                            Box(
                                modifier = Modifier
                                    .size((uiState.dockSizeDp * 0.65f).dp)
                                    .clip(CutCornerShape(4.dp))
                                    .background(theme.primaryColor.copy(alpha = 0.2f))
                                    .border(1.dp, theme.primaryColor, CutCornerShape(4.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = symbol,
                                    color = theme.primaryColor,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            // Permission Status Card
            RetroCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "SYSTEM OVERLAY PERMISSION",
                            color = theme.secondaryTextColor,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (hasOverlayPermission) "GRANTED" else "PERMISSION REQUIRED",
                            color = if (hasOverlayPermission) theme.primaryColor else theme.textColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    if (!hasOverlayPermission) {
                        RetroButton(
                            text = "GRANT",
                            onClick = {
                                val intent = Intent(
                                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                    Uri.parse("package:${context.packageName}")
                                )
                                context.startActivity(intent)
                            },
                            modifier = Modifier.width(100.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(theme.primaryColor)
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "READY",
                                color = theme.backgroundColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }

            // Enable Service Switch Card
            RetroCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "FLOATING OVERLAY SERVICE",
                            color = theme.textColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = if (uiState.isDockEnabled) "Service active in background" else "Service offline",
                            color = theme.secondaryTextColor,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Switch(
                        checked = uiState.isDockEnabled,
                        onCheckedChange = { isChecked ->
                            if (isChecked && !hasOverlayPermission) {
                                val intent = Intent(
                                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                    Uri.parse("package:${context.packageName}")
                                )
                                context.startActivity(intent)
                            } else {
                                onUpdateDockSettings(isChecked, uiState.dockSizeDp, uiState.dockAlpha)
                                if (isChecked) {
                                    FloatingDockService.startService(context)
                                } else {
                                    FloatingDockService.stopService(context)
                                }
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = theme.backgroundColor,
                            checkedTrackColor = theme.primaryColor,
                            uncheckedThumbColor = theme.secondaryTextColor,
                            uncheckedTrackColor = theme.cardBackgroundColor
                        )
                    )
                }
            }

            // Controls: Size & Transparency
            RetroCard {
                Text(
                    text = "DOCK SIZE (${uiState.dockSizeDp} DP)",
                    color = theme.secondaryTextColor,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Slider(
                    value = uiState.dockSizeDp.toFloat(),
                    onValueChange = { size ->
                        onUpdateDockSettings(uiState.isDockEnabled, size.toInt(), uiState.dockAlpha)
                    },
                    valueRange = 40f..80f,
                    steps = 8,
                    colors = SliderDefaults.colors(
                        thumbColor = theme.primaryColor,
                        activeTrackColor = theme.primaryColor,
                        inactiveTrackColor = theme.gridLineColor
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "DOCK TRANSPARENCY (${(uiState.dockAlpha * 100).toInt()}%)",
                    color = theme.secondaryTextColor,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Slider(
                    value = uiState.dockAlpha,
                    onValueChange = { alpha ->
                        onUpdateDockSettings(uiState.isDockEnabled, uiState.dockSizeDp, alpha)
                    },
                    valueRange = 0.2f..1.0f,
                    steps = 8,
                    colors = SliderDefaults.colors(
                        thumbColor = theme.primaryColor,
                        activeTrackColor = theme.primaryColor,
                        inactiveTrackColor = theme.gridLineColor
                    )
                )
            }
        }
    }
}
