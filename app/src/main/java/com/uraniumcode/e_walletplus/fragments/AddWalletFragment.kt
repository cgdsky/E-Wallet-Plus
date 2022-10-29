package com.uraniumcode.e_walletplus.fragments

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.model.Wallet
import com.uraniumcode.e_walletplus.utils.Constants
import com.uraniumcode.e_walletplus.viewmodels.AddWalletViewModel
import kotlinx.android.synthetic.main.fragment_add_wallet.*

class AddWalletFragment() : BottomSheetDialogFragment() {

    private lateinit var viewModel : AddWalletViewModel
    private var mInterstitialAd: InterstitialAd? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetTransparentTheme)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context!!,Constants().ADMOB_UNIT_ID, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                mInterstitialAd = interstitialAd

            }
        })


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

    private fun listeners(){
        btn_add_wallet.setOnClickListener {
            if(et_wallet_name.text.toString().trim() != ""  && et_wallet_amount.text.toString().trim() != "" && et_wallet_amount.text.toString().trim() !=  "." ) {
                    val walletName = et_wallet_name.text.toString()
                    val walletAmount = et_wallet_amount.text.toString().toDouble()
                    val dataTime = System.currentTimeMillis()
                    val walletData = Wallet(walletName, dataTime, walletAmount)
                    viewModel.addWallet(walletData)
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(requireActivity())
                }
            }

        }
    }

    private fun observeLiveData(){
        viewModel.insertedWalletId.observe(viewLifecycleOwner, { insertWalletId->
            insertWalletId?.let {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(Constants().ADDED_WALLET, insertWalletId)
                dismiss()
            }
        })
    }

}