package com.uraniumcode.e_walletplus.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.databinding.ItemSpendBinding
import com.uraniumcode.e_walletplus.listeners.DatabaseListener
import com.uraniumcode.e_walletplus.model.Spend
import com.uraniumcode.e_walletplus.model.Wallet
import com.uraniumcode.e_walletplus.utils.AlertDialogHelper
import com.uraniumcode.e_walletplus.utils.dateTime

class SpendAdapter(
    private val databaseListener: DatabaseListener?,
    private val spendList: ArrayList<Spend>,
    private val walletList: ArrayList<Wallet>
) : RecyclerView.Adapter<SpendAdapter.SpendViewHolder>() {

    class SpendViewHolder(val binding: ItemSpendBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendViewHolder {
        val binding = ItemSpendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpendViewHolder, position: Int) {
        val spend = spendList[position]
        val binding = holder.binding

        if (spend.amount < 0) {
            binding.imgSpend.setBackgroundResource(R.drawable.ic_remove)
            binding.tvMoney.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red))
        } else {
            binding.imgSpend.setBackgroundResource(R.drawable.ic_plus)
            binding.tvMoney.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green))
        }

        binding.tvTitleSpend.text = spend.title
        binding.tvMoney.text = spend.amount.toString()

        if (walletList.size > position) {
            binding.tvWalletName.text = walletList[position].name
        } else {
            binding.tvWalletName.text = ""
        }

        binding.tvDate.text = spend.dateTime.dateTime()

        binding.btnDelete.setOnClickListener {
            databaseListener?.let {
                AlertDialogHelper().showDeleteDialog(binding.root.context, it, spend.id, true)
            }
        }
    }

    override fun getItemCount(): Int = spendList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateSpendList(newSpendList: List<Spend>, newWalletList: List<Wallet>) {
        spendList.clear()
        walletList.clear()
        spendList.addAll(newSpendList)
        walletList.addAll(newWalletList)
        notifyDataSetChanged()
    }
}
