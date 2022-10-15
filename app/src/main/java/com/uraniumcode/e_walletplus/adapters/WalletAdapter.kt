package com.uraniumcode.e_walletplus.adapters

import android.annotation.SuppressLint
import android.view.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.fragments.HomeFragmentDirections
import com.uraniumcode.e_walletplus.listeners.DatabaseListener
import com.uraniumcode.e_walletplus.model.Wallet
import com.uraniumcode.e_walletplus.utils.AlertDialogHelper
import kotlinx.android.synthetic.main.item_wallet.view.*

import android.util.DisplayMetrics
import com.uraniumcode.e_walletplus.utils.Constants

class WalletAdapter(private val databaseListener: DatabaseListener?, private val walletList: ArrayList<Wallet>, private val recyclerView: RecyclerView): RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {

    class WalletViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }
    private  var view: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val inflater = LayoutInflater.from(parent.context)
         view = inflater.inflate(R.layout.item_wallet, parent, false)

        return WalletViewHolder(view!!)
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
        holder.view.img_right_arrow.setOnClickListener{
            recyclerView.smoothScrollToPosition(position + 1)
        }
        holder.view.img_left_arrow.setOnClickListener{
            recyclerView.smoothScrollToPosition(position - 1)
        }

        if(position+1 == walletList.size && walletList[walletList.size - 1].id ==  Constants().MONEY_BOX_ID ){
            holder.view.btn_delete.visibility = View.GONE
        }else{
            holder.view.btn_delete.visibility = View.VISIBLE
        }

        if(walletList.size > 1){
            if(position > 0 && position+1 != walletList.size){
                holder.view.img_right_arrow.visibility = View.VISIBLE
                holder.view.img_left_arrow.visibility = View.VISIBLE
            }else if(position+1 == walletList.size){
                holder.view.img_right_arrow.visibility = View.GONE
                holder.view.img_left_arrow.visibility = View.VISIBLE
            }else{
                holder.view.img_right_arrow.visibility = View.VISIBLE
                holder.view.img_left_arrow.visibility = View.GONE
            }

        }else{
            holder.view.img_right_arrow.visibility = View.GONE
            holder.view.img_left_arrow.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return walletList.size
    }


    private fun setRootViewWidth(){
        val params = view!!.main_rel.layoutParams
        val displayMetrics: DisplayMetrics = view!!.resources.displayMetrics
        val pxWidth = displayMetrics.widthPixels
        if(walletList.size > 1) {

            params.width = pxWidth - 220
        }else{
            params.width = pxWidth
        }

        view!!.main_rel.layoutParams = params
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateWalletList(newWalletList: List<Wallet>) {
        walletList.clear()
        walletList.addAll(newWalletList)
        notifyDataSetChanged()
    }
}