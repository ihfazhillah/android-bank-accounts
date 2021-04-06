package com.ihfazh.bankaccounts.ui.banks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihfazh.bankaccounts.data.Resource
import com.ihfazh.bankaccounts.databinding.FragmentBanksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankFragment : Fragment() {

    private val bankViewModel: BankViewModel by viewModels()
    private lateinit var binding: FragmentBanksBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBanksBinding.inflate(layoutInflater)

        val rvAdapter = BankRecyclerViewAdapter()
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
                    binding.rvBanks.visibility = View.VISIBLE
                    if (banks.data != null)
                        rvAdapter.submitList(banks.data!!)

                }
                is Resource.Error -> {
                    binding.loading.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), banks.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }
}