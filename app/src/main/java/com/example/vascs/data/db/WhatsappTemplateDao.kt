package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.WhatsappTemplateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WhatsappTemplateDao {
    @Query("SELECT * FROM whatsapp_templates ORDER BY templateId ASC")
    fun getAllTemplates(): Flow<List<WhatsappTemplateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: WhatsappTemplateEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplates(templates: List<WhatsappTemplateEntity>)
}
