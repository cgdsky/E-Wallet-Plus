package com.uraniumcode.e_walletplus.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.listeners.DatabaseListener
import com.uraniumcode.e_walletplus.model.Spend
import com.uraniumcode.e_walletplus.model.Wallet
import com.uraniumcode.e_walletplus.utils.*
import kotlinx.android.synthetic.main.item_spend.view.*

class SpendAdapter(private val databaseListener: DatabaseListener?, private val spendList: ArrayList<Spend>, private val walletList: ArrayList<Wallet>): RecyclerView.Adapter<SpendAdapter.SpendViewHolder>()  {

    class SpendViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_spend, parent, false)

        return SpendViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpendViewHolder, position: Int) {
        if(spendList[position].amount < 0){
            holder.view.img_spend.setBackgroundResource(R.drawable.ic_remove)
            holder.view.tv_money.setTextColor(ContextCompat.getColor(holder.view.context, R.color.red))
        }else{
            holder.view.img_spend.setBackgroundResource(R.drawable.ic_plus)
            holder.view.tv_money.setTextColor(ContextCompat.getColor(holder.view.context, R.color.green))

        }
        holder.view.tv_title_spend.text = spendList[position].title
        holder.view.tv_money.text = spendList[position].amount.toString()
        if(walletList.size > 0){
            holder.view.tv_wallet_name.text = walletList.get(position).name
        }
        holder.view.tv_date.text = spendList[position].dateTime.dateTime()
        holder.view.btn_delete.setOnClickListener {
            AlertDialogHelper().showDeleteDialog(holder.view.context,databaseListener!!,spendList[position].id,true)
        }
    }

    override fun getItemCount(): Int {
        return spendList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSpendList(newSpendList: List<Spend>, newWalletList: List<Wallet>) {
        spendList.clear()
        walletList.clear()
        spendList.addAll(newSpendList)
        walletList.addAll(newWalletList)
        notifyDataSetChanged()
    }
}