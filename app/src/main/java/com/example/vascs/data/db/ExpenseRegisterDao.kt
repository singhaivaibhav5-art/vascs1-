package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.ExpenseRegisterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseRegisterDao {
    @Query("SELECT * FROM expense_register ORDER BY expenseId DESC")
    fun getAllExpenses(): Flow<List<ExpenseRegisterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseRegisterEntity): Long
}
