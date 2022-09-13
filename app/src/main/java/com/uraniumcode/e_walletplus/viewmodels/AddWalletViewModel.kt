package com.uraniumcode.e_walletplus.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.uraniumcode.e_cuzdanplus.viewModels.BaseViewModel
import com.uraniumcode.e_walletplus.database.AppDatabase
import com.uraniumcode.e_walletplus.model.Wallet
import kotlinx.coroutines.launch

class AddWalletViewModel(application: Application) : BaseViewModel(application) {
    val insertedWalletId = MutableLiveData<Long>()

     fun addWallet(data : Wallet) {
        launch {
            val dao = AppDatabase(getApplication()).walletDao()
            insertedWalletId.value = dao.insertData(data)
        }

    }
}