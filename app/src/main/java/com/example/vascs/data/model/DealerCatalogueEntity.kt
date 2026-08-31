package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dealer_catalogues",
    indices = [
        Index(value = ["catalogueId"], unique = true),
        Index(value = ["dealerId"]),
        Index(value = ["catalogueType"])
    ]
)
data class DealerCatalogueEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val catalogueId: String,
    val dealerId: String = "",
    val title: String,
    val catalogueType: String, // Retail Dealer Catalogue, Wholesale Dealer Catalogue, Distributor Catalogue
    val productIdsJson: String = "[]",
    val fileUri: String = "",
    val createdDate: Long = System.currentTimeMillis(),
    val downloadCount: Int = 0
)
