package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.BillingRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BillingRecordDao {
    @Query("SELECT * FROM billing_records ORDER BY billingId DESC")
    fun getAllBillingRecords(): Flow<List<BillingRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBillingRecord(billing: BillingRecordEntity): Long
}
