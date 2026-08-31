package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.MarketplaceProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketplaceProductDao {
    @Query("SELECT * FROM marketplace_products ORDER BY productId DESC")
    fun getAllMarketplaceProducts(): Flow<List<MarketplaceProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketplaceProduct(product: MarketplaceProductEntity): Long

    @Update
    suspend fun updateMarketplaceProduct(product: MarketplaceProductEntity)
}
