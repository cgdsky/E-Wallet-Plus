package com.uraniumcode.e_walletplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uraniumcode.e_walletplus.MainActivity
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.adapters.SpendAdapter
import com.uraniumcode.e_walletplus.model.Wallet
import com.uraniumcode.e_walletplus.utils.Constants
import com.uraniumcode.e_walletplus.viewmodels.WalletViewModel
import kotlinx.android.synthetic.main.fragment_wallet.*
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList


class WalletFragment : Fragment() {

    private lateinit var viewModel : WalletViewModel
    private lateinit var wallet : Wallet
    private val  spendAdapter = SpendAdapter(arrayListOf(), arrayListOf())
    private val monthNames: Array<String> =  DateFormatSymbols(Locale.getDefault()).months
    private val years: ArrayList<Int> = arrayListOf()
    private var selectedMonth = 0
    private var selectedYear = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            wallet = WalletFragmentArgs.fromBundle(it).wallet
        }
        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        initViews()
        setSpinners()
        listeners()
        observeLiveData()
        setAdapters()

    }

    private fun initViews(){
        tv_wallet_amount.text = wallet.amount.toString()
        (activity as MainActivity).changeToolbarTitle(wallet.name!!)
        (activity as MainActivity).openCloseButtons(true)
    }

    private fun setSpinners(){
        val now = Calendar.getInstance()
        val thisYear = Calendar.getInstance()[Calendar.YEAR]

        for (i in 2020..thisYear) {
            years.add(i)
        }
        val yearAdapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                years
            )
        }
        val monthAdapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                monthNames
            )
        }

        spinner_year.adapter = yearAdapter
        spinner_year.setSelection(years.size - 1)
        spinner_month.adapter = monthAdapter
        spinner_month.setSelection(now.get(Calendar.MONTH))
    }

    private fun listeners(){
        spinner_month.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                selectedMonth = position+1
                getAllSpends()
            }
        }

        spinner_year.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                selectedYear = years[position]
                getAllSpends()
            }
        }

        btn_wallet_add_money.setOnClickListener {
            val action = WalletFragmentDirections.actionWalletFragmentToAddSpendFragment(wallet.id,
                false)
            Navigation.findNavController(it).navigate(action)
        }

        btn_wallet_add_spend.setOnClickListener {
            val action = WalletFragmentDirections.actionWalletFragmentToAddSpendFragment(wallet.id,
                true)
            Navigation.findNavController(it).navigate(action)
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(
            Constants().ADDED_SPEND)?.observe(viewLifecycleOwner) { result ->
            if(result != 0L){
                getAllSpends()
                viewModel.getWallet(wallet.id)
            }
        }
    }

    private fun getAllSpends(){
        if(selectedMonth != 0 && selectedYear != 0){
            viewModel.getAllSpends(wallet.id,selectedMonth,selectedYear)
        }
    }

    private fun setAdapters(){
        recycler_wallet_spends.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        recycler_wallet_spends.adapter = spendAdapter
    }

    private fun observeLiveData() {
        viewModel.spendsLiveData.observe(viewLifecycleOwner, { spends ->
            spends?.let {
                spendAdapter.updateSpendList(spends, arrayListOf())
            }
        })

        viewModel.walletLiveData.observe(viewLifecycleOwner, { data ->
            data?.let {
                wallet = data
                initViews()
            }
        })
    }
}