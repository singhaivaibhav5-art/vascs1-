package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.GlobalEconomyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GlobalEconomyDao {
    @Query("SELECT * FROM global_economy ORDER BY economyId DESC")
    fun getAllGlobalEconomy(): Flow<List<GlobalEconomyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobalEconomy(economy: GlobalEconomyEntity): Long

    @Update
    suspend fun updateGlobalEconomy(economy: GlobalEconomyEntity)
}
