package com.infinitedock.app.ui.navigation

enum class RemoteNavMode(
    val route: String,
    val title: String,
    val iconSymbol: String
) {
    REMOTE_DOCK("remote_dock", "Dock", "📱"),
    MOUSE_MODE("mouse_mode", "Mouse", "🖱️"),
    KEYBOARD_MODE("keyboard_mode", "Keypad", "⌨️"),
    GAME_CONTROLLER("game_controller", "Gamepad", "🎮"),
    SETTINGS_PORTAL("settings_portal", "Settings", "⚙️")
}
