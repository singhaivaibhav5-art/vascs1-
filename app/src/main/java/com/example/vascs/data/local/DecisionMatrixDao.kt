package com.example.vascs.data.local

import androidx.room.*
import com.example.vascs.data.model.DecisionMatrixEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DecisionMatrixDao {
    @Query("SELECT * FROM decision_matrix ORDER BY matrixId DESC")
    fun getAllDecisions(): Flow<List<DecisionMatrixEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDecision(decision: DecisionMatrixEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDecisions(decisions: List<DecisionMatrixEntity>)
}
