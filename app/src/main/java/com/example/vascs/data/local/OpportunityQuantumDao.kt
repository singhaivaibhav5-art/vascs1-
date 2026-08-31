package com.example.vascs.data.local

import androidx.room.*
import com.example.vascs.data.model.OpportunityQuantumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OpportunityQuantumDao {
    @Query("SELECT * FROM opportunity_quantum ORDER BY opportunityId DESC")
    fun getAllOpportunities(): Flow<List<OpportunityQuantumEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOpportunity(opportunity: OpportunityQuantumEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOpportunities(opportunities: List<OpportunityQuantumEntity>)
}
