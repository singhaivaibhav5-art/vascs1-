package com.example.vascs.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AICatalogueRequestEntity
import com.example.vascs.data.model.AICatalogueResultEntity
import com.example.vascs.data.model.AICatalogueTemplateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AICatalogueDao {

    // Requests
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: AICatalogueRequestEntity): Long

    @Query("SELECT * FROM ai_catalogue_requests ORDER BY createdAt DESC")
    fun getAllRequests(): Flow<List<AICatalogueRequestEntity>>

    // Results (History)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: AICatalogueResultEntity): Long

    @Update
    suspend fun updateResult(result: AICatalogueResultEntity)

    @Query("SELECT * FROM ai_catalogue_results ORDER BY createdAt DESC")
    fun getAllResults(): Flow<List<AICatalogueResultEntity>>

    @Query("SELECT * FROM ai_catalogue_results WHERE resultId = :id LIMIT 1")
    fun getResultById(id: Long): Flow<AICatalogueResultEntity?>

    @Query("SELECT * FROM ai_catalogue_results WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoriteResults(): Flow<List<AICatalogueResultEntity>>

    @Query("DELETE FROM ai_catalogue_results WHERE resultId = :id")
    suspend fun deleteResultById(id: Long)

    @Delete
    suspend fun deleteResult(result: AICatalogueResultEntity)

    @Query("DELETE FROM ai_catalogue_results")
    suspend fun clearAllResults()

    // Templates
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: AICatalogueTemplateEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplates(templates: List<AICatalogueTemplateEntity>)

    @Query("SELECT * FROM ai_catalogue_templates ORDER BY templateId ASC")
    fun getAllTemplates(): Flow<List<AICatalogueTemplateEntity>>

    @Query("SELECT COUNT(*) FROM ai_catalogue_templates")
    suspend fun getTemplateCount(): Int

    @Delete
    suspend fun deleteTemplate(template: AICatalogueTemplateEntity)
}
