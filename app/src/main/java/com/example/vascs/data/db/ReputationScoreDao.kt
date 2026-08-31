package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.ReputationScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReputationScoreDao {
    @Query("SELECT * FROM reputation_scores ORDER BY scoreId DESC")
    fun getAllReputationScores(): Flow<List<ReputationScoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReputationScore(score: ReputationScoreEntity): Long

    @Update
    suspend fun updateReputationScore(score: ReputationScoreEntity)
}
