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
import com.uraniumcode.e_walletplus.adapters.WalletAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var viewModel : HomeViewModel
    private val walletAdapter = WalletAdapter(arrayListOf())


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
        getWallets()



    }
    private fun initViews(){
        recycler_Wallet.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        recycler_Wallet.adapter = walletAdapter
    }
    private fun initListeners(){
        btnWallets.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddWalletFragment()
            Navigation.findNavController(it).navigate(action)
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>("key")?.observe(viewLifecycleOwner) { result ->
            if(result != 0L){
            getWallets()
             }
        }
    }
    private fun observeLiveData() {
        viewModel.walletLiveData.observe(viewLifecycleOwner, { wallets ->

            wallets?.let {
                walletAdapter.updateWalletList(wallets)
            }

        })

    }

    fun getWallets(){
        viewModel.getAllWallets()
        observeLiveData()
    }




}