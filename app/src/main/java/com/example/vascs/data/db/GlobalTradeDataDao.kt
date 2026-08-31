package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.GlobalTradeDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GlobalTradeDataDao {
    @Query("SELECT * FROM global_trade_data ORDER BY tradeDataId DESC")
    fun getAllGlobalTradeData(): Flow<List<GlobalTradeDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobalTradeData(tradeData: GlobalTradeDataEntity): Long

    @Update
    suspend fun updateGlobalTradeData(tradeData: GlobalTradeDataEntity)
}
