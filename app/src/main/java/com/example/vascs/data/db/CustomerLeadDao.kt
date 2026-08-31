package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.CustomerLeadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerLeadDao {
    @Query("SELECT * FROM customer_leads ORDER BY leadId DESC")
    fun getAllLeads(): Flow<List<CustomerLeadEntity>>

    @Query("SELECT * FROM customer_leads WHERE status = :status ORDER BY leadId DESC")
    fun getLeadsByStatus(status: String): Flow<List<CustomerLeadEntity>>

    @Query("SELECT * FROM customer_leads WHERE leadId = :leadId LIMIT 1")
    suspend fun getLeadById(leadId: Long): CustomerLeadEntity?

    @Query("SELECT * FROM customer_leads WHERE customerName LIKE '%' || :query || '%' OR mobile LIKE '%' || :query || '%' OR city LIKE '%' || :query || '%'")
    fun searchLeads(query: String): Flow<List<CustomerLeadEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLead(lead: CustomerLeadEntity): Long

    @Update
    suspend fun updateLead(lead: CustomerLeadEntity)

    @Query("UPDATE customer_leads SET status = :status WHERE leadId = :leadId")
    suspend fun updateLeadStatus(leadId: Long, status: String)
}
