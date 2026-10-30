package com.infinitedock.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.infinitedock.app.theme.LocalThemeSpec

@Composable
fun RetroHeader(
    title: String,
    subtitle: String? = null,
    onBackClick: (() -> Unit)? = null
) {
    val theme = LocalThemeSpec.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(theme.surfaceColor.copy(alpha = 0.95f))
            .border(
                BorderStroke(1.dp, theme.primaryColor.copy(alpha = 0.5f)),
                shape = CutCornerShape(bottomEnd = 12.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (onBackClick != null) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CutCornerShape(6.dp))
                            .background(theme.primaryColor.copy(alpha = 0.2f))
                            .border(1.dp, theme.primaryColor, CutCornerShape(6.dp))
                            .clickable { onBackClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "◄",
                            color = theme.primaryColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Column {
                    Text(
                        text = title.uppercase(),
                        color = theme.textColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 2.sp
                    )
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            color = theme.secondaryTextColor,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            // Futuristic Hardware Indicator Status Light
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(theme.primaryColor)
            )
        }
    }
}

@Composable
fun RetroButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true,
    icon: String? = null
) {
    val theme = LocalThemeSpec.current
    val bg = if (isPrimary) theme.primaryColor.copy(alpha = 0.25f) else theme.surfaceColor.copy(alpha = 0.8f)
    val borderCol = if (isPrimary) theme.primaryColor else theme.gridLineColor

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp))
            .background(bg)
            .border(
                BorderStroke(1.5.dp, borderCol),
                shape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Text(
                        text = icon,
                        color = theme.primaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Text(
                    text = text.uppercase(),
                    color = theme.textColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.2.sp
                )
            }
            Text(
                text = "►",
                color = if (isPrimary) theme.primaryColor else theme.secondaryTextColor,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun RetroCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val theme = LocalThemeSpec.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(CutCornerShape(topEnd = 12.dp, bottomStart = 12.dp))
            .background(theme.surfaceColor.copy(alpha = 0.9f))
            .border(
                BorderStroke(1.dp, theme.primaryColor.copy(alpha = 0.4f)),
                shape = CutCornerShape(topEnd = 12.dp, bottomStart = 12.dp)
            )
            .padding(16.dp),
        content = content
    )
}
