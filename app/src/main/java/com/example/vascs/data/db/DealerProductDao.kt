package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.DealerProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DealerProductDao {

    @Query("SELECT * FROM dealer_products WHERE dealerId = :dealerId ORDER BY assignedDate DESC")
    fun getProductsForDealer(dealerId: String): Flow<List<DealerProductEntity>>

    @Query("SELECT * FROM dealer_products WHERE productId = :productId ORDER BY assignedDate DESC")
    fun getDealersForProduct(productId: Long): Flow<List<DealerProductEntity>>

    @Query("SELECT * FROM dealer_products")
    fun getAllDealerProducts(): Flow<List<DealerProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dealerProduct: DealerProductEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dealerProducts: List<DealerProductEntity>)

    @Query("DELETE FROM dealer_products WHERE dealerId = :dealerId AND productId = :productId")
    suspend fun deleteAssignment(dealerId: String, productId: Long)

    @Query("DELETE FROM dealer_products WHERE productId = :productId")
    suspend fun clearProductAssignments(productId: Long)
}
