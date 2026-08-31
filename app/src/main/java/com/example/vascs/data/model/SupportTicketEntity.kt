package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "support_tickets",
    indices = [
        Index(value = ["companyId"])
    ]
)
data class SupportTicketEntity(
    @PrimaryKey(autoGenerate = true) val ticketId: Long = 0,
    val companyId: Long,
    val subject: String,
    val category: String = "General", // Technical, Billing, Feature, Bug
    val priority: String = "Medium", // Low, Medium, High, Urgent
    val status: String = "Open", // Open, In Progress, Resolved, Closed
    val createdDate: String
)
