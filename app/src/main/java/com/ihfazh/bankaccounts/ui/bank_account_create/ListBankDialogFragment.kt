package com.ihfazh.bankaccounts.ui.bank_account_create

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

class ListBankDialogFragment : BottomSheetDialogFragment(), OnBankItemClick {
    companion object {
        val TAG = "ListBankDialogFragment"
    }

    private lateinit var binding: FragmentBanksBinding
    private val viewModel: CreateAccountViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBanksBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder = AlertDialog.Builder(requireContext()).apply {
//            setTitle("Select a Bank")
//        }
//        return builder.create()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setTitle("Select Bank")


        val rvAdapter = CreateBankListAdapter(this)
        binding.rvBanks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }
        viewModel.getAllBanks().observe(viewLifecycleOwner) {
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

    }

    override fun onClick(bank: Bank) {
        viewModel.setBank(bank)
        dismiss()
    }
}