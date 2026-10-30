package com.infinitedock.app.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalThemeSpec = staticCompositionLocalOf { ThemeRegistry.Voltstrike }
val LocalAnimationIntensity = staticCompositionLocalOf { AnimationIntensity.MEDIUM }

@Composable
fun InfiniteDockTheme(
    themeSpec: ThemeSpec = ThemeRegistry.Voltstrike,
    animationIntensity: AnimationIntensity = AnimationIntensity.MEDIUM,
    content: @Composable () -> Unit
) {
    val animatedPrimary = animateColorAsState(themeSpec.primaryColor, tween(400), label = "primary").value
    val animatedBackground = animateColorAsState(themeSpec.backgroundColor, tween(400), label = "bg").value
    val animatedSurface = animateColorAsState(themeSpec.surfaceColor, tween(400), label = "surface").value
    val animatedText = animateColorAsState(themeSpec.textColor, tween(400), label = "text").value

    val colorScheme: ColorScheme = if (themeSpec.id == "aurora_dawn") {
        lightColorScheme(
            primary = animatedPrimary,
            secondary = themeSpec.secondaryColor,
            tertiary = themeSpec.accentColor,
            background = animatedBackground,
            surface = animatedSurface,
            onPrimary = themeSpec.backgroundColor,
            onBackground = animatedText,
            onSurface = animatedText
        )
    } else {
        darkColorScheme(
            primary = animatedPrimary,
            secondary = themeSpec.secondaryColor,
            tertiary = themeSpec.accentColor,
            background = animatedBackground,
            surface = animatedSurface,
            onPrimary = themeSpec.backgroundColor,
            onBackground = animatedText,
            onSurface = animatedText
        )
    }

    CompositionLocalProvider(
        LocalThemeSpec provides themeSpec,
        LocalAnimationIntensity provides animationIntensity
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
