package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.SelfEvolvingModelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SelfEvolvingModelDao {
    @Query("SELECT * FROM self_evolving_models ORDER BY modelId DESC")
    fun getAllModels(): Flow<List<SelfEvolvingModelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModel(model: SelfEvolvingModelEntity): Long

    @Update
    suspend fun updateModel(model: SelfEvolvingModelEntity)
}
