package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.CompetitorIntelligenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompetitorIntelligenceDao {
    @Query("SELECT * FROM competitor_intelligence ORDER BY competitorId DESC")
    fun getAllCompetitorIntelligence(): Flow<List<CompetitorIntelligenceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompetitorIntelligence(competitor: CompetitorIntelligenceEntity): Long

    @Update
    suspend fun updateCompetitorIntelligence(competitor: CompetitorIntelligenceEntity)
}
