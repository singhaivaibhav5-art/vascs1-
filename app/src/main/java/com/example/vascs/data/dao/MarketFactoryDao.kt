package com.example.vascs.data.dao

import androidx.room.*
import com.example.vascs.data.model.MarketFactoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketFactoryDao {
    @Query("SELECT * FROM market_factory ORDER BY marketId DESC")
    fun getAllMarkets(): Flow<List<MarketFactoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarket(market: MarketFactoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarkets(markets: List<MarketFactoryEntity>)

    @Update
    suspend fun updateMarket(market: MarketFactoryEntity)

    @Query("DELETE FROM market_factory")
    suspend fun clearMarkets()
}
