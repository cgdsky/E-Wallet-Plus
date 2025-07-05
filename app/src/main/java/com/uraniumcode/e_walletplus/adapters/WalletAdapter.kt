package com.uraniumcode.e_walletplus.adapters

import android.annotation.SuppressLint
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.uraniumcode.e_walletplus.databinding.ItemWalletBinding
import com.uraniumcode.e_walletplus.fragments.HomeFragmentDirections
import com.uraniumcode.e_walletplus.listeners.DatabaseListener
import com.uraniumcode.e_walletplus.model.Wallet
import com.uraniumcode.e_walletplus.utils.AlertDialogHelper
import com.uraniumcode.e_walletplus.utils.Constants

class WalletAdapter(
    private val databaseListener: DatabaseListener?,
    private val walletList: ArrayList<Wallet>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {

    inner class WalletViewHolder(val binding: ItemWalletBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val binding = ItemWalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        setRootViewWidth(binding)
        return WalletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        val wallet = walletList[position]
        val binding = holder.binding

        binding.tvWalletName.text = wallet.name
        binding.tvWalletAmount.text = wallet.amount.toString()

        binding.btnWalletAddSpend.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddSpendFragment(wallet.id, true)
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnWalletAddMoney.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddSpendFragment(wallet.id, false)
            Navigation.findNavController(it).navigate(action)
        }

        binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWalletFragment(wallet)
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnDelete.setOnClickListener {
            databaseListener?.let {
                AlertDialogHelper().showDeleteDialog(binding.root.context, it, wallet.id, false)
            }
        }

        binding.imgRightArrow.setOnClickListener {
            recyclerView.smoothScrollToPosition(position + 1)
        }

        binding.imgLeftArrow.setOnClickListener {
            recyclerView.smoothScrollToPosition(position - 1)
        }

        if (position + 1 == walletList.size && walletList[walletList.size - 1].id == Constants().MONEY_BOX_ID) {
            binding.btnDelete.visibility = View.GONE
        } else {
            binding.btnDelete.visibility = View.VISIBLE
        }

        if (walletList.size > 1) {
            if (position > 0 && position + 1 != walletList.size) {
                binding.imgRightArrow.visibility = View.VISIBLE
                binding.imgLeftArrow.visibility = View.VISIBLE
            } else if (position + 1 == walletList.size) {
                binding.imgRightArrow.visibility = View.GONE
                binding.imgLeftArrow.visibility = View.VISIBLE
            } else {
                binding.imgRightArrow.visibility = View.VISIBLE
                binding.imgLeftArrow.visibility = View.GONE
            }
        } else {
            binding.imgRightArrow.visibility = View.GONE
            binding.imgLeftArrow.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = walletList.size

    private fun setRootViewWidth(binding: ItemWalletBinding) {
        val params = binding.mainRel.layoutParams
        val displayMetrics: DisplayMetrics = binding.root.resources.displayMetrics
        val pxWidth = displayMetrics.widthPixels
        params.width = if (walletList.size > 1) {
            pxWidth - 220
        } else {
            pxWidth
        }
        binding.mainRel.layoutParams = params
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateWalletList(newWalletList: List<Wallet>) {
        walletList.clear()
        walletList.addAll(newWalletList)
        notifyDataSetChanged()
    }
}
