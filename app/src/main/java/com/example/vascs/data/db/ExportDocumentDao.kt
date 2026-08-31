package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.ExportDocumentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExportDocumentDao {
    @Query("SELECT * FROM export_documents ORDER BY documentId DESC")
    fun getAllExportDocuments(): Flow<List<ExportDocumentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExportDocument(doc: ExportDocumentEntity): Long
}
