package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AiAgentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiAgentDao {
    @Query("SELECT * FROM ai_agents ORDER BY agentId ASC")
    fun getAllAiAgents(): Flow<List<AiAgentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiAgent(agent: AiAgentEntity): Long

    @Update
    suspend fun updateAiAgent(agent: AiAgentEntity)
}
