package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.WhatsAppCampaignEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WhatsAppCampaignDao {

    @Query("SELECT * FROM whatsapp_campaigns ORDER BY createdDate DESC")
    fun getAllCampaigns(): Flow<List<WhatsAppCampaignEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(campaign: WhatsAppCampaignEntity): Long

    @Update
    suspend fun update(campaign: WhatsAppCampaignEntity)

    @Delete
    suspend fun delete(campaign: WhatsAppCampaignEntity)

    @Query("UPDATE whatsapp_campaigns SET status = :status, sentCount = :sentCount WHERE id = :id")
    suspend fun updateCampaignStatus(id: Long, status: String, sentCount: Int)
}
