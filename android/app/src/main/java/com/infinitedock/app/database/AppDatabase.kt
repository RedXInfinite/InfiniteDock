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
                        // Pre-populate database
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = getDatabase(context)
                            database.appPreferencesDao().savePreferences(AppPreferencesEntity())
                            seedDefaultShortcuts(database.shortcutDao())
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun seedDefaultShortcuts(shortcutDao: ShortcutDao) {
            val defaultShortcuts = listOf(
                ShortcutEntity(
                    packageName = "com.android.settings",
                    displayName = "Settings",
                    position = 0,
                    shortcutType = "SYSTEM",
                    isDockShortcut = true,
                    isDesktopShortcut = true
                ),
                ShortcutEntity(
                    packageName = "com.android.chrome",
                    displayName = "Browser",
                    position = 1,
                    shortcutType = "APP",
                    isDockShortcut = true,
                    isDesktopShortcut = true
                ),
                ShortcutEntity(
                    packageName = "com.google.android.youtube",
                    displayName = "Media",
                    position = 2,
                    shortcutType = "APP",
                    isDockShortcut = true,
                    isDesktopShortcut = true
                ),
                ShortcutEntity(
                    packageName = "com.infinitedock.app",
                    displayName = "InfiniteDock",
                    position = 3,
                    shortcutType = "APP",
                    isDockShortcut = true,
                    isDesktopShortcut = true
                )
            )
            shortcutDao.insertShortcuts(defaultShortcuts)
        }
    }
}
