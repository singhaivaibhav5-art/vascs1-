package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.PackingSlipEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PackingSlipDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPackingSlip(packingSlip: PackingSlipEntity): Long

    @Query("SELECT * FROM packing_slips WHERE orderId = :orderId LIMIT 1")
    fun getPackingSlipForOrder(orderId: Long): Flow<PackingSlipEntity?>

    @Query("SELECT * FROM packing_slips WHERE orderId = :orderId LIMIT 1")
    suspend fun getPackingSlipForOrderOnce(orderId: Long): PackingSlipEntity?

    @Query("SELECT * FROM packing_slips ORDER BY packingId DESC")
    fun getAllPackingSlips(): Flow<List<PackingSlipEntity>>
}
