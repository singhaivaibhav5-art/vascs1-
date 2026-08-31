package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "research_reports")
data class ResearchReportEntity(
    @PrimaryKey(autoGenerate = true) val reportId: Long = 0,
    val topicTitle: String,
    val domain: String,
    val executiveSummary: String,
    val disruptiveTechnologies: String,
    val futureOpportunityScore: Double,
    val publicationDate: String,
    val aiConfidenceScore: Double
)
