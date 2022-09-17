package com.uraniumcode.e_walletplus.fragments

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.model.Wallet
import com.uraniumcode.e_walletplus.utils.Constants
import com.uraniumcode.e_walletplus.viewmodels.AddWalletViewModel
import kotlinx.android.synthetic.main.fragment_add_wallet.*

class AddWalletFragment() : BottomSheetDialogFragment() {

    private lateinit var viewModel : AddWalletViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetTransparentTheme)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddWalletViewModel::class.java)
        listeners()
        observeLiveData()
    }

    fun listeners(){
        btn_add_wallet.setOnClickListener {
            val walletName = et_wallet_name.text.toString()
            val walletAmount = et_wallet_amount.text.toString().toDouble()
            val dataTime = System.currentTimeMillis()
            val walletData = Wallet(walletName , dataTime, walletAmount)
            viewModel.addWallet(walletData)


        }
    }

    fun observeLiveData(){
        viewModel.insertedWalletId.observe(viewLifecycleOwner, { insertWalletId->
            insertWalletId?.let {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(Constants().ADDED_WALLET, insertWalletId)
                dismiss()
            }
        })
    }

}