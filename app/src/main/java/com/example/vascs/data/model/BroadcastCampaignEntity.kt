package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "broadcast_campaigns")
data class BroadcastCampaignEntity(
    @PrimaryKey(autoGenerate = true)
    val campaignId: Long = 0,
    val campaignName: String,
    val targetSegment: String, // All Dealers, New Leads, Retail Customers
    val targetCount: Int, // 100, 1000, 10000
    val sentCount: Int = 0,
    val deliveredCount: Int = 0,
    val templateUsed: String,
    val createdDate: String,
    val status: String = "SENT"
)
