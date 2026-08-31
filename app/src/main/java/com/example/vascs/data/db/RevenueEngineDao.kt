package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.RevenueEngineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RevenueEngineDao {
    @Query("SELECT * FROM revenue_engine ORDER BY streamId DESC")
    fun getAllRevenueEngine(): Flow<List<RevenueEngineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRevenueEngine(revenue: RevenueEngineEntity): Long

    @Update
    suspend fun updateRevenueEngine(revenue: RevenueEngineEntity)
}
