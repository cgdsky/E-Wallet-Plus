package com.uraniumcode.e_walletplus.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.uraniumcode.e_walletplus.model.Spend

@Dao
interface SpendDao {

    @Insert
    suspend fun insertData(spend: Spend) : Long

    @Query("Select * From Spend order by `dateTime ` DESC")
    suspend fun getLastSpends() : List<Spend>

    @Query("Select * From Spend where walletId = :walletId and `dateTime ` between :startDate and :endDate order by `dateTime ` DESC")
    suspend fun getSpendsBetweenTime(walletId: Long, startDate: Long, endDate: Long): List<Spend>

}