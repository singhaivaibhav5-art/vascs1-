package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.BusinessDnaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessDnaDao {
    @Query("SELECT * FROM business_dna ORDER BY dnaId DESC")
    fun getAllBusinessDna(): Flow<List<BusinessDnaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusinessDna(dna: BusinessDnaEntity): Long

    @Update
    suspend fun updateBusinessDna(dna: BusinessDnaEntity)
}
