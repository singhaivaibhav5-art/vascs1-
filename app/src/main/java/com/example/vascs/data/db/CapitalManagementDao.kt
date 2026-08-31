package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.CapitalManagementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CapitalManagementDao {
    @Query("SELECT * FROM capital_management ORDER BY capitalId DESC")
    fun getAllCapitalManagement(): Flow<List<CapitalManagementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapitalManagement(capital: CapitalManagementEntity): Long

    @Update
    suspend fun updateCapitalManagement(capital: CapitalManagementEntity)
}
