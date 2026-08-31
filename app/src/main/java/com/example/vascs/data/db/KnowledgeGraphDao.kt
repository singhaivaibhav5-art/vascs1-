package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.KnowledgeGraphEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KnowledgeGraphDao {
    @Query("SELECT * FROM knowledge_graph ORDER BY graphId DESC")
    fun getAllKnowledgeGraph(): Flow<List<KnowledgeGraphEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKnowledgeGraph(graph: KnowledgeGraphEntity): Long

    @Update
    suspend fun updateKnowledgeGraph(graph: KnowledgeGraphEntity)
}
