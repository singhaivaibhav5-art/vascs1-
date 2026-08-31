package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.DeliveryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliveryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDelivery(delivery: DeliveryEntity): Long

    @Query("SELECT * FROM deliveries WHERE orderId = :orderId LIMIT 1")
    fun getDeliveryForOrder(orderId: Long): Flow<DeliveryEntity?>

    @Query("SELECT * FROM deliveries WHERE orderId = :orderId LIMIT 1")
    suspend fun getDeliveryForOrderOnce(orderId: Long): DeliveryEntity?

    @Query("SELECT * FROM deliveries ORDER BY deliveryId DESC")
    fun getAllDeliveries(): Flow<List<DeliveryEntity>>
}
