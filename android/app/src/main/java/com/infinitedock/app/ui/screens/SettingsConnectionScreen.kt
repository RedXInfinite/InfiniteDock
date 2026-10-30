package com.infinitedock.app.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infinitedock.app.theme.AnimationIntensity
import com.infinitedock.app.theme.LocalThemeSpec
import com.infinitedock.app.theme.ThemeRegistry
import com.infinitedock.app.theme.ThemeSpec
import com.infinitedock.app.ui.MainUiState
import com.infinitedock.app.ui.components.AnimatedThemeBackground
import com.infinitedock.app.ui.components.RetroButton
import com.infinitedock.app.ui.components.RetroCard
import com.infinitedock.app.ui.components.RetroHeader

@Composable
fun SettingsConnectionScreen(
    uiState: MainUiState,
    onSelectTheme: (String) -> Unit,
    onSetAnimationIntensity: (AnimationIntensity) -> Unit
) {
    val theme = LocalThemeSpec.current
    var pinCode by remember { mutableStateOf("849201") }
    var isScanning by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        RetroHeader(
            title = "SETTINGS & HOST PAIRING",
            subtitle = "DESKTOP-01 CONNECTION PORTAL"
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Connection Status Header Card
            item {
                RetroCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "HOST CONNECTION STATUS",
                                color = theme.secondaryTextColor,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (uiState.isPairedWithCompanion) "CONNECTED TO DESKTOP-01" else "DISCONNECTED / OFFLINE MODE",
                                color = if (uiState.isPairedWithCompanion) theme.primaryColor else theme.textColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(RoundedCornerShape(50))
                                .background(if (uiState.isPairedWithCompanion) theme.primaryColor else theme.cardBackgroundColor)
                                .border(1.dp, theme.primaryColor, RoundedCornerShape(50))
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (isScanning) {
                        ScanningModal(theme = theme, onCancel = { isScanning = false })
                    } else {
                        RetroButton(
                            text = if (uiState.isPairedWithCompanion) "RE-SCAN HOST NETWORK" else "CONNECT TO WINDOWS PC ►",
                            onClick = { isScanning = true }
                        )
                    }
                }
            }

            // 6-Digit PIN Pairing Card
            item {
                RetroCard {
                    Text(
                        text = "SECURITY PAIRING PIN (6-DIGIT)",
                        color = theme.primaryColor,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = pinCode,
                        onValueChange = { if (it.length <= 6) pinCode = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "ENTER 6-DIGIT HOST PIN",
                                color = theme.secondaryTextColor,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp
                            )
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = theme.primaryColor,
                            unfocusedBorderColor = theme.gridLineColor,
                            focusedTextColor = theme.textColor,
                            unfocusedTextColor = theme.textColor,
                            focusedContainerColor = theme.backgroundColor,
                            unfocusedContainerColor = theme.backgroundColor
                        )
                    )
                }
            }

            // Global Animation Intensity Selector
            item {
                RetroCard {
                    Text(
                        text = "GLOBAL ANIMATION INTENSITY",
                        color = theme.secondaryTextColor,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (intensity in AnimationIntensity.entries) {
                            val isSelected = uiState.animationIntensity == intensity
                            val bg = if (isSelected) theme.primaryColor else theme.cardBackgroundColor
                            val textColor = if (isSelected) theme.backgroundColor else theme.textColor

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                                    .clip(CutCornerShape(6.dp))
                                    .background(bg)
                                    .border(
                                        BorderStroke(1.dp, if (isSelected) theme.primaryColor else theme.gridLineColor),
                                        CutCornerShape(6.dp)
                                    )
                                    .clickable { onSetAnimationIntensity(intensity) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = intensity.name,
                                    color = textColor,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }

            // All 8 Visual Themes Header
            item {
                Text(
                    text = "SELECT 8 SIGNATURE THEMES",
                    color = theme.primaryColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 8.dp)
                )
            }

            items(ThemeRegistry.allThemes) { themeSpec ->
                ThemeCardItem(
                    themeSpec = themeSpec,
                    isSelected = uiState.currentTheme.id == themeSpec.id,
                    animationIntensity = uiState.animationIntensity,
                    onSelect = { onSelectTheme(themeSpec.id) }
                )
            }
        }
    }
}

@Composable
private fun ScanningModal(theme: ThemeSpec, onCancel: () -> Unit) {
    val transition = rememberInfiniteTransition(label = "scan_pulse")
    val radius by transition.animateFloat(
        initialValue = 20f,
        targetValue = 70f,
        animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing), RepeatMode.Restart),
        label = "radius"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CutCornerShape(8.dp))
            .background(theme.backgroundColor)
            .border(BorderStroke(1.5.dp, theme.primaryColor), CutCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = theme.primaryColor.copy(alpha = 0.5f),
                    radius = radius,
                    style = Stroke(width = 3f)
                )
                drawCircle(
                    color = theme.primaryColor,
                    radius = 8f,
                    center = Offset(size.width / 2, size.height / 2)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "SCANNING LOCAL SUBNET FOR DESKTOP-01...",
            color = theme.primaryColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(12.dp))

        RetroButton(
            text = "CANCEL SCAN ✖",
            onClick = onCancel,
            isPrimary = false
        )
    }
}

@Composable
private fun ThemeCardItem(
    themeSpec: ThemeSpec,
    isSelected: Boolean,
    animationIntensity: AnimationIntensity,
    onSelect: () -> Unit
) {
    val activeTheme = LocalThemeSpec.current
    val borderCol = if (isSelected) themeSpec.primaryColor else activeTheme.gridLineColor

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CutCornerShape(topStart = 12.dp, bottomEnd = 12.dp))
            .background(themeSpec.backgroundColor)
            .border(
                BorderStroke(if (isSelected) 2.dp else 1.dp, borderCol),
                CutCornerShape(topStart = 12.dp, bottomEnd = 12.dp)
            )
            .clickable { onSelect() }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            ) {
                AnimatedThemeBackground(
                    themeSpec = themeSpec,
                    intensity = animationIntensity,
                    modifier = Modifier.fillMaxSize()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CutCornerShape(4.dp))
                            .background(themeSpec.surfaceColor.copy(alpha = 0.85f))
                            .border(1.dp, themeSpec.primaryColor, CutCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = themeSpec.name,
                            color = themeSpec.primaryColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(themeSpec.primaryColor)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "ACTIVE",
                                color = themeSpec.backgroundColor,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(themeSpec.surfaceColor)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = themeSpec.subtitle,
                    color = themeSpec.textColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(RoundedCornerShape(50))
                            .background(themeSpec.primaryColor)
                    )
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(RoundedCornerShape(50))
                            .background(themeSpec.secondaryColor)
                    )
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(RoundedCornerShape(50))
                            .background(themeSpec.accentColor)
                    )
                }
            }
        }
    }
}
