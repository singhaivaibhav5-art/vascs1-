package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.BoardDecisionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardDecisionDao {
    @Query("SELECT * FROM board_decisions ORDER BY decisionId DESC")
    fun getAllBoardDecisions(): Flow<List<BoardDecisionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBoardDecision(decision: BoardDecisionEntity): Long

    @Update
    suspend fun updateBoardDecision(decision: BoardDecisionEntity)
}
