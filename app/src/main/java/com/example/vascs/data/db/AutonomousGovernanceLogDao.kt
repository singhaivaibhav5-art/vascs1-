package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AutonomousGovernanceLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AutonomousGovernanceLogDao {
    @Query("SELECT * FROM autonomous_governance_logs ORDER BY logId DESC")
    fun getAllLogs(): Flow<List<AutonomousGovernanceLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: AutonomousGovernanceLogEntity): Long

    @Update
    suspend fun updateLog(log: AutonomousGovernanceLogEntity)
}
