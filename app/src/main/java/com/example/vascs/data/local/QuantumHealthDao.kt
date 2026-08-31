package com.example.vascs.data.local

import androidx.room.*
import com.example.vascs.data.model.QuantumHealthEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuantumHealthDao {
    @Query("SELECT * FROM quantum_health ORDER BY healthId DESC")
    fun getAllQuantumHealth(): Flow<List<QuantumHealthEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuantumHealth(health: QuantumHealthEntity): Long
}
