package com.example.vascs.data.dao

import androidx.room.*
import com.example.vascs.data.model.BrandFactoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BrandFactoryDao {
    @Query("SELECT * FROM brand_factory ORDER BY brandId DESC")
    fun getAllBrands(): Flow<List<BrandFactoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBrand(brand: BrandFactoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBrands(brands: List<BrandFactoryEntity>)

    @Update
    suspend fun updateBrand(brand: BrandFactoryEntity)

    @Query("DELETE FROM brand_factory")
    suspend fun clearBrands()
}
