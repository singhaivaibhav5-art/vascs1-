package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.OmegaTwinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OmegaTwinDao {
    @Query("SELECT * FROM omega_twin ORDER BY twinId DESC")
    fun getAllOmegaTwin(): Flow<List<OmegaTwinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOmegaTwin(twin: OmegaTwinEntity): Long

    @Update
    suspend fun updateOmegaTwin(twin: OmegaTwinEntity)
}
