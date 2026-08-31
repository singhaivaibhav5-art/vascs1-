package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.GlobalIntelligenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GlobalIntelligenceDao {
    @Query("SELECT * FROM global_intelligence ORDER BY intelligenceId DESC")
    fun getAllGlobalIntelligence(): Flow<List<GlobalIntelligenceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobalIntelligence(intelligence: GlobalIntelligenceEntity): Long

    @Update
    suspend fun updateGlobalIntelligence(intelligence: GlobalIntelligenceEntity)
}
