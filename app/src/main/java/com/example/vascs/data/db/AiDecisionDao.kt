package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.AiDecisionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiDecisionDao {
    @Query("SELECT * FROM ai_decisions ORDER BY decisionId DESC")
    fun getAllAiDecisions(): Flow<List<AiDecisionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiDecision(decision: AiDecisionEntity): Long
}
