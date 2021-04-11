package com.ihfazh.bankaccounts.ui.bank_account_create

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.data.Resource
import com.ihfazh.bankaccounts.databinding.FragmentBanksBinding
import com.ihfazh.bankaccounts.ui.banks.OnBankItemClick
import com.jakewharton.rxbinding2.widget.RxTextView

class ListBankDialogFragment : BottomSheetDialogFragment(), OnBankItemClick {
    companion object {
        val TAG = "ListBankDialogFragment"
    }

    private lateinit var binding: FragmentBanksBinding
    private val viewModel: CreateAccountViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBanksBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder = AlertDialog.Builder(requireContext()).apply {
//            setTitle("Select a Bank")
//        }
//        return builder.create()
//    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setTitle("Select Bank")
        viewModel.setSearch("")

        val rvAdapter = CreateBankListAdapter(this)
        binding.rvBanks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }
        viewModel.allBanks.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error ->
                    dismiss()
                is Resource.Loading -> {
                    binding.rvBanks.visibility = View.INVISIBLE
                    binding.loading.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        binding.rvBanks.visibility = View.VISIBLE
                        binding.loading.visibility = View.INVISIBLE
                        rvAdapter.submitList(it.data)
                    }
                }
            }

        }

        RxTextView.textChanges(binding.etSearch)
                .skipInitialValue()
                .subscribe {
                    viewModel.setSearch(it.toString())
                }
    }

    override fun onClick(bank: Bank) {
        viewModel.setBank(bank)
        dismiss()
    }
}