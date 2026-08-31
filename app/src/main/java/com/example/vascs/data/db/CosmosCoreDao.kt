package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.CosmosCoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CosmosCoreDao {
    @Query("SELECT * FROM cosmos_core ORDER BY coreId DESC")
    fun getAllCosmosCore(): Flow<List<CosmosCoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCosmosCore(core: CosmosCoreEntity): Long

    @Update
    suspend fun updateCosmosCore(core: CosmosCoreEntity)
}
