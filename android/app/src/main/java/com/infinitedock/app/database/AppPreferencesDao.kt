package com.infinitedock.app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppPreferencesDao {
    @Query("SELECT * FROM app_preferences WHERE id = 1")
    fun getPreferences(): Flow<AppPreferencesEntity?>

    @Query("SELECT * FROM app_preferences WHERE id = 1")
    suspend fun getPreferencesSync(): AppPreferencesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePreferences(preferences: AppPreferencesEntity)

    @Update
    suspend fun updatePreferences(preferences: AppPreferencesEntity)
}
