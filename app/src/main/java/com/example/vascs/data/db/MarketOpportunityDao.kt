package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.MarketOpportunityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketOpportunityDao {
    @Query("SELECT * FROM market_opportunities ORDER BY opportunityId DESC")
    fun getAllMarketOpportunities(): Flow<List<MarketOpportunityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketOpportunity(opportunity: MarketOpportunityEntity): Long

    @Update
    suspend fun updateMarketOpportunity(opportunity: MarketOpportunityEntity)
}
