package com.uraniumcode.e_walletplus.utils

import android.app.AlertDialog
import android.content.Context
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.listeners.DatabaseListener

class AlertDialogHelper {

     fun showDeleteDialog(context: Context,databaseListener: DatabaseListener, id: Long, isSpendDelete: Boolean) {
        AlertDialog.Builder(context)
            .setMessage(context.getString(if(isSpendDelete)R.string.spend_delete_dialog_title else R.string.wallet_delete_dialog_title))
            .setCancelable(false)
            .setPositiveButton(context.getString(R.string.yes)) { dialog, _ ->
                if(isSpendDelete){
                    databaseListener.deleteSpend(id)
                }else{
                    databaseListener.deleteWallet(id)
                }
                dialog.cancel()
            }
            .setNegativeButton(context.getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }.show()
    }

}