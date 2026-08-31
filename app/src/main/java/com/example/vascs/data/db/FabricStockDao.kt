package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.FabricStockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FabricStockDao {
    @Query("SELECT * FROM fabric_stock ORDER BY fabricId DESC")
    fun getAllFabricStock(): Flow<List<FabricStockEntity>>

    @Query("SELECT * FROM fabric_stock WHERE fabricId = :id LIMIT 1")
    suspend fun getFabricById(id: Long): FabricStockEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFabricStock(fabric: FabricStockEntity): Long

    @Update
    suspend fun updateFabricStock(fabric: FabricStockEntity)
}
