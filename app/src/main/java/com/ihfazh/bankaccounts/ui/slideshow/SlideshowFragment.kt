package com.ihfazh.bankaccounts.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihfazh.bankaccounts.R
import com.ihfazh.bankaccounts.databinding.FragmentSlideshowBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SlideshowFragment : Fragment() {

    private val slideshowViewModel: SlideshowViewModel by viewModels()
    private lateinit var binding: FragmentSlideshowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSlideshowBinding.inflate(layoutInflater)

        val rv = binding.rvBankAccountItems
        rv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = BankAccountRecyclerViewAdapter()
        slideshowViewModel.bankAccounts.observe(requireActivity()) {
            adapter.setBanks(it)
        }
        rv.adapter = adapter

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_nav_slideshow_to_bankAccountCreateFragment)
        }

        return binding.root
    }
}