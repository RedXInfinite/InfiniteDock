package com.infinitedock.app.ui.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
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

@Composable
fun RemoteBottomNavigationBar(
    currentMode: RemoteNavMode,
    onSelectMode: (RemoteNavMode) -> Unit
) {
    val theme = LocalThemeSpec.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(theme.surfaceColor.copy(alpha = 0.95f))
            .border(
                BorderStroke(1.5.dp, theme.primaryColor.copy(alpha = 0.6f)),
                shape = CutCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )
            .padding(horizontal = 4.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RemoteNavMode.entries.forEach { mode ->
            val isSelected = currentMode == mode
            val itemBg = if (isSelected) theme.primaryColor.copy(alpha = 0.25f) else theme.surfaceColor.copy(alpha = 0.4f)
            val borderColor = if (isSelected) theme.primaryColor else theme.gridLineColor.copy(alpha = 0.3f)

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
                    .clip(CutCornerShape(6.dp))
                    .background(itemBg)
                    .border(BorderStroke(1.dp, borderColor), CutCornerShape(6.dp))
                    .clickable { onSelectMode(mode) },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = mode.iconSymbol,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = mode.title.uppercase(),
                        color = if (isSelected) theme.primaryColor else theme.secondaryTextColor,
                        fontSize = 9.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
