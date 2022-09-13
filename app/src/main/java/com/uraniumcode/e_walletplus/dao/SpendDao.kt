package com.uraniumcode.e_walletplus.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.uraniumcode.e_walletplus.model.Spend

@Dao
interface SpendDao {

    @Insert
    suspend fun insertData(spend: Spend) : Long

    @Query("Select * From Spend where id = :walletId")
    suspend fun getWalletsAllSpends(walletId: Int) : List<Spend>

    @Query("Select * From Spend order by `dateTime ` DESC")
    suspend fun getLastSpends() : List<Spend>

}