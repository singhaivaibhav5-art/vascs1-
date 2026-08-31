package com.example.vascs.data.dao

import androidx.room.*
import com.example.vascs.data.model.ProductFactoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductFactoryDao {
    @Query("SELECT * FROM product_factory ORDER BY productId DESC")
    fun getAllProducts(): Flow<List<ProductFactoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductFactoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductFactoryEntity>)

    @Update
    suspend fun updateProduct(product: ProductFactoryEntity)

    @Query("DELETE FROM product_factory")
    suspend fun clearProducts()
}
