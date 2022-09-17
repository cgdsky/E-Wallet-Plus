package com.uraniumcode.e_walletplus.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.uraniumcode.e_cuzdanplus.viewModels.BaseViewModel
import com.uraniumcode.e_walletplus.database.AppDatabase
import com.uraniumcode.e_walletplus.model.Spend
import kotlinx.coroutines.launch

class AddSpendViewModel(application: Application) : BaseViewModel(application) {
    val insertedSpendId = MutableLiveData<Long>()

    fun addSpend(data : Spend) {
        launch {
            val walletDao = AppDatabase(getApplication()).walletDao()
            val spendDao = AppDatabase(getApplication()).spendDao()

            val wallet = walletDao.getWallet(data.walletId)
            val walletAmount = wallet.amount!! + data.amount

            walletDao.updateWalletAmount(walletAmount,data.walletId)
            insertedSpendId.value = spendDao.insertData(data)
        }

    }

}