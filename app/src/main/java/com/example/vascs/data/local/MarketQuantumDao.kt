package com.example.vascs.data.local

import androidx.room.*
import com.example.vascs.data.model.MarketQuantumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketQuantumDao {
    @Query("SELECT * FROM market_quantum ORDER BY predictionId DESC")
    fun getAllMarketPredictions(): Flow<List<MarketQuantumEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketPrediction(prediction: MarketQuantumEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketPredictions(predictions: List<MarketQuantumEntity>)
}
