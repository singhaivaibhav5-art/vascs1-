package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.TradeLeadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TradeLeadDao {
    @Query("SELECT * FROM trade_leads ORDER BY leadId DESC")
    fun getAllTradeLeads(): Flow<List<TradeLeadEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTradeLead(lead: TradeLeadEntity): Long

    @Update
    suspend fun updateTradeLead(lead: TradeLeadEntity)
}
