package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ai_agents",
    indices = [
        Index(value = ["agentType"], unique = true)
    ]
)
data class AiAgentEntity(
    @PrimaryKey(autoGenerate = true) val agentId: Long = 0,
    val agentType: String, // Sales Agent, Inventory Agent, Marketing Agent, Finance Agent, Dealer Agent, Customer Agent, Support Agent, Purchase Agent
    val agentName: String,
    val status: String = "Active", // Active, Idle, Executing, Paused
    val tasksCompleted: Int = 124,
    val performanceScore: Double = 98.2,
    val executionMode: String = "Semi Automatic" // Manual, Semi Automatic, Fully Automatic
)
