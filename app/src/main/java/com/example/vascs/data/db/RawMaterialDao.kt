package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.RawMaterialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RawMaterialDao {
    @Query("SELECT * FROM raw_materials ORDER BY materialId DESC")
    fun getAllRawMaterials(): Flow<List<RawMaterialEntity>>

    @Query("SELECT * FROM raw_materials WHERE materialId = :id LIMIT 1")
    suspend fun getRawMaterialById(id: Long): RawMaterialEntity?

    @Query("SELECT * FROM raw_materials WHERE materialCategory = :category ORDER BY materialName ASC")
    fun getRawMaterialsByCategory(category: String): Flow<List<RawMaterialEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRawMaterial(material: RawMaterialEntity): Long

    @Update
    suspend fun updateRawMaterial(material: RawMaterialEntity)

    @Query("UPDATE raw_materials SET currentStock = :newStock WHERE materialId = :id")
    suspend fun updateStock(id: Long, newStock: Double)
}
