package com.uraniumcode.e_cuzdanplus.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.uraniumcode.e_walletplus.database.AppDatabase
import com.uraniumcode.e_walletplus.model.Spend
import com.uraniumcode.e_walletplus.model.Wallet
import kotlinx.coroutines.launch
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.utils.Constants


class HomeViewModel(application: Application) : BaseViewModel(application)  {


    var walletsLiveData = MutableLiveData<List<Wallet>>()
    var spendsWalletLiveData = MutableLiveData<List<Wallet>>()
    var spendsLiveData = MutableLiveData<List<Spend>>()
    var spendDeleteLiveData = MutableLiveData<Int>()
    var walletDeleteLiveData = MutableLiveData<Int>()
    var prefs: SharedPreferences = application.getSharedPreferences(Constants().PREF_NAME, MODE_PRIVATE)
    val walletDao = AppDatabase(getApplication()).walletDao()
    val spendDao = AppDatabase(getApplication()).spendDao()


    fun getAllWallets() {
        launch {

            val wallets = walletDao.getAllWallets()
            walletsLiveData.value = wallets

        }
    }

    fun getLastSpends() {
        launch {


            val spends = spendDao.getLastSpends()
            var i = 0
            val walletList: ArrayList<Wallet> = arrayListOf()
            while (i < spends.size) {
                walletList.add(walletDao.getWallet(spends[i].walletId))
                i++
            }
            spendsWalletLiveData.value = walletList
            spendsLiveData.value = spends

        }

    }

    fun deleteSpend(spendId: Long){
        launch {
            val spend = spendDao.getSpend(spendId)
            val wallet = walletDao.getWallet(spend.walletId)
            spendDeleteLiveData.value = spendDao.deleteSpend(spendId)
            val totalWalletAmount = wallet.amount!! - spend.amount
            walletDao.updateWalletAmount(totalWalletAmount,spend.walletId)
            getAllWallets()

        }
    }

    fun deleteWallet(walletId: Long){
        launch {
            walletDeleteLiveData.value = walletDao.deleteWallet(walletId)
        }
    }

    fun isFirstTime(){
        launch {
            if (prefs.getBoolean(Constants().PREF_FIRST_TIME, true))
            {
                val editor = prefs.edit()
                editor.putBoolean(Constants().PREF_FIRST_TIME, false)
                editor.apply()
                val walletName = getApplication<Application>().getString(R.string.moneyBox)
                val walletAmount = 0.0
                val dataTime = System.currentTimeMillis()
                val walletData = Wallet(walletName , dataTime, walletAmount)
                walletDao.insertData(walletData)
                getAllWallets()
            }
        }
    }

}