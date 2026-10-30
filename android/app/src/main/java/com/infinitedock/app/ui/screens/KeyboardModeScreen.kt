package com.infinitedock.app.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infinitedock.app.theme.LocalThemeSpec
import com.infinitedock.app.ui.MainUiState
import com.infinitedock.app.ui.components.RetroButton
import com.infinitedock.app.ui.components.RetroCard
import com.infinitedock.app.ui.components.RetroHeader

@Composable
fun KeyboardModeScreen(uiState: MainUiState) {
    val theme = LocalThemeSpec.current
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }

    val quickModifiers = listOf(
        "ESC", "TAB", "CTRL", "ALT", "WIN", "SHIFT",
        "ENTER", "BACKSPACE", "DELETE", "SPACE", "UP", "DOWN",
        "LEFT", "RIGHT", "F1", "F5", "F11", "F12"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        RetroHeader(
            title = "REMOTE KEYBOARD INPUT",
            subtitle = "DIRECT TEXT & MODIFIER KEY STREAM :: DESKTOP-01"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Direct Text Input Field Card
            RetroCard {
                Text(
                    text = "REMOTE TEXT STREAM",
                    color = theme.secondaryTextColor,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "TYPE TEXT TO SEND TO WINDOWS PC...",
                            color = theme.secondaryTextColor,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = theme.primaryColor,
                        unfocusedBorderColor = theme.gridLineColor,
                        focusedTextColor = theme.textColor,
                        unfocusedTextColor = theme.textColor,
                        focusedContainerColor = theme.backgroundColor,
                        unfocusedContainerColor = theme.backgroundColor
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                RetroButton(
                    text = "SEND TEXT STREAM ►",
                    onClick = {
                        if (inputText.isNotBlank()) {
                            Toast.makeText(context, "► Sent text: \"$inputText\" to DESKTOP-01", Toast.LENGTH_SHORT).show()
                            inputText = ""
                        }
                    }
                )
            }

            // Quick Modifiers Grid
            RetroCard {
                Text(
                    text = "QUICK ACTION MODIFIERS & KEYS",
                    color = theme.primaryColor,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(quickModifiers) { key ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .clip(CutCornerShape(6.dp))
                                .background(theme.surfaceColor)
                                .border(BorderStroke(1.dp, theme.primaryColor.copy(alpha = 0.5f)), CutCornerShape(6.dp))
                                .clickable {
                                    Toast.makeText(context, "► Key Triggered: $key", Toast.LENGTH_SHORT).show()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = key,
                                color = theme.textColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }
}
