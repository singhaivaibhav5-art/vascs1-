package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.MarketIntelligenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketIntelligenceDao {
    @Query("SELECT * FROM market_intelligence ORDER BY intelligenceId DESC")
    fun getAllMarketIntelligence(): Flow<List<MarketIntelligenceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketIntelligence(intelligence: MarketIntelligenceEntity): Long
}
