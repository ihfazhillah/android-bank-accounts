package com.ihfazh.bankaccounts.ui.banks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    ): View? {
        binding = FragmentBanksBinding.inflate(layoutInflater)

        val rv = binding.rvBanks
        rv.layoutManager = LinearLayoutManager(requireContext())

        val adapter = BankRecyclerViewAdapter()
        rv.adapter = adapter

        bankViewModel.allBanks.observe(requireActivity()){
            when(it){
               is Resource.Success ->
                   if (it.data != null)
                       adapter.setBanks(it.data)
                else -> {}
            }
        }

        return binding.root
    }
}