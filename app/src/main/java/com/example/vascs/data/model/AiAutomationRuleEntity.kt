package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_automation_rules")
data class AiAutomationRuleEntity(
    @PrimaryKey(autoGenerate = true) val ruleId: Long = 0,
    val triggerCondition: String, // e.g., IF Stock < 10
    val actionCommand: String, // e.g., THEN Create Reorder Alert
    val isActive: Boolean = true,
    val executionCount: Int = 0
)
