package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "knowledge_graph",
    indices = [
        Index(value = ["sourceNodeType"])
    ]
)
data class KnowledgeGraphEntity(
    @PrimaryKey(autoGenerate = true) val graphId: Long = 0,
    val sourceNode: String, // e.g. Product: Kanjeevaram Silk Saree
    val sourceNodeType: String, // Product, Dealer, Customer, Campaign, Market
    val targetNode: String, // e.g. Dealer: Vikas Sarees Wholesale Network
    val targetNodeType: String,
    val relationshipType: String, // High Conversion Affinity, Cross-Sell Opportunity, Supply Chain Link
    val weightStrength: Double = 0.95
)
