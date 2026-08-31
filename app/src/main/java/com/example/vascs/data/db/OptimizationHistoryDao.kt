package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.OptimizationHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OptimizationHistoryDao {
    @Query("SELECT * FROM optimization_history ORDER BY historyId DESC")
    fun getAllOptimizationHistory(): Flow<List<OptimizationHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptimizationHistory(history: OptimizationHistoryEntity): Long

    @Update
    suspend fun updateOptimizationHistory(history: OptimizationHistoryEntity)
}
