package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reward_points",
    indices = [
        Index(value = ["userCode"])
    ]
)
data class RewardPointEntity(
    @PrimaryKey(autoGenerate = true) val rewardId: Long = 0,
    val userCode: String,
    val userType: String,
    val pointsEarned: Int,
    val activityDescription: String,
    val dateEarned: String
)
