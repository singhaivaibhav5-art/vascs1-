package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ai_brain",
    indices = [
        Index(value = ["brainModule"])
    ]
)
data class AiBrainEntity(
    @PrimaryKey(autoGenerate = true) val brainId: Long = 0,
    val brainModule: String, // Thinking, Reasoning, Memory, Decision, Planning
    val memoryKeysCount: Int = 12500,
    val reasoningAccuracy: Double = 99.4,
    val currentThought: String,
    val lastSyncTimestamp: String
)
