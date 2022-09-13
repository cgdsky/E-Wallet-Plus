package com.uraniumcode.e_walletplus.dao

import android.icu.util.CurrencyAmount
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.uraniumcode.e_walletplus.model.Wallet

@Dao
interface WalletDao {

    @Insert
    suspend fun insertData(wallet: Wallet) : Long

    @Query("Select * From Wallet")
    suspend fun getAllWallets() : List<Wallet>

    @Query("Update  Wallet set amount= :amount where id= :walletId")
    suspend fun updateWalletAmount(amount: Double,walletId: Long)

}