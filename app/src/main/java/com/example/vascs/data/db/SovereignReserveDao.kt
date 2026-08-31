package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.SovereignReserveEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SovereignReserveDao {
    @Query("SELECT * FROM sovereign_reserves ORDER BY reserveId DESC")
    fun getAllReserves(): Flow<List<SovereignReserveEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReserve(reserve: SovereignReserveEntity): Long

    @Update
    suspend fun updateReserve(reserve: SovereignReserveEntity)
}
