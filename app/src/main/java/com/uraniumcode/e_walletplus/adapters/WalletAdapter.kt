package com.uraniumcode.e_walletplus.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.model.Wallet
import kotlinx.android.synthetic.main.item_wallet.view.*

class WalletAdapter(val walletList: ArrayList<Wallet>): RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {

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