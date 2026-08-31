package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.EconomicTwinsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EconomicTwinsDao {
    @Query("SELECT * FROM economic_twins ORDER BY twinId DESC")
    fun getAllEconomicTwins(): Flow<List<EconomicTwinsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEconomicTwin(twin: EconomicTwinsEntity): Long

    @Update
    suspend fun updateEconomicTwin(twin: EconomicTwinsEntity)
}
