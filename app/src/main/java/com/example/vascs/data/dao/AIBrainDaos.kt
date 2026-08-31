package com.example.vascs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AIConversationEntity
import com.example.vascs.data.model.AIPromptEntity
import com.example.vascs.data.model.AISuggestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AIPromptDao {
    @Query("SELECT * FROM ai_prompts ORDER BY createdAt DESC")
    fun getAllPrompts(): Flow<List<AIPromptEntity>>

    @Query("SELECT * FROM ai_prompts WHERE featureType = :featureType ORDER BY createdAt DESC")
    fun getPromptsByFeature(featureType: String): Flow<List<AIPromptEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrompt(prompt: AIPromptEntity): Long

    @Query("DELETE FROM ai_prompts")
    suspend fun clearAll()
}

@Dao
interface AIConversationDao {
    @Query("SELECT * FROM ai_conversations ORDER BY timestamp ASC")
    fun getAllConversations(): Flow<List<AIConversationEntity>>

    @Query("SELECT * FROM ai_conversations WHERE domain = :domain ORDER BY timestamp ASC")
    fun getConversationsByDomain(domain: String): Flow<List<AIConversationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: AIConversationEntity): Long

    @Query("DELETE FROM ai_conversations WHERE domain = :domain")
    suspend fun clearDomainConversation(domain: String)

    @Query("DELETE FROM ai_conversations")
    suspend fun clearAll()
}

@Dao
interface AISuggestionDao {
    @Query("SELECT * FROM ai_suggestions ORDER BY createdAt DESC")
    fun getAllSuggestions(): Flow<List<AISuggestionEntity>>

    @Query("SELECT * FROM ai_suggestions WHERE suggestionType = :type ORDER BY createdAt DESC")
    fun getSuggestionsByType(type: String): Flow<List<AISuggestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuggestion(suggestion: AISuggestionEntity): Long

    @Update
    suspend fun updateSuggestion(suggestion: AISuggestionEntity)

    @Query("DELETE FROM ai_suggestions WHERE suggestionId = :id")
    suspend fun deleteSuggestionById(id: Long)

    @Query("DELETE FROM ai_suggestions")
    suspend fun clearAll()
}
