package com.uraniumcode.e_walletplus.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Wallet(

    @ColumnInfo(name = "walletName")
    val name: String?,

    @ColumnInfo(name = "dateTime ")
    val dateTime: Long?,

    @ColumnInfo(name = "amount")
    val amount: Double?
):Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
