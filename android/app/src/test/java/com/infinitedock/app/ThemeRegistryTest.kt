package com.infinitedock.app

import com.infinitedock.app.theme.AnimationIntensity
import com.infinitedock.app.theme.ThemeRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ThemeRegistryTest {

    @Test
    fun `verify all seven signature themes exist`() {
        val themes = ThemeRegistry.allThemes
        assertEquals(7, themes.size)

        val themeIds = themes.map { it.id }.toSet()
        assertEquals(7, themeIds.size)

        val expectedIds = setOf(
            "crimson_core",
            "voltstrike",
            "inferno_pulse",
            "tidal_surge",
            "verdant_bloom",
            "shadow_protocol",
            "aurora_dawn"
        )
        assertEquals(expectedIds, themeIds)
    }

    @Test
    fun `getThemeById returns Voltstrike for unknown theme id`() {
        val theme = ThemeRegistry.getThemeById("non_existent_theme")
        assertEquals("voltstrike", theme.id)
    }

    @Test
    fun `getThemeById returns correct theme spec`() {
        val voltstrike = ThemeRegistry.getThemeById("voltstrike")
        assertEquals("VOLTSTRIKE", voltstrike.name)
        assertNotNull(voltstrike.primaryColor)

        val shadowProtocol = ThemeRegistry.getThemeById("shadow_protocol")
        assertEquals("SHADOW PROTOCOL", shadowProtocol.name)
        assertNotNull(shadowProtocol.primaryColor)
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
