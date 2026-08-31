package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.OmegaCoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OmegaCoreDao {
    @Query("SELECT * FROM omega_core ORDER BY coreId DESC")
    fun getAllOmegaCore(): Flow<List<OmegaCoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOmegaCore(core: OmegaCoreEntity): Long

    @Update
    suspend fun updateOmegaCore(core: OmegaCoreEntity)
}
