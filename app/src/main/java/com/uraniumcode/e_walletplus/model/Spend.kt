package com.uraniumcode.e_walletplus.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Spend(

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "dateTime ")
    val dateTime: Long?,

    @ColumnInfo(name = "amount")
    val amount: Double?,

    @ColumnInfo(name = "walletId")
    val walletId: Long?
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}