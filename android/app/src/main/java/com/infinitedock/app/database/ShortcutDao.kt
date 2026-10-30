package com.infinitedock.app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShortcutDao {
    @Query("SELECT * FROM shortcuts ORDER BY position ASC")
    fun getAllShortcuts(): Flow<List<ShortcutEntity>>

    @Query("SELECT * FROM shortcuts WHERE isDockShortcut = 1 ORDER BY position ASC")
    fun getDockShortcuts(): Flow<List<ShortcutEntity>>

    @Query("SELECT * FROM shortcuts WHERE isDesktopShortcut = 1 ORDER BY position ASC")
    fun getDesktopShortcuts(): Flow<List<ShortcutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShortcut(shortcut: ShortcutEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShortcuts(shortcuts: List<ShortcutEntity>)

    @Update
    suspend fun updateShortcut(shortcut: ShortcutEntity)

    @Delete
    suspend fun deleteShortcut(shortcut: ShortcutEntity)

    @Query("DELETE FROM shortcuts")
    suspend fun clearAllShortcuts()
}
