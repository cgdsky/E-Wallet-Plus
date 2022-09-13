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
            val dao = AppDatabase(getApplication()).spendDao()
            insertedSpendId.value = dao.insertData(data)
        }

    }

}