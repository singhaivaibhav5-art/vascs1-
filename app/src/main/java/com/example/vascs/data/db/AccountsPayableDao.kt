package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AccountsPayableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsPayableDao {
    @Query("SELECT * FROM payables ORDER BY id DESC")
    fun getAllPayables(): Flow<List<AccountsPayableEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayable(payable: AccountsPayableEntity): Long

    @Update
    suspend fun updatePayable(payable: AccountsPayableEntity)
}
