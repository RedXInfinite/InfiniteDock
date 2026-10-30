package com.infinitedock.app

import com.infinitedock.app.theme.AnimationIntensity
import com.infinitedock.app.theme.ThemeRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ThemeRegistryTest {

    @Test
    fun `verify all eight signature themes exist`() {
        val themes = ThemeRegistry.allThemes
        assertEquals(8, themes.size)

        val themeIds = themes.map { it.id }.toSet()
        assertEquals(8, themeIds.size)

        val expectedIds = setOf(
            "crimson_core",
            "voltstrike",
            "inferno_pulse",
            "tidal_surge",
            "verdant_bloom",
            "shadow_protocol",
            "aurora_dawn",
            "liquid_glass"
        )
        assertEquals(expectedIds, themeIds)
    }

    @Test
    fun `getThemeById returns Voltstrike for unknown theme id`() {
        val theme = ThemeRegistry.getThemeById("non_existent_theme")
        assertEquals("voltstrike", theme.id)
    }

    @Test
    fun `getThemeById returns correct theme spec for Liquid Glass`() {
        val liquidGlass = ThemeRegistry.getThemeById("liquid_glass")
        assertEquals("LIQUID GLASS", liquidGlass.name)
        assertNotNull(liquidGlass.primaryColor)
    }

    @Test
    fun `AnimationIntensity parsing works correctly`() {
        assertEquals(AnimationIntensity.OFF, AnimationIntensity.fromString("OFF"))
        assertEquals(AnimationIntensity.LOW, AnimationIntensity.fromString("low"))
        assertEquals(AnimationIntensity.MEDIUM, AnimationIntensity.fromString("MEDIUM"))
        assertEquals(AnimationIntensity.HIGH, AnimationIntensity.fromString("HIGH"))
        assertEquals(AnimationIntensity.MEDIUM, AnimationIntensity.fromString("INVALID"))
    }
}
