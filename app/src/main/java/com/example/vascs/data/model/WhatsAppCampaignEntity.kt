package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "whatsapp_campaigns",
    indices = [
        Index(value = ["campaignId"], unique = true),
        Index(value = ["campaignType"]),
        Index(value = ["status"])
    ]
)
data class WhatsAppCampaignEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val campaignId: String,
    val title: String,
    val campaignType: String, // SINGLE_PRODUCT, FESTIVAL_OFFER, DEALER_OFFER, WHOLESALE_OFFER, CUSTOM
    val targetDealerType: String = "ALL", // ALL, Retailer, Wholesaler, Distributor, Agent, Online Seller
    val targetDealerCount: Int = 0,
    val messageTemplate: String,
    val productIdsJson: String = "[]",
    val status: String = "DRAFT", // DRAFT, SCHEDULED, COMPLETED, SENT
    val createdDate: Long = System.currentTimeMillis(),
    val sentCount: Int = 0
)
