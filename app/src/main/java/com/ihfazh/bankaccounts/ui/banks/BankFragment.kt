package com.ihfazh.bankaccounts.ui.banks

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.data.Resource
import com.ihfazh.bankaccounts.databinding.FragmentBanksBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankFragment : Fragment(), OnBankItemClick {

    private val bankViewModel: BankViewModel by viewModels()
    private lateinit var binding: FragmentBanksBinding

    @SuppressLint("CheckResult")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentBanksBinding.inflate(layoutInflater)
        bankViewModel.setSearch("")

        val rvAdapter = BankRecyclerViewAdapter(this)
        binding.rvBanks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }

        bankViewModel.allBanks.observe(requireActivity()) { banks ->
            when (banks) {
                is Resource.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.rvBanks.visibility = View.INVISIBLE
                }
                is Resource.Success -> {
                    binding.loading.visibility = View.INVISIBLE
                    if (banks.data != null) {
                        binding.rvBanks.visibility = View.VISIBLE
                        rvAdapter.submitList(banks.data!!)
                    }
                }
                is Resource.Error -> {
                    binding.loading.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), banks.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        RxTextView.textChanges(binding.etSearch)
                .skipInitialValue()
                .subscribe {
                    bankViewModel.setSearch(it.toString())
                }


        return binding.root
    }

    override fun onClick(bank: Bank) {
        val action = BankFragmentDirections.actionNavBanksToBankDetailFragment(bank.id)
        findNavController().navigate(action)
    }
}