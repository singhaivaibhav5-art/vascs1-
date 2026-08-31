package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dealer_portals",
    indices = [
        Index(value = ["companyId"])
    ]
)
data class DealerPortalEntity(
    @PrimaryKey(autoGenerate = true) val portalId: Long = 0,
    val companyId: Long,
    val dealerCode: String,
    val dealerName: String,
    val outstandingBalance: Double = 0.0,
    val status: String = "Active"
)
