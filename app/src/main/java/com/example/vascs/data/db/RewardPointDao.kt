package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.RewardPointEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RewardPointDao {
    @Query("SELECT * FROM reward_points ORDER BY rewardId DESC")
    fun getAllRewardPoints(): Flow<List<RewardPointEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRewardPoint(reward: RewardPointEntity): Long
}
