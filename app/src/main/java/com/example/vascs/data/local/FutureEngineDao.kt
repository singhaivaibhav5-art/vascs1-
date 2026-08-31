package com.example.vascs.data.local

import androidx.room.*
import com.example.vascs.data.model.FutureEngineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FutureEngineDao {
    @Query("SELECT * FROM future_engine ORDER BY scenarioId ASC")
    fun getAllScenarios(): Flow<List<FutureEngineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScenario(scenario: FutureEngineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScenarios(scenarios: List<FutureEngineEntity>)

    @Query("DELETE FROM future_engine")
    suspend fun clearScenarios()
}
