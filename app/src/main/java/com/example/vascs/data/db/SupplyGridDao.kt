package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.SupplyGridEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplyGridDao {
    @Query("SELECT * FROM supply_grid ORDER BY gridId DESC")
    fun getAllSupplyGrid(): Flow<List<SupplyGridEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupplyGrid(grid: SupplyGridEntity): Long

    @Update
    suspend fun updateSupplyGrid(grid: SupplyGridEntity)
}
