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
import com.uraniumcode.e_walletplus.databinding.FragmentAddWalletBinding
import com.uraniumcode.e_walletplus.model.Wallet
import com.uraniumcode.e_walletplus.utils.Constants
import com.uraniumcode.e_walletplus.viewmodels.AddWalletViewModel

class AddWalletFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddWalletBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddWalletViewModel
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetTransparentTheme)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(), Constants().ADMOB_UNIT_ID, adRequest, object : InterstitialAdLoadCallback() {
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
    ): View {
        _binding = FragmentAddWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AddWalletViewModel::class.java]
        listeners()
        observeLiveData()
    }

    private fun listeners() {
        binding.btnAddWallet.setOnClickListener {
            val walletName = binding.etWalletName.text.toString().trim()
            val walletAmountText = binding.etWalletAmount.text.toString().trim()

            if (walletName.isNotEmpty() && walletAmountText.isNotEmpty() && walletAmountText != ".") {
                val walletAmount = walletAmountText.toDouble()
                val dateTime = System.currentTimeMillis()
                val walletData = Wallet(walletName, dateTime, walletAmount)
                viewModel.addWallet(walletData)

                mInterstitialAd?.show(requireActivity())
            }
        }
    }

    private fun observeLiveData() {
        viewModel.insertedWalletId.observe(viewLifecycleOwner) { insertedWalletId ->
            insertedWalletId?.let {
                findNavController().previousBackStackEntry?.savedStateHandle
                    ?.set(Constants().ADDED_WALLET, insertedWalletId)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
