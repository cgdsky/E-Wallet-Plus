package com.uraniumcode.e_walletplus.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.model.Spend
import com.uraniumcode.e_walletplus.viewmodels.AddSpendViewModel
import kotlinx.android.synthetic.main.fragment_add_spend.*


class AddSpendFragment : BottomSheetDialogFragment() {
    private lateinit var viewModel : AddSpendViewModel
    private var walletId = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetTransparentTheme)
        getActivity()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_spend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddSpendViewModel::class.java)
        arguments?.let {
            walletId = AddSpendFragmentArgs.fromBundle(it).walletId
        }
        listeners()
        observeLiveData()
    }

    fun listeners(){
        btn_add_spend.setOnClickListener {
            val spendTitle = et_spend_title.text.toString()
            val spendAmount = et_spend_amount.text.toString().toDouble()
            val dataTime = System.currentTimeMillis()
            val spendData = Spend(spendTitle, dataTime, spendAmount, walletId)

            viewModel.addSpend(spendData)


        }
    }


    fun observeLiveData(){

    }


}