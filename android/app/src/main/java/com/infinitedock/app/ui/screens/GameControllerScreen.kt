package com.infinitedock.app.ui.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.infinitedock.app.ui.components.RetroCard
import com.infinitedock.app.ui.components.RetroHeader

enum class ControllerStyle {
    XBOX,
    PLAYSTATION
}

@Composable
fun GameControllerScreen(uiState: MainUiState) {
    val theme = LocalThemeSpec.current
    val context = LocalContext.current

    var selectedStyle by remember { mutableStateOf(ControllerStyle.XBOX) }
    var selectedProfile by remember { mutableStateOf("Need for Speed: Most Wanted") }
    var hapticRumbleEnabled by remember { mutableStateOf(true) }

    val profiles = listOf(
        "Need for Speed: Most Wanted",
        "Blur",
        "Minecraft (BlueStacks)"
    )

    fun triggerHaptic() {
        if (!hapticRumbleEnabled) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                @Suppress("DEPRECATION")
                vibrator?.vibrate(30)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        RetroHeader(
            title = "GAME CONTROLLER OVERLAY",
            subtitle = "REMOTE GAMEPAD :: $selectedProfile"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Style Toggle & Profile Header Card
            RetroCard {
                Text(
                    text = "CONTROLLER LAYOUT STYLE",
                    color = theme.secondaryTextColor,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(CutCornerShape(6.dp))
                            .background(if (selectedStyle == ControllerStyle.XBOX) theme.primaryColor else theme.cardBackgroundColor)
                            .border(BorderStroke(1.dp, theme.primaryColor), CutCornerShape(6.dp))
                            .clickable { selectedStyle = ControllerStyle.XBOX },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "XBOX STYLE",
                            color = if (selectedStyle == ControllerStyle.XBOX) theme.backgroundColor else theme.textColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(CutCornerShape(6.dp))
                            .background(if (selectedStyle == ControllerStyle.PLAYSTATION) theme.primaryColor else theme.cardBackgroundColor)
                            .border(BorderStroke(1.dp, theme.primaryColor), CutCornerShape(6.dp))
                            .clickable { selectedStyle = ControllerStyle.PLAYSTATION },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "PLAYSTATION STYLE",
                            color = if (selectedStyle == ControllerStyle.PLAYSTATION) theme.backgroundColor else theme.textColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "GAME PROFILE",
                    color = theme.secondaryTextColor,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    profiles.forEach { profile ->
                        val isSelected = selectedProfile == profile
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(CutCornerShape(4.dp))
                                .background(if (isSelected) theme.primaryColor.copy(alpha = 0.3f) else theme.surfaceColor)
                                .border(BorderStroke(1.dp, if (isSelected) theme.primaryColor else theme.gridLineColor), CutCornerShape(4.dp))
                                .clickable { selectedProfile = profile }
                                .padding(vertical = 8.dp, horizontal = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = profile,
                                color = if (isSelected) theme.primaryColor else theme.textColor,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                maxLines = 1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "HAPTIC RUMBLE FEEDBACK",
                        color = theme.textColor,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Switch(
                        checked = hapticRumbleEnabled,
                        onCheckedChange = { hapticRumbleEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = theme.backgroundColor,
                            checkedTrackColor = theme.primaryColor
                        )
                    )
                }
            }

            // Gamepad Controls Overlay Box
            RetroCard {
                // Shoulders & Triggers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val l1 = if (selectedStyle == ControllerStyle.XBOX) "LB" else "L1"
                    val l2 = if (selectedStyle == ControllerStyle.XBOX) "LT" else "L2"
                    val r1 = if (selectedStyle == ControllerStyle.XBOX) "RB" else "R1"
                    val r2 = if (selectedStyle == ControllerStyle.XBOX) "RT" else "R2"

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TriggerButton(label = l2) { triggerHaptic(); Toast.makeText(context, "► $l2 Trigger", Toast.LENGTH_SHORT).show() }
                        TriggerButton(label = l1) { triggerHaptic(); Toast.makeText(context, "► $l1 Bumper", Toast.LENGTH_SHORT).show() }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TriggerButton(label = r1) { triggerHaptic(); Toast.makeText(context, "► $r1 Bumper", Toast.LENGTH_SHORT).show() }
                        TriggerButton(label = r2) { triggerHaptic(); Toast.makeText(context, "► $r2 Trigger", Toast.LENGTH_SHORT).show() }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // D-Pad and Face Buttons Area
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // D-Pad Cross
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TriggerButton(label = "▲") { triggerHaptic(); Toast.makeText(context, "► D-PAD UP", Toast.LENGTH_SHORT).show() }
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            TriggerButton(label = "◄") { triggerHaptic(); Toast.makeText(context, "► D-PAD LEFT", Toast.LENGTH_SHORT).show() }
                            TriggerButton(label = "►") { triggerHaptic(); Toast.makeText(context, "► D-PAD RIGHT", Toast.LENGTH_SHORT).show() }
                        }
                        TriggerButton(label = "▼") { triggerHaptic(); Toast.makeText(context, "► D-PAD DOWN", Toast.LENGTH_SHORT).show() }
                    }

                    // Virtual Analog Stick Hub
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(50))
                            .background(theme.backgroundColor)
                            .border(BorderStroke(1.5.dp, theme.primaryColor), RoundedCornerShape(50)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "LS / RS",
                            color = theme.primaryColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    // Face Buttons Cluster
                    val faceTop = if (selectedStyle == ControllerStyle.XBOX) "Y" else "▲"
                    val faceLeft = if (selectedStyle == ControllerStyle.XBOX) "X" else "■"
                    val faceRight = if (selectedStyle == ControllerStyle.XBOX) "B" else "●"
                    val faceBottom = if (selectedStyle == ControllerStyle.XBOX) "A" else "✖"

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TriggerButton(label = faceTop) { triggerHaptic(); Toast.makeText(context, "► Button $faceTop", Toast.LENGTH_SHORT).show() }
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            TriggerButton(label = faceLeft) { triggerHaptic(); Toast.makeText(context, "► Button $faceLeft", Toast.LENGTH_SHORT).show() }
                            TriggerButton(label = faceRight) { triggerHaptic(); Toast.makeText(context, "► Button $faceRight", Toast.LENGTH_SHORT).show() }
                        }
                        TriggerButton(label = faceBottom) { triggerHaptic(); Toast.makeText(context, "► Button $faceBottom", Toast.LENGTH_SHORT).show() }
                    }
                }
            }
        }
    }
}

@Composable
private fun TriggerButton(label: String, onClick: () -> Unit) {
    val theme = LocalThemeSpec.current

    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CutCornerShape(6.dp))
            .background(theme.surfaceColor)
            .border(BorderStroke(1.2.dp, theme.primaryColor), CutCornerShape(6.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = theme.textColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}
