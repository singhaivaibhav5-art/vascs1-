package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val sku: String,
    val barcode: String,
    val category: String,
    val brand: String,
    val fabric: String,
    val colour: String,
    val size: String,
    val hsn: String,
    val gst: Double,
    val purchasePrice: Double,
    val wholesalePrice: Double,
    val retailPrice: Double,
    val mrp: Double,
    val discount: Double,
    val stock: Int,
    val image: String,
    val imagesJson: String = "[]",
    val createdAt: String
)
