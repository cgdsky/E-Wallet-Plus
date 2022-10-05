package com.uraniumcode.e_walletplus.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.uraniumcode.e_cuzdanplus.viewModels.BaseViewModel
import com.uraniumcode.e_walletplus.database.AppDatabase
import com.uraniumcode.e_walletplus.extensions.timeStamp
import com.uraniumcode.e_walletplus.model.Spend
import com.uraniumcode.e_walletplus.model.Wallet
import kotlinx.coroutines.launch

class WalletViewModel(application: Application) : BaseViewModel(application)  {

    var walletLiveData = MutableLiveData<Wallet>()
    var spendsLiveData = MutableLiveData<List<Spend>>()
    var walletDeleteLiveData = MutableLiveData<Int>()
    var spendDeleteLiveData = MutableLiveData<Int>()

    fun getAllSpends(walletId: Long, month: Int, year: Int) {
        launch {
            val startDate= "01-$month-$year".timeStamp()
            val endDate= "31-$month-$year".timeStamp()

            val dao = AppDatabase(getApplication()).spendDao()
            val spends = dao.getSpendsBetweenTime(walletId,startDate,endDate)

            spendsLiveData.value = spends
        }
    }

    fun getWallet(walletId: Long) {
        launch {
            val dao = AppDatabase(getApplication()).walletDao()
            val wallets = dao.getWallet(walletId)
            walletLiveData.value = wallets
        }
    }

    fun deleteWallet(walletId: Long){
        launch {
            val walletDao = AppDatabase(getApplication()).walletDao()
            val spendDao = AppDatabase(getApplication()).spendDao()
            spendDao.deleteWalletSpends(walletId)
            walletDeleteLiveData.value = walletDao.deleteWallet(walletId)

        }
    }

    fun deleteSpend(spendId: Long){
        launch {
            val spendDao = AppDatabase(getApplication()).spendDao()
            spendDeleteLiveData.value = spendDao.deleteSpend(spendId)
        }
    }

}