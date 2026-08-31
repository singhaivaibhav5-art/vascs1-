package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "white_label_configs",
    indices = [
        Index(value = ["companyId"], unique = true)
    ]
)
data class WhiteLabelConfigEntity(
    @PrimaryKey(autoGenerate = true) val configId: Long = 0,
    val companyId: Long,
    val brandName: String,
    val logoUrl: String = "",
    val primaryColorHex: String = "#1E88E5",
    val secondaryColorHex: String = "#D32F2F",
    val customDomain: String = "",
    val status: String = "Active"
)
