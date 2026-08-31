package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.KnowledgeWebEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KnowledgeWebDao {
    @Query("SELECT * FROM knowledge_web ORDER BY webId DESC")
    fun getAllKnowledgeWeb(): Flow<List<KnowledgeWebEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKnowledgeWeb(item: KnowledgeWebEntity): Long

    @Update
    suspend fun updateKnowledgeWeb(item: KnowledgeWebEntity)
}
