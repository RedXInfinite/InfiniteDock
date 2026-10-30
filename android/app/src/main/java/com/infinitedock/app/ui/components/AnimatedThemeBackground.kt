package com.infinitedock.app.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.infinitedock.app.theme.AnimationIntensity
import com.infinitedock.app.theme.ThemeSpec
import kotlin.math.sin

@Composable
fun AnimatedThemeBackground(
    themeSpec: ThemeSpec,
    intensity: AnimationIntensity,
    modifier: Modifier = Modifier
) {
    if (intensity == AnimationIntensity.OFF) {
        Canvas(modifier = modifier.fillMaxSize()) {
            drawRect(color = themeSpec.backgroundColor)
        }
        return
    }

    val speedMultiplier = when (intensity) {
        AnimationIntensity.LOW -> 0.5f
        AnimationIntensity.MEDIUM -> 1.0f
        AnimationIntensity.HIGH -> 1.8f
        AnimationIntensity.OFF -> 0.0f
    }

    val durationMillis = (3000 / speedMultiplier).toInt()
    val transition = rememberInfiniteTransition(label = "theme_bg_transition")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bg_progress"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Base background fill
        drawRect(color = themeSpec.backgroundColor)

        // Draw grid lines
        val gridSize = 48f
        var x = 0f
        while (x < w) {
            drawLine(
                color = themeSpec.gridLineColor,
                start = Offset(x, 0f),
                end = Offset(x, h),
                strokeWidth = 1f
            )
            x += gridSize
        }
        var y = 0f
        while (y < h) {
            drawLine(
                color = themeSpec.gridLineColor,
                start = Offset(0f, y),
                end = Offset(w, y),
                strokeWidth = 1f
            )
            y += gridSize
        }

        // Signature Theme Animations
        when (themeSpec.id) {
            "voltstrike" -> {
                val sparkCount = when (intensity) {
                    AnimationIntensity.LOW -> 3
                    AnimationIntensity.MEDIUM -> 6
                    AnimationIntensity.HIGH -> 12
                    else -> 0
                }
                for (i in 0 until sparkCount) {
                    val px = ((i * 137.5f + progress * w) % w)
                    val py = ((i * 89.3f + sin(progress * 6.28f + i) * 100f + h / 2) % h)
                    drawCircle(
                        color = themeSpec.primaryColor.copy(alpha = 0.8f),
                        radius = 3f + (i % 4),
                        center = Offset(px, py)
                    )
                }

                val boltPath = Path().apply {
                    var bx = 0f
                    var by = h * 0.3f
                    moveTo(bx, by)
                    while (bx < w) {
                        bx += 40f
                        by += (sin(bx * 0.05f + progress * 6.28f) * 30f)
                        lineTo(bx, by)
                    }
                }
                drawPath(
                    path = boltPath,
                    color = themeSpec.primaryColor.copy(alpha = 0.4f * (intensity.ordinal / 3f)),
                    style = Stroke(width = 2f)
                )
            }

            "shadow_protocol" -> {
                val scanlineY = progress * h
                drawLine(
                    color = themeSpec.primaryColor.copy(alpha = 0.6f),
                    start = Offset(0f, scanlineY),
                    end = Offset(w, scanlineY),
                    strokeWidth = 3f
                )

                val blockX = (progress * w * 1.5f) % w
                val blockY = (progress * h * 0.8f) % h
                drawRect(
                    color = themeSpec.accentColor.copy(alpha = 0.15f * (intensity.ordinal / 3f)),
                    topLeft = Offset(blockX, blockY),
                    size = Size(120f, 60f)
                )
            }

            "liquid_glass" -> {
                // Liquid Glass acrylic shimmer & refraction light sheen
                val shimmerX = (progress * w * 2f) - w
                val glassPath = Path().apply {
                    moveTo(shimmerX, 0f)
                    lineTo(shimmerX + 160f, 0f)
                    lineTo(shimmerX + 60f, h)
                    lineTo(shimmerX - 100f, h)
                    close()
                }
                drawPath(
                    path = glassPath,
                    color = themeSpec.accentColor.copy(alpha = 0.12f * (intensity.ordinal / 3f))
                )
            }

            "crimson_core" -> {
                val scanBarY = (sin(progress * 3.14f) * h)
                drawLine(
                    color = themeSpec.primaryColor.copy(alpha = 0.3f),
                    start = Offset(0f, scanBarY),
                    end = Offset(w, scanBarY),
                    strokeWidth = 6f
                )
            }

            "inferno_pulse" -> {
                val particleCount = (5 * intensity.ordinal)
                for (i in 0 until particleCount) {
                    val pY = (h - ((progress * h + i * 80) % h))
                    val pX = (w * 0.2f + i * 60f + sin(progress * 6.28f + i) * 30f) % w
                    drawCircle(
                        color = themeSpec.primaryColor.copy(alpha = 0.7f),
                        radius = 4f,
                        center = Offset(pX, pY)
                    )
                }
            }

            "tidal_surge" -> {
                val wavePath = Path().apply {
                    moveTo(0f, h * 0.8f)
                    var wx = 0f
                    while (wx <= w) {
                        val wy = h * 0.8f + sin((wx / w) * 12.56f + progress * 6.28f) * 15f
                        lineTo(wx, wy)
                        wx += 20f
                    }
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()
                }
                drawPath(
                    path = wavePath,
                    color = themeSpec.primaryColor.copy(alpha = 0.15f)
                )
            }

            "verdant_bloom" -> {
                val sporeCount = 4 * intensity.ordinal
                for (i in 0 until sporeCount) {
                    val sx = (i * 90f + progress * 50f) % w
                    val sy = (i * 110f + sin(progress * 3.14f + i) * 40f) % h
                    drawCircle(
                        color = themeSpec.primaryColor.copy(alpha = 0.5f),
                        radius = 5f,
                        center = Offset(sx, sy)
                    )
                }
            }

            "aurora_dawn" -> {
                val auroraX = (sin(progress * 6.28f) * w * 0.3f) + w / 2
                drawCircle(
                    color = themeSpec.accentColor.copy(alpha = 0.12f),
                    radius = w * 0.4f,
                    center = Offset(auroraX, h * 0.2f)
                )
            }
        }
    }
}
