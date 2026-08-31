package com.example.vascs.data.local

import androidx.room.*
import com.example.vascs.data.model.EvolutionEngineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EvolutionEngineDao {
    @Query("SELECT * FROM evolution_engine ORDER BY evolutionId DESC")
    fun getAllEvolutionLogs(): Flow<List<EvolutionEngineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvolutionLog(log: EvolutionEngineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvolutionLogs(logs: List<EvolutionEngineEntity>)
}
