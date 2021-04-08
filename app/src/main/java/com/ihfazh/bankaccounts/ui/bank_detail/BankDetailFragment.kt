package com.ihfazh.bankaccounts.ui.bank_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ihfazh.bankaccounts.databinding.FragmentBankDetailBinding
import com.squareup.picasso.Picasso

class BankDetailFragment : Fragment() {

    private lateinit var binding: FragmentBankDetailBinding
    val args: BankDetailFragmentArgs by navArgs()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBankDetailBinding.inflate(
                inflater,
                container,
                false
        )
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                BankDetailFragment().apply {
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bank = args.bank
        Picasso.get()
                .load(bank.image)
                .resize(150, 75)
                .into(binding.imgLogo)
        binding.bankDetail.bankName.text = bank.name
        binding.bankDetail.bankCode.text = "Transfer Code: ${bank.code}"
    }
}