package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.OptimizationLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OptimizationLogDao {
    @Query("SELECT * FROM optimization_logs ORDER BY logId DESC")
    fun getAllOptimizationLogs(): Flow<List<OptimizationLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptimizationLog(log: OptimizationLogEntity): Long
}
