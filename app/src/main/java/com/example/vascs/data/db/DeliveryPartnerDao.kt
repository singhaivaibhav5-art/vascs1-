package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.DeliveryPartnerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliveryPartnerDao {
    @Query("SELECT * FROM delivery_partners ORDER BY partnerId DESC")
    fun getAllDeliveryPartners(): Flow<List<DeliveryPartnerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeliveryPartner(partner: DeliveryPartnerEntity): Long

    @Update
    suspend fun updateDeliveryPartner(partner: DeliveryPartnerEntity)
}
