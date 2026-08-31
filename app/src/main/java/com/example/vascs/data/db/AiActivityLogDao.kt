package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.AiActivityLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiActivityLogDao {
    @Query("SELECT * FROM ai_activity_logs ORDER BY logId DESC")
    fun getAllActivityLogs(): Flow<List<AiActivityLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityLog(log: AiActivityLogEntity): Long
}
