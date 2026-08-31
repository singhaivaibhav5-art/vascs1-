package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.ExpansionBlueprintEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpansionBlueprintDao {
    @Query("SELECT * FROM expansion_blueprints ORDER BY blueprintId DESC")
    fun getAllExpansionBlueprints(): Flow<List<ExpansionBlueprintEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpansionBlueprint(blueprint: ExpansionBlueprintEntity): Long

    @Update
    suspend fun updateExpansionBlueprint(blueprint: ExpansionBlueprintEntity)
}
