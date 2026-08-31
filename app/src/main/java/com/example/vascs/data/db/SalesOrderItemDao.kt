package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.SalesOrderItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SalesOrderItemDao {
    @Query("SELECT * FROM sales_order_items WHERE orderId = :orderId ORDER BY id ASC")
    fun getItemsForOrder(orderId: Long): Flow<List<SalesOrderItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSalesOrderItem(item: SalesOrderItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSalesOrderItems(items: List<SalesOrderItemEntity>)
}
