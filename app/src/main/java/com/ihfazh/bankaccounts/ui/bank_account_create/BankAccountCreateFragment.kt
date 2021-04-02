package com.ihfazh.bankaccounts.ui.bank_account_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihfazh.bankaccounts.data.Resource
import com.ihfazh.bankaccounts.databinding.FragmentBanksBinding
import com.ihfazh.bankaccounts.databinding.FragmentCreateBankAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankAccountCreateFragment : Fragment() {
    private lateinit var binding: FragmentCreateBankAccountBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBankAccountBinding.inflate(layoutInflater)

        return binding.root
    }
}