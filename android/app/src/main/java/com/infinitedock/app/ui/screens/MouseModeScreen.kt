package com.infinitedock.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infinitedock.app.theme.LocalThemeSpec
import com.infinitedock.app.ui.MainUiState
import com.infinitedock.app.ui.components.RetroHeader

@Composable
fun MouseModeScreen(uiState: MainUiState) {
    val theme = LocalThemeSpec.current
    val context = LocalContext.current

    var cursorX by remember { mutableFloatStateOf(0f) }
    var cursorY by remember { mutableFloatStateOf(0f) }

    Column(modifier = Modifier.fillMaxSize()) {
        RetroHeader(
            title = "REMOTE MOUSE TOUCHPAD",
            subtitle = "HIGH-PRECISION GESTURE SURFACE :: DESKTOP-01"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Touchpad & Scroll Strip Row
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Touchpad Surface
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clip(CutCornerShape(12.dp))
                        .background(theme.surfaceColor.copy(alpha = 0.85f))
                        .border(BorderStroke(1.5.dp, theme.primaryColor), CutCornerShape(12.dp))
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                cursorX += dragAmount.x
                                cursorY += dragAmount.y
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "TOUCHPAD SURFACE",
                            color = theme.primaryColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "ΔX: ${cursorX.toInt()}  ΔY: ${cursorY.toInt()}",
                            color = theme.secondaryTextColor,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                // Vertical Scroll Strip
                Box(
                    modifier = Modifier
                        .width(54.dp)
                        .fillMaxSize()
                        .clip(CutCornerShape(12.dp))
                        .background(theme.cardBackgroundColor)
                        .border(BorderStroke(1.5.dp, theme.gridLineColor), CutCornerShape(12.dp))
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                cursorY += dragAmount.y
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "▲\nS\nC\nR\nO\nL\nL\n▼",
                        color = theme.secondaryTextColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            // Mouse Click Buttons (L / R)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clip(CutCornerShape(bottomStart = 10.dp))
                        .background(theme.primaryColor.copy(alpha = 0.25f))
                        .border(BorderStroke(1.5.dp, theme.primaryColor), CutCornerShape(bottomStart = 10.dp))
                        .clickable {
                            Toast.makeText(context, "► Mouse Left Click Sent", Toast.LENGTH_SHORT).show()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "LEFT CLICK (L)",
                        color = theme.textColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clip(CutCornerShape(bottomEnd = 10.dp))
                        .background(theme.primaryColor.copy(alpha = 0.25f))
                        .border(BorderStroke(1.5.dp, theme.primaryColor), CutCornerShape(bottomEnd = 10.dp))
                        .clickable {
                            Toast.makeText(context, "► Mouse Right Click Sent", Toast.LENGTH_SHORT).show()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "RIGHT CLICK (R)",
                        color = theme.textColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
