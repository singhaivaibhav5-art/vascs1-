package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.ImportDocumentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImportDocumentDao {
    @Query("SELECT * FROM import_documents ORDER BY documentId DESC")
    fun getAllImportDocuments(): Flow<List<ImportDocumentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImportDocument(doc: ImportDocumentEntity): Long
}
