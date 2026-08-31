package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dealer_products",
    indices = [
        Index(value = ["dealerId"]),
        Index(value = ["productId"]),
        Index(value = ["dealerId", "productId"], unique = true)
    ]
)
data class DealerProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dealerId: String,
    val productId: Long,
    val assignedDate: Long = System.currentTimeMillis(),
    val specialPrice: Double = 0.0,
    val status: String = "ACTIVE"
)
