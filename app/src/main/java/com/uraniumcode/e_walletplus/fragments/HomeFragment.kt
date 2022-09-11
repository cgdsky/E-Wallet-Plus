package com.uraniumcode.e_walletplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.uraniumcode.e_cuzdanplus.viewModels.HomeViewModel
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.adapters.WalletAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var viewModel : HomeViewModel
    private val walletAdapter = WalletAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getAllWallets()
        recycler_Wallet.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        recycler_Wallet.adapter = walletAdapter
        btnWallets.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddWalletFragment()
            Navigation.findNavController(it).navigate(action)
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.walletLiveData.observe(viewLifecycleOwner, { wallets ->

            wallets?.let {
                walletAdapter.updateWalletList(wallets)
            }

        })
    }


}