package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AiTaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiTaskDao {
    @Query("SELECT * FROM ai_tasks ORDER BY taskId DESC")
    fun getAllAiTasks(): Flow<List<AiTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiTask(task: AiTaskEntity): Long

    @Update
    suspend fun updateAiTask(task: AiTaskEntity)
}
