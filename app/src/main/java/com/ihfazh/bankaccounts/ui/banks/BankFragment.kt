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
import com.ihfazh.bankaccounts.core.data.Resource
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.databinding.FragmentBanksBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankFragment : Fragment(), OnBankItemClick {

    private val bankViewModel: BankViewModel by viewModels()

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentBanksBinding = FragmentBanksBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onClick(bank: Bank) {
        val action = BankFragmentDirections.actionNavBanksToBankDetailFragment(bank.id, bank.name)
        findNavController().navigate(action)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBanksBinding.bind(view)
        bankViewModel.setSearch("")

        val rvAdapter = BankRecyclerViewAdapter(this)
        binding.rvBanks.apply {
            layoutManager = LinearLayoutManager(context.applicationContext)
            adapter = rvAdapter
        }

        bankViewModel.allBanks.observe(viewLifecycleOwner) { banks ->
            when (banks) {
                is Resource.Loading -> {
                    with(binding) {
                        loading.visibility = View.VISIBLE
                        rvBanks.visibility = View.INVISIBLE
                    }
                }
                is Resource.Success -> {
                    with(binding) {
                        loading.visibility = View.INVISIBLE
                        val data = banks.data
                        if (data != null) {
                            rvBanks.visibility = View.VISIBLE
                            rvAdapter.submitList(data)
                        }
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
    }
}