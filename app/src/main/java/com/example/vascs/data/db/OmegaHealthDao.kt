package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.OmegaHealthEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OmegaHealthDao {
    @Query("SELECT * FROM omega_health ORDER BY healthId DESC")
    fun getAllOmegaHealth(): Flow<List<OmegaHealthEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOmegaHealth(health: OmegaHealthEntity): Long

    @Update
    suspend fun updateOmegaHealth(health: OmegaHealthEntity)
}
