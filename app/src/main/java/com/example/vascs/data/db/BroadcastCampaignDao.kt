package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.BroadcastCampaignEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BroadcastCampaignDao {
    @Query("SELECT * FROM broadcast_campaigns ORDER BY campaignId DESC")
    fun getAllCampaigns(): Flow<List<BroadcastCampaignEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampaign(campaign: BroadcastCampaignEntity): Long
}
