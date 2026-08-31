package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "whatsapp_templates")
data class WhatsappTemplateEntity(
    @PrimaryKey(autoGenerate = true)
    val templateId: Long = 0,
    val title: String, // New Arrival, Festival Offer, Dealer Offer, Quotation, Dispatch Update, Payment Reminder, Follow-up
    val templateType: String,
    val content: String,
    val createdDate: String
)
