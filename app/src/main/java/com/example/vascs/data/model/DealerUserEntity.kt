package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dealer_users",
    indices = [
        Index(value = ["dealerId"], unique = true),
        Index(value = ["username"], unique = true)
    ]
)
data class DealerUserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dealerId: String,
    val username: String,
    val passwordHash: String,
    val lastLoginDate: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)
