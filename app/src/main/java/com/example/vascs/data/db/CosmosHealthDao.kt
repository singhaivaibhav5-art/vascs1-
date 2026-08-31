package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.CosmosHealthEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CosmosHealthDao {
    @Query("SELECT * FROM cosmos_health ORDER BY healthId DESC")
    fun getAllCosmosHealth(): Flow<List<CosmosHealthEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCosmosHealth(health: CosmosHealthEntity): Long

    @Update
    suspend fun updateCosmosHealth(health: CosmosHealthEntity)
}
