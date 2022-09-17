package com.uraniumcode.e_cuzdanplus.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.uraniumcode.e_walletplus.database.AppDatabase
import com.uraniumcode.e_walletplus.model.Spend
import com.uraniumcode.e_walletplus.model.Wallet
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application)  {


    var walletsLiveData = MutableLiveData<List<Wallet>>()
    var spendsWalletLiveData = MutableLiveData<List<Wallet>>()
    var spendsLiveData = MutableLiveData<List<Spend>>()

    fun getAllWallets() {
        launch {

            val dao = AppDatabase(getApplication()).walletDao()
            val wallets = dao.getAllWallets()
            walletsLiveData.value = wallets

        }

    }

    fun getLastSpends() {
        launch {

            val dao = AppDatabase(getApplication()).spendDao()
            val walletdao = AppDatabase(getApplication()).walletDao()

            val spends = dao.getLastSpends()
            var i = 0
            val walletList: ArrayList<Wallet> = arrayListOf()
            while (i < spends.size) {
                walletList.add(walletdao.getWallet(spends[i].walletId))
                i++
            }
            spendsWalletLiveData.value = walletList
            spendsLiveData.value = spends

        }

    }

}