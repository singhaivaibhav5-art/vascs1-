package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.ManufacturerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ManufacturerDao {
    @Query("SELECT * FROM manufacturers ORDER BY manufacturerId DESC")
    fun getAllManufacturers(): Flow<List<ManufacturerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManufacturer(manufacturer: ManufacturerEntity): Long

    @Update
    suspend fun updateManufacturer(manufacturer: ManufacturerEntity)
}
