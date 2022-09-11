package com.uraniumcode.e_walletplus.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wallet(

    @ColumnInfo(name = "walletName")
    val name: String?,

    @ColumnInfo(name = "dateTime ")
    val dateTime: Long?,

    @ColumnInfo(name = "amount")
    val amount: Double?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
