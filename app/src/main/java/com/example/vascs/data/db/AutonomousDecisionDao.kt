package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AutonomousDecisionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AutonomousDecisionDao {
    @Query("SELECT * FROM autonomous_decisions ORDER BY decisionId DESC")
    fun getAllAutonomousDecisions(): Flow<List<AutonomousDecisionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAutonomousDecision(decision: AutonomousDecisionEntity): Long

    @Update
    suspend fun updateAutonomousDecision(decision: AutonomousDecisionEntity)
}
