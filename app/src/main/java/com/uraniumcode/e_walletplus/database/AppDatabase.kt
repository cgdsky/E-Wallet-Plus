package com.uraniumcode.e_walletplus.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uraniumcode.e_walletplus.dao.WalletDao
import com.uraniumcode.e_walletplus.model.Wallet

@Database(entities = arrayOf(Wallet::class),version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun walletDao() : WalletDao

    companion object {
        @Volatile private var instance : AppDatabase? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }


        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,AppDatabase::class.java,"walletDatabase"
        ).build()
    }
}