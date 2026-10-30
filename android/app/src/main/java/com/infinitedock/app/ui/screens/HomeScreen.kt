package com.infinitedock.app.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infinitedock.app.theme.LocalThemeSpec
import com.infinitedock.app.ui.MainUiState
import com.infinitedock.app.ui.components.RetroCard
import com.infinitedock.app.ui.components.RetroHeader

@Composable
fun HomeScreen(
    uiState: MainUiState,
    onNavigateToThemeSelector: () -> Unit,
    onNavigateToDockSettings: () -> Unit,
    onNavigateToDesktopMode: () -> Unit,
    onNavigateToConnectionManager: () -> Unit
) {
    val theme = LocalThemeSpec.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        RetroHeader(
            title = "INFINITE DOCK",
            subtitle = "HANDHELD OS :: STAGE 1 FOUNDATION"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // System Status HUD Card
            RetroCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "ACTIVE SYSTEM MATRIX",
                            color = theme.secondaryTextColor,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = theme.name,
                            color = theme.textColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = theme.subtitle,
                            color = theme.secondaryTextColor,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    // Palette Indicator Bulbs
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(RoundedCornerShape(50))
                                .background(theme.primaryColor)
                                .border(1.dp, theme.textColor.copy(alpha = 0.5f), RoundedCornerShape(50))
                        )
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(RoundedCornerShape(50))
                                .background(theme.accentColor)
                                .border(1.dp, theme.textColor.copy(alpha = 0.5f), RoundedCornerShape(50))
                        )
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(RoundedCornerShape(50))
                                .background(theme.cardBackgroundColor)
                                .border(1.dp, theme.textColor.copy(alpha = 0.5f), RoundedCornerShape(50))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Stats Metrics Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricChip(label = "ANIMATION", value = uiState.animationIntensity.name)
                    MetricChip(
                        label = "FLOATING DOCK",
                        value = if (uiState.isDockEnabled) "ONLINE" else "STANDBY"
                    )
                    MetricChip(
                        label = "COMPANION",
                        value = if (uiState.isPairedWithCompanion) "LINKED" else "UNPAIRED"
                    )
                }
            }

            Text(
                text = "OPERATING SYSTEM MODULES",
                color = theme.primaryColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(start = 4.dp, top = 8.dp)
            )

            // Module Navigation Buttons
            RetroButton(
                text = "Theme Selector Engine",
                subtitle = "7 Signature themes & animated live previews",
                icon = "❖",
                onClick = onNavigateToThemeSelector
            )

            RetroButton(
                text = "Floating Dock Settings",
                subtitle = "Overlay behavior, size, transparency & service",
                icon = "⚡",
                onClick = onNavigateToDockSettings
            )

            RetroButton(
                text = "Desktop Mode Shell",
                subtitle = "Full-screen workspace, launcher & taskbar",
                icon = "⬡",
                onClick = onNavigateToDesktopMode
            )

            RetroButton(
                text = "Connection Manager",
                subtitle = "Windows companion pairing & status",
                icon = "⇄",
                onClick = onNavigateToConnectionManager
            )
        }
    }
}

@Composable
private fun RetroButton(
    text: String,
    subtitle: String,
    icon: String,
    onClick: () -> Unit
) {
    val theme = LocalThemeSpec.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp))
            .background(theme.surfaceColor.copy(alpha = 0.85f))
            .border(
                BorderStroke(1.2.dp, theme.primaryColor.copy(alpha = 0.6f)),
                shape = CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp)
            )
            .padding(14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CutCornerShape(6.dp))
                        .background(theme.primaryColor.copy(alpha = 0.2f))
                        .border(1.dp, theme.primaryColor, CutCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = icon,
                        color = theme.primaryColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = text.uppercase(),
                        color = theme.textColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = subtitle,
                        color = theme.secondaryTextColor,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(CutCornerShape(4.dp))
                    .background(theme.primaryColor.copy(alpha = 0.2f))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "OPEN ►",
                    color = theme.primaryColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp))
                .border(
                    BorderStroke(1.2.dp, theme.primaryColor.copy(alpha = 0.6f)),
                    CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp)
                )
                .clickable { onClick() }
        )
    }
}

@Composable
private fun MetricChip(label: String, value: String) {
    val theme = LocalThemeSpec.current

    Column(
        modifier = Modifier
            .clip(CutCornerShape(6.dp))
            .background(theme.cardBackgroundColor)
            .border(0.8.dp, theme.gridLineColor, CutCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = theme.secondaryTextColor,
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = value,
            color = theme.primaryColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}
