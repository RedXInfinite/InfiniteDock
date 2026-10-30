package com.infinitedock.app.ui.screens

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
fun ConnectionManagerScreen(
    uiState: MainUiState,
    onBackClick: () -> Unit
) {
    val theme = LocalThemeSpec.current

    Column(modifier = Modifier.fillMaxSize()) {
        RetroHeader(
            title = "CONNECTION MANAGER",
            subtitle = "WINDOWS COMPANION PAIRING",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Status Card
            RetroCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "COMPANION PAIRING STATUS",
                            color = theme.secondaryTextColor,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (uiState.isPairedWithCompanion) "CONNECTED" else "UNPAIRED",
                            color = if (uiState.isPairedWithCompanion) theme.primaryColor else theme.textColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (uiState.isPairedWithCompanion) theme.primaryColor else theme.cardBackgroundColor)
                            .border(1.dp, theme.primaryColor, RoundedCornerShape(50))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (uiState.isPairedWithCompanion) "LINKED" else "OFFLINE",
                            color = if (uiState.isPairedWithCompanion) theme.backgroundColor else theme.primaryColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            // Security Architecture Note Card
            RetroCard {
                Text(
                    text = "SECURITY & PAIRING PROTOCOL",
                    color = theme.primaryColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Stage 1 establishes the UI foundation and secure token data structures. Remote network and Bluetooth socket listeners remain strictly inactive in Stage 1 to guarantee system security without authorized user pairing.",
                    color = theme.textColor,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    lineHeight = 18.sp
                )
            }

            // Pairing Token UI Foundation Card
            RetroCard {
                Text(
                    text = "PAIRING CODE FOUNDATION",
                    color = theme.secondaryTextColor,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(CutCornerShape(8.dp))
                        .background(theme.backgroundColor)
                        .border(1.dp, theme.primaryColor.copy(alpha = 0.5f), CutCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "8F92 - 4D10 - INF8",
                        color = theme.primaryColor,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 2.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Enter this token into the InfiniteDock Windows Companion App (Planned for Stage 3) to establish end-to-end encrypted local sync.",
                    color = theme.secondaryTextColor,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}
