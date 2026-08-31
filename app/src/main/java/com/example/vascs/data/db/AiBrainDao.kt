package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AiBrainEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiBrainDao {
    @Query("SELECT * FROM ai_brain ORDER BY brainId DESC")
    fun getAllAiBrains(): Flow<List<AiBrainEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiBrain(brain: AiBrainEntity): Long

    @Update
    suspend fun updateAiBrain(brain: AiBrainEntity)
}
