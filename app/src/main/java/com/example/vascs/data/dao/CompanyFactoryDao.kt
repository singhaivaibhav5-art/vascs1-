package com.example.vascs.data.dao

import androidx.room.*
import com.example.vascs.data.model.CompanyFactoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyFactoryDao {
    @Query("SELECT * FROM company_factory ORDER BY companyId DESC")
    fun getAllCompanies(): Flow<List<CompanyFactoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompany(company: CompanyFactoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanies(companies: List<CompanyFactoryEntity>)

    @Update
    suspend fun updateCompany(company: CompanyFactoryEntity)

    @Query("DELETE FROM company_factory")
    suspend fun clearCompanies()
}
