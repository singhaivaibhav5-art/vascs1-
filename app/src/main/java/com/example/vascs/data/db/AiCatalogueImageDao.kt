package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AiCatalogueImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiCatalogueImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: AiCatalogueImageEntity)

    @Update
    suspend fun update(image: AiCatalogueImageEntity)

    @Delete
    suspend fun delete(image: AiCatalogueImageEntity)

    @Query(
        """
        SELECT *
        FROM ai_catalogue_images
        ORDER BY createdDate DESC
        """
    )
    fun getAllImages(): Flow<List<AiCatalogueImageEntity>>

    @Query(
        """
        SELECT *
        FROM ai_catalogue_images
        WHERE imageSource=:source
        ORDER BY createdDate DESC
        """
    )
    fun getBySource(source: String): Flow<List<AiCatalogueImageEntity>>

    @Query(
        """
        SELECT *
        FROM ai_catalogue_images
        WHERE productId=:productId
        ORDER BY createdDate DESC
        """
    )
    fun getByProduct(productId: Long): Flow<List<AiCatalogueImageEntity>>

    @Query(
        """
        DELETE
        FROM ai_catalogue_images
        WHERE id=:id
        """
    )
    suspend fun deleteById(id: Long)
}
