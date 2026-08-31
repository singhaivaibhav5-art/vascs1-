package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.DealerCatalogueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DealerCatalogueDao {

    @Query("SELECT * FROM dealer_catalogues ORDER BY createdDate DESC")
    fun getAllCatalogues(): Flow<List<DealerCatalogueEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(catalogue: DealerCatalogueEntity): Long

    @Delete
    suspend fun delete(catalogue: DealerCatalogueEntity)

    @Query("UPDATE dealer_catalogues SET downloadCount = downloadCount + 1 WHERE id = :id")
    suspend fun incrementDownloadCount(id: Long)
}
