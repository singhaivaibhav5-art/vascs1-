package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.NexusHealthEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NexusHealthDao {
    @Query("SELECT * FROM nexus_health ORDER BY healthId DESC")
    fun getAllNexusHealth(): Flow<List<NexusHealthEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNexusHealth(health: NexusHealthEntity): Long

    @Update
    suspend fun updateNexusHealth(health: NexusHealthEntity)
}
