package com.uraniumcode.e_walletplus.listeners

interface DatabaseListener {
    fun deleteSpend(spendId: Long)
    fun deleteWallet(walletId: Long)

}