package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.TradeNetworksEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TradeNetworksDao {
    @Query("SELECT * FROM trade_networks ORDER BY networkId DESC")
    fun getAllTradeNetworks(): Flow<List<TradeNetworksEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTradeNetwork(network: TradeNetworksEntity): Long

    @Update
    suspend fun updateTradeNetwork(network: TradeNetworksEntity)
}
