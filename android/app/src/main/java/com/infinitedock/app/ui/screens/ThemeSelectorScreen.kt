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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.infinitedock.app.ui.components.RetroCard
import com.infinitedock.app.ui.components.RetroHeader

@Composable
fun ThemeSelectorScreen(
    uiState: MainUiState,
    onSelectTheme: (String) -> Unit,
    onSetAnimationIntensity: (AnimationIntensity) -> Unit,
    onBackClick: () -> Unit
) {
    val currentTheme = LocalThemeSpec.current

    Column(modifier = Modifier.fillMaxSize()) {
        RetroHeader(
            title = "THEME MATRIX & ANIMATION",
            subtitle = "7 SIGNATURE VISUAL IDENTITIES",
            onBackClick = onBackClick
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Global Animation Intensity Selector
                RetroCard {
                    Text(
                        text = "GLOBAL ANIMATION INTENSITY",
                        color = currentTheme.secondaryTextColor,
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
                            val bg = if (isSelected) currentTheme.primaryColor else currentTheme.cardBackgroundColor
                            val textColor = if (isSelected) currentTheme.backgroundColor else currentTheme.textColor

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                                    .clip(CutCornerShape(6.dp))
                                    .background(bg)
                                    .border(
                                        BorderStroke(
                                            1.dp,
                                            if (isSelected) currentTheme.primaryColor else currentTheme.gridLineColor
                                        ),
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

            item {
                Text(
                    text = "SELECT THEME ENGINE",
                    color = currentTheme.primaryColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 8.dp)
                )
            }

            items(ThemeRegistry.allThemes) { themeSpec ->
                ThemePreviewCard(
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
private fun ThemePreviewCard(
    themeSpec: ThemeSpec,
    isSelected: Boolean,
    animationIntensity: AnimationIntensity,
    onSelect: () -> Unit
) {
    val borderCol = if (isSelected) themeSpec.primaryColor else LocalThemeSpec.current.gridLineColor

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
            // Live Animated Preview Banner Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            ) {
                AnimatedThemeBackground(
                    themeSpec = themeSpec,
                    intensity = animationIntensity,
                    modifier = Modifier.fillMaxSize()
                )

                // Theme Badge Header on top of preview
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
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(themeSpec.primaryColor)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "ACTIVE",
                                color = themeSpec.backgroundColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }

            // Theme Palette & Description Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(themeSpec.surfaceColor)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = themeSpec.subtitle,
                        color = themeSpec.textColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(RoundedCornerShape(50))
                            .background(themeSpec.primaryColor)
                            .border(1.dp, themeSpec.textColor.copy(alpha = 0.5f), RoundedCornerShape(50))
                    )
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(RoundedCornerShape(50))
                            .background(themeSpec.secondaryColor)
                            .border(1.dp, themeSpec.textColor.copy(alpha = 0.5f), RoundedCornerShape(50))
                    )
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(RoundedCornerShape(50))
                            .background(themeSpec.accentColor)
                            .border(1.dp, themeSpec.textColor.copy(alpha = 0.5f), RoundedCornerShape(50))
                    )
                }
            }
        }
    }
}
