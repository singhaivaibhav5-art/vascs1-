package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.OpportunityExchangeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OpportunityExchangeDao {
    @Query("SELECT * FROM opportunity_exchange ORDER BY opportunityId DESC")
    fun getAllOpportunityExchange(): Flow<List<OpportunityExchangeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOpportunityExchange(item: OpportunityExchangeEntity): Long

    @Update
    suspend fun updateOpportunityExchange(item: OpportunityExchangeEntity)
}
