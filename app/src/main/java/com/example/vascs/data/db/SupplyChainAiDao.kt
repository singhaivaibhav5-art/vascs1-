package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.SupplyChainAiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplyChainAiDao {
    @Query("SELECT * FROM supply_chain_ai ORDER BY supplyChainId DESC")
    fun getAllSupplyChainAi(): Flow<List<SupplyChainAiEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupplyChainAi(supplyChain: SupplyChainAiEntity): Long

    @Update
    suspend fun updateSupplyChainAi(supplyChain: SupplyChainAiEntity)
}
