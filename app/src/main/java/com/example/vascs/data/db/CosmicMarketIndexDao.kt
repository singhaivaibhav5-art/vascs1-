package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.CosmicMarketIndexEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CosmicMarketIndexDao {
    @Query("SELECT * FROM cosmic_market_indices ORDER BY indexId DESC")
    fun getAllIndices(): Flow<List<CosmicMarketIndexEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIndex(index: CosmicMarketIndexEntity): Long

    @Update
    suspend fun updateIndex(index: CosmicMarketIndexEntity)
}
