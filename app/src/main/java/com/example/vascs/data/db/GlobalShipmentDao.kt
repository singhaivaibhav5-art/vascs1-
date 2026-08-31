package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.GlobalShipmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GlobalShipmentDao {
    @Query("SELECT * FROM global_shipments ORDER BY shipmentId DESC")
    fun getAllGlobalShipments(): Flow<List<GlobalShipmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobalShipment(shipment: GlobalShipmentEntity): Long

    @Update
    suspend fun updateGlobalShipment(shipment: GlobalShipmentEntity)
}
