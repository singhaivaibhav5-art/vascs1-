package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.PaymentRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentRecordDao {
    @Query("SELECT * FROM payment_records ORDER BY id DESC")
    fun getAllPaymentRecords(): Flow<List<PaymentRecordEntity>>

    @Query("SELECT * FROM payment_records WHERE dealerId = :dealerId ORDER BY id DESC")
    fun getPaymentRecordsForDealer(dealerId: Long): Flow<List<PaymentRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaymentRecord(record: PaymentRecordEntity): Long
}
