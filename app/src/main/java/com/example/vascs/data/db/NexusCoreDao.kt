package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.NexusCoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NexusCoreDao {
    @Query("SELECT * FROM nexus_core ORDER BY coreId DESC")
    fun getAllNexusCore(): Flow<List<NexusCoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNexusCore(core: NexusCoreEntity): Long

    @Update
    suspend fun updateNexusCore(core: NexusCoreEntity)
}
