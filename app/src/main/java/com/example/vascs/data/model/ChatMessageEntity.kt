package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "chat_messages",
    indices = [
        Index(value = ["senderId"]),
        Index(value = ["receiverId"])
    ]
)
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val messageId: Long = 0,
    val senderId: String,
    val receiverId: String,
    val senderRole: String,
    val messageText: String,
    val attachmentUrl: String = "",
    val timestamp: String,
    val isRead: Boolean = false
)
