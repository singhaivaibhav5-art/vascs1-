package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "social_analytics",
    indices = [
        Index(value = ["eventType"]),
        Index(value = ["dealerId"]),
        Index(value = ["productId"])
    ]
)
data class SocialAnalyticsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val eventType: String, // WHATSAPP_SHARE, TELEGRAM_SHARE, INSTAGRAM_EXPORT, FACEBOOK_EXPORT, DEALER_DOWNLOAD, DEALER_ORDER
    val dealerId: String = "",
    val productId: Long = 0L,
    val productName: String = "",
    val channel: String = "", // WhatsApp, Telegram, Instagram, Facebook, PDF
    val timestamp: Long = System.currentTimeMillis()
)
