package com.example.vascs.data.local

import androidx.room.*
import com.example.vascs.data.model.RiskQuantumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RiskQuantumDao {
    @Query("SELECT * FROM risk_quantum ORDER BY riskId DESC")
    fun getAllRisks(): Flow<List<RiskQuantumEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRisk(risk: RiskQuantumEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRisks(risks: List<RiskQuantumEntity>)
}
