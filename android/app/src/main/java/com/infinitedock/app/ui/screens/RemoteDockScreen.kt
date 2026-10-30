package com.infinitedock.app.ui.screens

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infinitedock.app.database.ShortcutEntity
import com.infinitedock.app.theme.LocalThemeSpec
import com.infinitedock.app.ui.MainUiState
import com.infinitedock.app.ui.components.RetroHeader

@Composable
fun RemoteDockScreen(
    uiState: MainUiState,
    shortcuts: List<ShortcutEntity>
) {
    val theme = LocalThemeSpec.current
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var activePageIndex by remember { mutableStateOf(0) }

    val filteredShortcuts = remember(shortcuts, searchQuery) {
        if (searchQuery.isBlank()) shortcuts
        else shortcuts.filter { it.displayName.contains(searchQuery, ignoreCase = true) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        RetroHeader(
            title = "REMOTE STREAM DOCK",
            subtitle = if (uiState.isPairedWithCompanion) "TARGET: DESKTOP-01 (ONLINE)" else "TARGET: DESKTOP-01 (OFFLINE / DISCONNECTED)"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Search Header Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "SEARCH WINDOWS SHORTCUTS...",
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
                    focusedContainerColor = theme.surfaceColor,
                    unfocusedContainerColor = theme.surfaceColor
                )
            )

            // Page Indicator Selector Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DOCK MATRIX PAGE ${activePageIndex + 1} / 3",
                    color = theme.primaryColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    (0..2).forEach { pageIdx ->
                        Box(
                            modifier = Modifier
                                .size(if (activePageIndex == pageIdx) 12.dp else 8.dp)
                                .clip(RoundedCornerShape(50))
                                .background(if (activePageIndex == pageIdx) theme.primaryColor else theme.gridLineColor)
                                .clickable { activePageIndex = pageIdx }
                        )
                    }
                }
            }

            // Stream Deck Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredShortcuts) { shortcut ->
                    StreamDeckButton(
                        shortcut = shortcut,
                        isOnline = uiState.isPairedWithCompanion,
                        onClick = {
                            if (uiState.isPairedWithCompanion) {
                                Toast.makeText(
                                    context,
                                    "► Remote Command Sent: Launch ${shortcut.displayName} on DESKTOP-01",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "⚠ Disconnected: Connect to Windows PC in Settings to trigger ${shortcut.displayName}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun StreamDeckButton(
    shortcut: ShortcutEntity,
    isOnline: Boolean,
    onClick: () -> Unit
) {
    val theme = LocalThemeSpec.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(CutCornerShape(10.dp))
            .background(theme.surfaceColor.copy(alpha = 0.9f))
            .border(
                BorderStroke(
                    1.5.dp,
                    if (isOnline) theme.primaryColor else theme.gridLineColor
                ),
                CutCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
                    text = shortcut.displayName.firstOrNull()?.uppercase() ?: "W",
                    color = theme.primaryColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = shortcut.displayName.uppercase(),
                color = theme.textColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}
