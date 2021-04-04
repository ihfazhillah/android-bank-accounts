package com.ihfazh.bankaccounts.ui.bank_account_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ihfazh.bankaccounts.R
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.data.Resource
import com.ihfazh.bankaccounts.databinding.FragmentCreateBankAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@AndroidEntryPoint
class BankAccountCreateFragment : Fragment() {
    private lateinit var binding: FragmentCreateBankAccountBinding
    private val viewModel: BankAccountCreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateBankAccountBinding.inflate(layoutInflater)

        viewModel.allBanks.observe(requireActivity()) { bankResource ->
            when (bankResource) {
                is Resource.Loading -> {
                    binding.searchBank.isClickable = false
                }
                is Resource.Success -> {
                    if (bankResource.data != null) {
                        binding.searchBank.isClickable = true
                        binding.searchBank.setTitle("Select Bank")

                        binding.searchBank.adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            bankResource.data!!
                        )
                        binding.searchBank.setPositiveButton("Yes")


                    }

                }
                    else ->
                        binding.searchBank.isClickable = true
                }
        }

        binding.btnSave.setOnClickListener{
            val account_holder = binding.etAccountHolder.text.toString()
            val account_number = binding.etAccountNumber.text.toString()

            val bank: Bank = binding.searchBank.selectedItem as Bank


            viewModel.insertBankAccount(BankAccount(null, bank, account_holder, account_number))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe {
                    findNavController().apply {
                        navigate(R.id.action_bankAccountCreateFragment_to_nav_slideshow)
                    }
                }

        }

        return binding.root
    }
}