package com.example.vascs.data.db

import androidx.room.*
import com.example.vascs.data.model.CatalogueGenerationJobEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogueGenerationJobDao {
    @Query("SELECT * FROM catalogue_generation_jobs ORDER BY createdAt DESC")
    fun getAllJobs(): Flow<List<CatalogueGenerationJobEntity>>

    @Query("SELECT * FROM catalogue_generation_jobs WHERE productId = :productId ORDER BY createdAt DESC")
    fun getJobsForProduct(productId: String): Flow<List<CatalogueGenerationJobEntity>>

    @Query("SELECT * FROM catalogue_generation_jobs WHERE jobId = :jobId LIMIT 1")
    suspend fun getJobById(jobId: String): CatalogueGenerationJobEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: CatalogueGenerationJobEntity)

    @Update
    suspend fun updateJob(job: CatalogueGenerationJobEntity)

    @Query("DELETE FROM catalogue_generation_jobs WHERE jobId = :jobId")
    suspend fun deleteJobById(jobId: String)
}
