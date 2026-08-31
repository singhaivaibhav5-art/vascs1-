package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.MarketCosmosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketCosmosDao {
    @Query("SELECT * FROM market_cosmos ORDER BY marketId DESC")
    fun getAllMarketCosmos(): Flow<List<MarketCosmosEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketCosmos(market: MarketCosmosEntity): Long

    @Update
    suspend fun updateMarketCosmos(market: MarketCosmosEntity)
}
