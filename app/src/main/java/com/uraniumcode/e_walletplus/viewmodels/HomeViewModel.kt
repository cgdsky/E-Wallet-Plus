package com.uraniumcode.e_cuzdanplus.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.uraniumcode.e_walletplus.database.AppDatabase
import com.uraniumcode.e_walletplus.model.Wallet
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application)  {


    var walletLiveData = MutableLiveData<List<Wallet>>()

    fun getAllWallets() {
        launch {

            val dao = AppDatabase(getApplication()).walletDao()
            val wallets = dao.getAllWallets()
            walletLiveData.value = wallets

        }

    }
}