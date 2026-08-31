package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "customer_portals",
    indices = [
        Index(value = ["companyId"])
    ]
)
data class CustomerPortalEntity(
    @PrimaryKey(autoGenerate = true) val portalId: Long = 0,
    val companyId: Long,
    val customerName: String,
    val email: String,
    val status: String = "Active"
)
