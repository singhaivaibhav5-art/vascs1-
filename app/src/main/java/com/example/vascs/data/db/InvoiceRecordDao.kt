package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.InvoiceRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceRecordDao {
    @Query("SELECT * FROM invoice_records ORDER BY id DESC")
    fun getAllInvoiceRecords(): Flow<List<InvoiceRecordEntity>>

    @Query("SELECT * FROM invoice_records WHERE orderId = :orderId LIMIT 1")
    fun getInvoiceForOrder(orderId: Long): Flow<InvoiceRecordEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvoiceRecord(record: InvoiceRecordEntity): Long
}
