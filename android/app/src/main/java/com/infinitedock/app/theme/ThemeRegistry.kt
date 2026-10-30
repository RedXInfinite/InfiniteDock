package com.infinitedock.app.theme

import androidx.compose.ui.graphics.Color

object ThemeRegistry {

    val CrimsonCore = ThemeSpec(
        id = "crimson_core",
        name = "CRIMSON CORE",
        subtitle = "Retro Handheld Hardware",
        primaryColor = Color(0xFFD32F2F),
        secondaryColor = Color(0xFF212121),
        accentColor = Color(0xFFFFF8E1),
        backgroundColor = Color(0xFF120808),
        surfaceColor = Color(0xFF1E1010),
        cardBackgroundColor = Color(0xFF2A1515),
        textColor = Color(0xFFFFF8E1),
        secondaryTextColor = Color(0xFFE57373),
        gridLineColor = Color(0x33D32F2F)
    )

    val Voltstrike = ThemeSpec(
        id = "voltstrike",
        name = "VOLTSTRIKE",
        subtitle = "High-Energy Electric Core",
        primaryColor = Color(0xFFFFE500),
        secondaryColor = Color(0xFFFFB300),
        accentColor = Color(0xFF00E5FF),
        backgroundColor = Color(0xFF0A0E14),
        surfaceColor = Color(0xFF111823),
        cardBackgroundColor = Color(0xFF182232),
        textColor = Color(0xFFFFFFFF),
        secondaryTextColor = Color(0xFFFFD54F),
        gridLineColor = Color(0x33FFE500)
    )

    val InfernoPulse = ThemeSpec(
        id = "inferno_pulse",
        name = "INFERNO PULSE",
        subtitle = "Thermal Ignition Matrix",
        primaryColor = Color(0xFFFF5722),
        secondaryColor = Color(0xFFD84315),
        accentColor = Color(0xFFFFAB40),
        backgroundColor = Color(0xFF140A07),
        surfaceColor = Color(0xFF21110B),
        cardBackgroundColor = Color(0xFF2F1810),
        textColor = Color(0xFFFFF3E0),
        secondaryTextColor = Color(0xFFFF8A65),
        gridLineColor = Color(0x33FF5722)
    )

    val TidalSurge = ThemeSpec(
        id = "tidal_surge",
        name = "TIDAL SURGE",
        subtitle = "Deep Hydro Fluidics",
        primaryColor = Color(0xFF00E5FF),
        secondaryColor = Color(0xFF0288D1),
        accentColor = Color(0xFF80DEEA),
        backgroundColor = Color(0xFF05111D),
        surfaceColor = Color(0xFF0A1D2F),
        cardBackgroundColor = Color(0xFF0F2A42),
        textColor = Color(0xFFE0F7FA),
        secondaryTextColor = Color(0xFF4DD0E1),
        gridLineColor = Color(0x3300E5FF)
    )

    val VerdantBloom = ThemeSpec(
        id = "verdant_bloom",
        name = "VERDANT BLOOM",
        subtitle = "Bio-Organic Network",
        primaryColor = Color(0xFF00E676),
        secondaryColor = Color(0xFF2E7D32),
        accentColor = Color(0xFFB9F6CA),
        backgroundColor = Color(0xFF06140A),
        surfaceColor = Color(0xFF0C2212),
        cardBackgroundColor = Color(0xFF12321B),
        textColor = Color(0xFFE8F5E9),
        secondaryTextColor = Color(0xFF69F0AE),
        gridLineColor = Color(0x3300E676)
    )

    val ShadowProtocol = ThemeSpec(
        id = "shadow_protocol",
        name = "SHADOW PROTOCOL",
        subtitle = "Cyber Security Operations",
        primaryColor = Color(0xFFD500F9),
        secondaryColor = Color(0xFF651FFF),
        accentColor = Color(0xFF00E5FF),
        backgroundColor = Color(0xFF0A0512),
        surfaceColor = Color(0xFF130A21),
        cardBackgroundColor = Color(0xFF1C0F33),
        textColor = Color(0xFFF3E5F5),
        secondaryTextColor = Color(0xFFE040FB),
        gridLineColor = Color(0x33D500F9)
    )

    val AuroraDawn = ThemeSpec(
        id = "aurora_dawn",
        name = "AURORA DAWN",
        subtitle = "Luminous Atmosphere Shell",
        primaryColor = Color(0xFF37474F),
        secondaryColor = Color(0xFF78909C),
        accentColor = Color(0xFFFF6D00),
        backgroundColor = Color(0xFFF4F6F9),
        surfaceColor = Color(0xFFFFFFFF),
        cardBackgroundColor = Color(0xFFECEFF1),
        textColor = Color(0xFF1A2327),
        secondaryTextColor = Color(0xFF546E7A),
        gridLineColor = Color(0x2237474F)
    )

    val allThemes: List<ThemeSpec> = listOf(
        CrimsonCore,
        Voltstrike,
        InfernoPulse,
        TidalSurge,
        VerdantBloom,
        ShadowProtocol,
        AuroraDawn
    )

    fun getThemeById(id: String): ThemeSpec {
        return allThemes.firstOrNull { it.id == id } ?: Voltstrike
    }
}
