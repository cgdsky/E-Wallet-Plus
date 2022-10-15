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
    val walletDao = AppDatabase(getApplication()).walletDao()
    val spendDao = AppDatabase(getApplication()).spendDao()

    fun getAllSpends(walletId: Long, month: Int, year: Int) {
        launch {
            val startDate= "01-$month-$year".timeStamp()
            val endDate= "31-$month-$year".timeStamp()

            val spends = spendDao.getSpendsBetweenTime(walletId,startDate,endDate)

            spendsLiveData.value = spends
        }
    }

    fun getWallet(walletId: Long) {
        launch {
            val wallets = walletDao.getWallet(walletId)
            walletLiveData.value = wallets
        }
    }

    fun deleteWallet(walletId: Long){
        launch {

            spendDao.deleteWalletSpends(walletId)
            walletDeleteLiveData.value = walletDao.deleteWallet(walletId)

        }
    }

    fun deleteSpend(spendId: Long){
        launch {
            val spend = spendDao.getSpend(spendId)
            val wallet = walletDao.getWallet(spend.walletId)
            spendDeleteLiveData.value = spendDao.deleteSpend(spendId)
            val totalWalletAmount = wallet.amount!! - spend.amount
            walletDao.updateWalletAmount(totalWalletAmount,spend.walletId)
            getWallet(wallet.id)
        }
    }

}