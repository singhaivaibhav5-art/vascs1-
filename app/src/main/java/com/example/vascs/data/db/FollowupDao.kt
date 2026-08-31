package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.FollowupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowupDao {
    @Query("SELECT * FROM followups ORDER BY followupId DESC")
    fun getAllFollowups(): Flow<List<FollowupEntity>>

    @Query("SELECT * FROM followups WHERE leadId = :leadId ORDER BY followupId DESC")
    fun getFollowupsForLead(leadId: Long): Flow<List<FollowupEntity>>

    @Query("SELECT * FROM followups WHERE status = 'PENDING' ORDER BY followupId ASC")
    fun getPendingFollowups(): Flow<List<FollowupEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowup(followup: FollowupEntity): Long

    @Query("UPDATE followups SET status = :status WHERE followupId = :followupId")
    suspend fun updateFollowupStatus(followupId: Long, status: String)
}
