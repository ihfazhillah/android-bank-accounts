package com.ihfazh.bankaccounts.ui.bank_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ihfazh.bankaccounts.R
import com.ihfazh.bankaccounts.databinding.FragmentBankDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@AndroidEntryPoint
class BankDetailFragment : Fragment() {

    private lateinit var binding: FragmentBankDetailBinding
    val args: BankDetailFragmentArgs by navArgs()
    val viewModel: BankDetailViewModel by viewModels()
    val compositeDisposable = CompositeDisposable()


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

        viewModel.setId(args.bankId)

        viewModel.bank.observe(requireActivity()) { bank ->
            Picasso.get()
                    .load(bank.image)
                    .resize(150, 75)
                    .into(binding.imgLogo)
            binding.bankDetail.bankName.text = bank.name
            binding.bankDetail.bankCode.text = getString(R.string.transfer_code_text, bank.code)

            setFavoriteButton(bank.favorite)
            (requireActivity() as AppCompatActivity).supportActionBar?.title = bank.name
        }

        binding.bankDetail.btnLove.setOnClickListener {
            val disposable = viewModel.toggleFavorite()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                    }
            compositeDisposable.add(disposable)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                BankDetailFragment().apply {
                }
    }


    private fun setFavoriteButton(active: Boolean) {
        if (active) {
            binding.bankDetail.btnLove.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.bankDetail.btnLove.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}