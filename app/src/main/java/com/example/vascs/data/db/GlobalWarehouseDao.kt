package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.GlobalWarehouseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GlobalWarehouseDao {
    @Query("SELECT * FROM global_warehouses ORDER BY warehouseId DESC")
    fun getAllGlobalWarehouses(): Flow<List<GlobalWarehouseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobalWarehouse(warehouse: GlobalWarehouseEntity): Long
}
