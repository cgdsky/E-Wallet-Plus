package com.uraniumcode.e_walletplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uraniumcode.e_cuzdanplus.viewModels.HomeViewModel
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.adapters.SpendAdapter
import com.uraniumcode.e_walletplus.adapters.WalletAdapter
import com.uraniumcode.e_walletplus.utils.Constants
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var viewModel : HomeViewModel
    private val walletAdapter = WalletAdapter(arrayListOf())
    private val  spendAdapter = SpendAdapter(arrayListOf(), arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        initViews()
        getAllData()
        observeLiveData()

    }

    private fun initViews(){
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

        recycler_Wallet.adapter = walletAdapter
        recycler_last_spends.adapter = spendAdapter
    }

    private fun initListeners(){
        btn_wallet.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddWalletFragment()
            Navigation.findNavController(it).navigate(action)
        }

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

    }

    private fun getAllData(){
        viewModel.getAllWallets()
        viewModel.getLastSpends()
    }

}