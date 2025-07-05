package com.uraniumcode.e_walletplus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uraniumcode.e_walletplus.R
import com.uraniumcode.e_walletplus.databinding.FragmentAddSpendBinding
import com.uraniumcode.e_walletplus.model.Spend
import com.uraniumcode.e_walletplus.utils.Constants
import com.uraniumcode.e_walletplus.viewmodels.AddSpendViewModel

class AddSpendFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddSpendBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddSpendViewModel
    private var walletId = 0L
    private var isSpend = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetTransparentTheme)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddSpendBinding.inflate(inflater, container, false)
        return binding.root
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

    private fun changeTitle() {
        if (!isSpend) {
            binding.tvTitle.setText(R.string.add_balance)
        }
    }

    private fun listeners() {
        binding.btnAddSpend.setOnClickListener {
            val title = binding.etSpendTitle.text.toString().trim()
            val amountText = binding.etSpendAmount.text.toString().trim()

            if (title.isNotEmpty() && amountText.isNotEmpty() && amountText != ".") {
                val spendAmount = if (isSpend) amountText.toDouble() * -1 else amountText.toDouble()
                val dateTime = System.currentTimeMillis()
                val spendData = Spend(title, dateTime, spendAmount, walletId)
                viewModel.addSpend(spendData)
            }
        }
    }

    private fun observeLiveData() {
        viewModel.insertedSpendId.observe(viewLifecycleOwner) { id ->
            id?.let {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(Constants().ADDED_SPEND, id)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
