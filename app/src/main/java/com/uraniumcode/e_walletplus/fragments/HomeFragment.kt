package com.uraniumcode.e_walletplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.uraniumcode.e_cuzdanplus.viewModels.HomeViewModel
import com.uraniumcode.e_walletplus.MainActivity
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.adapters.SpendAdapter
import com.uraniumcode.e_walletplus.adapters.WalletAdapter
import com.uraniumcode.e_walletplus.listeners.DatabaseListener
import com.uraniumcode.e_walletplus.model.Wallet
import com.uraniumcode.e_walletplus.utils.Constants
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), DatabaseListener {
    private lateinit var viewModel: HomeViewModel
    private var databaseListener: DatabaseListener? = null
    private lateinit var walletAdapter: WalletAdapter
    private lateinit var spendAdapter: SpendAdapter
    private lateinit var walletList: ArrayList<Wallet>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseListener = this
        spendAdapter = SpendAdapter(databaseListener, arrayListOf(), arrayListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        initListeners()
        (activity as MainActivity).changeToolbarTitle(getString(R.string.app_name))
        (activity as MainActivity).openCloseButtons(false)
        setAdapters()
        getAllData()
        observeLiveData()
    }

    private fun setAdapters(){
        recycler_Wallet.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recycler_last_spends.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        walletAdapter = WalletAdapter(databaseListener, arrayListOf(),recycler_Wallet)

        recycler_Wallet.adapter = walletAdapter
        recycler_last_spends.adapter = spendAdapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler_Wallet)
    }

    private fun initListeners(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(Constants().ADDED_WALLET)?.observe(viewLifecycleOwner) { result ->
            if(result != 0L){
                viewModel.getAllWallets()
             }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(Constants().ADDED_SPEND)?.observe(viewLifecycleOwner) { result ->
            if(result != 0L){
                getAllData()
            }
        }
    }

    private fun observeLiveData() {
        viewModel.walletsLiveData.observe(viewLifecycleOwner, { wallets ->

            wallets?.let {
                walletList = wallets as ArrayList<Wallet>
                walletAdapter.updateWalletList(wallets)
            }

        })

        viewModel.spendsLiveData.observe(viewLifecycleOwner, { spends ->

            spends?.let {
                viewModel.spendsWalletLiveData.observe(viewLifecycleOwner, { wallets ->

                    wallets?.let {
                        spendAdapter.updateSpendList(spends,wallets)
                    }
                })
            }
        })

        viewModel.spendDeleteLiveData.observe(viewLifecycleOwner, { data ->

            data?.let {
                if(data == 1){
                    viewModel.getLastSpends()
                }
            }
        })

        viewModel.walletDeleteLiveData.observe(viewLifecycleOwner, { data ->

            data?.let {
                if(data == 1){
                    viewModel.getAllWallets()
                }
            }
        })

    }

    private fun getAllData(){
        viewModel.getAllWallets()
        viewModel.isFirstTime()
        viewModel.getLastSpends()
    }

    override fun deleteSpend(spendId: Long) {
        viewModel.deleteSpend(spendId)
    }

    override fun deleteWallet(walletId: Long) {
        viewModel.deleteWallet(walletId)
    }
}