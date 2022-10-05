package com.uraniumcode.e_walletplus.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.fragments.HomeFragmentDirections
import com.uraniumcode.e_walletplus.listeners.DatabaseListener
import com.uraniumcode.e_walletplus.model.Wallet
import com.uraniumcode.e_walletplus.utils.AlertDialogHelper
import kotlinx.android.synthetic.main.item_wallet.view.*

class WalletAdapter(private val databaseListener: DatabaseListener?, private val walletList: ArrayList<Wallet>): RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {

    class WalletViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_wallet, parent, false)
        return WalletViewHolder(view)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.view.tv_wallet_name.text = walletList[position].name
        holder.view.tv_wallet_amount.text = walletList[position].amount.toString()

        holder.view.btn_wallet_add_spend.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddSpendFragment(walletList[position].id,true)
            Navigation.findNavController(it).navigate(action)
        }
        holder.view.btn_wallet_add_money.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddSpendFragment(walletList[position].id,false)
            Navigation.findNavController(it).navigate(action)
        }
        holder.view.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWalletFragment(walletList[position])
            Navigation.findNavController(it).navigate(action)
        }
        holder.view.btn_delete.setOnClickListener {
            AlertDialogHelper().showDeleteDialog(holder.view.context, databaseListener!!, walletList[position].id,false)
        }
    }

    override fun getItemCount(): Int {
        return walletList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateWalletList(newWalletList: List<Wallet>) {
        walletList.clear()
        walletList.addAll(newWalletList)
        notifyDataSetChanged()
    }
}