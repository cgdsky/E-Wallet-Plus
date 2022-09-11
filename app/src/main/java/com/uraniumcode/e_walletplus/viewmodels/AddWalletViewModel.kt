package com.uraniumcode.e_walletplus.viewmodels

import android.app.Application
import android.util.Log
import com.uraniumcode.e_cuzdanplus.viewModels.BaseViewModel
import com.uraniumcode.e_walletplus.database.AppDatabase
import com.uraniumcode.e_walletplus.model.Wallet
import kotlinx.coroutines.launch

class AddWalletViewModel(application: Application) : BaseViewModel(application) {

    val walletData = Wallet("wallet name",123123,100.00)
    fun addData(){
        addWallet(walletData)
    }


    private fun addWallet(data : Wallet){
        launch {
            val dao = AppDatabase(getApplication()).walletDao()
            val walletId = dao.insertData(data)
            Log.e("TAG", "getDataFromSQLite: "+walletId)

        }
    }
}