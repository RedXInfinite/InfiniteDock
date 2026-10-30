package com.infinitedock.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [ShortcutEntity::class, AppPreferencesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shortcutDao(): ShortcutDao
    abstract fun appPreferencesDao(): AppPreferencesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "infinite_dock_db"
                )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = getDatabase(context)
                            database.appPreferencesDao().savePreferences(AppPreferencesEntity())
                            seedDefaultWindowsShortcuts(database.shortcutDao())
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun seedDefaultWindowsShortcuts(shortcutDao: ShortcutDao) {
            val windowsShortcuts = listOf(
                ShortcutEntity(
                    packageName = "com.google.chrome.win",
                    displayName = "Google Chrome",
                    position = 0,
                    shortcutType = "WIN_APP",
                    isDockShortcut = true,
                    isDesktopShortcut = true
                ),
                ShortcutEntity(
                    packageName = "com.discord.win",
                    displayName = "Discord",
                    position = 1,
                    shortcutType = "WIN_APP",
                    isDockShortcut = true,
                    isDesktopShortcut = true
                ),
                ShortcutEntity(
                    packageName = "com.spotify.win",
                    displayName = "Spotify",
                    position = 2,
                    shortcutType = "WIN_APP",
                    isDockShortcut = true,
                    isDesktopShortcut = true
                ),
                ShortcutEntity(
                    packageName = "com.valvesoftware.steam.win",
                    displayName = "Steam PC",
                    position = 3,
                    shortcutType = "WIN_APP",
                    isDockShortcut = true,
                    isDesktopShortcut = true
                ),
                ShortcutEntity(
                    packageName = "com.microsoft.vscode.win",
                    displayName = "VS Code",
                    position = 4,
                    shortcutType = "WIN_APP",
                    isDockShortcut = true,
                    isDesktopShortcut = true
                ),
                ShortcutEntity(
                    packageName = "com.adobe.photoshop.win",
                    displayName = "Photoshop",
                    position = 5,
                    shortcutType = "WIN_APP",
                    isDockShortcut = true,
                    isDesktopShortcut = true
                )
            )
            shortcutDao.insertShortcuts(windowsShortcuts)
        }
    }
}
