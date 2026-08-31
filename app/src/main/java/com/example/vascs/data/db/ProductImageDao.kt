package com.example.vascs.data.db

import androidx.room.*
import com.example.vascs.data.model.ProductImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductImageDao {
    @Query("SELECT * FROM product_images WHERE productId = :productId ORDER BY sortOrder ASC, createdAt ASC")
    fun getImagesForProduct(productId: String): Flow<List<ProductImageEntity>>

    @Query("SELECT * FROM product_images WHERE productId = :productId AND isPrimary = 1 LIMIT 1")
    suspend fun getPrimaryImage(productId: String): ProductImageEntity?

    @Query("SELECT * FROM product_images WHERE productId = :productId ORDER BY sortOrder ASC, createdAt ASC")
    suspend fun getImagesListForProduct(productId: String): List<ProductImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: ProductImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ProductImageEntity>)

    @Update
    suspend fun updateImage(image: ProductImageEntity)

    @Query("DELETE FROM product_images WHERE id = :id")
    suspend fun deleteImageById(id: String)

    @Query("DELETE FROM product_images WHERE productId = :productId")
    suspend fun deleteImagesForProduct(productId: String)

    @Query("UPDATE product_images SET isPrimary = 0 WHERE productId = :productId")
    suspend fun clearPrimaryFlags(productId: String)

    @Query("UPDATE product_images SET isPrimary = 1 WHERE id = :imageId")
    suspend fun setPrimaryFlag(imageId: String)

    @Transaction
    suspend fun setPrimaryImage(productId: String, imageId: String) {
        clearPrimaryFlags(productId)
        setPrimaryFlag(imageId)
    }
}
