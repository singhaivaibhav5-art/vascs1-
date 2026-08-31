package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AiEmployeeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiEmployeeDao {
    @Query("SELECT * FROM ai_employees ORDER BY employeeId ASC")
    fun getAllAiEmployees(): Flow<List<AiEmployeeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiEmployee(employee: AiEmployeeEntity): Long

    @Update
    suspend fun updateAiEmployee(employee: AiEmployeeEntity)
}
