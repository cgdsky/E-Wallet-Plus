package com.uraniumcode.e_walletplus.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.model.Spend
import com.uraniumcode.e_walletplus.utils.Constants
import com.uraniumcode.e_walletplus.viewmodels.AddSpendViewModel
import kotlinx.android.synthetic.main.fragment_add_spend.*
import kotlinx.android.synthetic.main.fragment_add_spend.tv_title
import kotlinx.android.synthetic.main.fragment_add_wallet.*


class AddSpendFragment : BottomSheetDialogFragment() {
    private lateinit var viewModel : AddSpendViewModel
    private var walletId = 0L
    private var isSpend = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetTransparentTheme)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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
            isSpend = AddSpendFragmentArgs.fromBundle(it).isSpend
        }
        changeTitle()
        listeners()
        observeLiveData()
    }


    private fun changeTitle(){
        if(!isSpend){
            tv_title.setText(R.string.add_balance)
        }
    }
    private fun listeners(){
        btn_add_spend.setOnClickListener {
            if(et_spend_title.text.toString().trim() != "" && et_spend_amount.text.toString().trim() !=  "" && et_spend_amount.text.toString().trim() !=  "." ) {
                    val spendTitle = et_spend_title.text.toString()
                    val spendAmount = if (isSpend) et_spend_amount.text.toString()
                        .toDouble() * -1 else et_spend_amount.text.toString().toDouble()
                    val dataTime = System.currentTimeMillis()
                    val spendData = Spend(spendTitle, dataTime, spendAmount, walletId)
                    viewModel.addSpend(spendData)
            }
        }
    }


    private fun observeLiveData(){
        viewModel.insertedSpendId.observe(viewLifecycleOwner, { id ->
            id?.let {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(Constants().ADDED_SPEND, id)
                dismiss()
            }
        })
    }


}