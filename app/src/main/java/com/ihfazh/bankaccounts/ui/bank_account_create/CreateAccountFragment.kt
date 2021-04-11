package com.ihfazh.bankaccounts.ui.bank_account_create

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ihfazh.bankaccounts.R
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.databinding.FragmentCreateAccountBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CreateAccountFragment : Fragment() {
    private lateinit var binding: FragmentCreateAccountBinding
    private val viewModel: CreateAccountViewModel by viewModels()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val compositeDisposable = CompositeDisposable()
    private val args: CreateAccountFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateAccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateAccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bankAccount = args.account
        if (bankAccount != null) {
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "Update Account"
            viewModel.setBank(bankAccount.bank)
            binding.etAccountHolder.setText(bankAccount.account_holder)
            binding.etAccountNumber.setText(bankAccount.account_number)
        }

        viewModel.bank.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.tvNoBankSelected.visibility = View.VISIBLE
                binding.bankItem.root.visibility = View.GONE
            } else {
                binding.tvNoBankSelected.visibility = View.GONE
                binding.bankItem.apply {
                    root.visibility = View.VISIBLE
                    tvName.text = it.name
                    tvCode.text = it.name
                    Picasso.get()
                            .load(it.image)
                            .into(imgLogo)
                }
                binding.btnAddBank.text = getString(R.string.update_bank)
            }
        }

        binding.btnAddBank.setOnClickListener {
            val dialog = ListBankDialogFragment()
            dialog.show(childFragmentManager, ListBankDialogFragment.TAG)
        }

        val accountHolderStream = RxTextView.textChanges(binding.etAccountHolder)
                .map { holder ->
                    holder.toString().isNotEmpty()
                }
        val accountNumberStream = RxTextView.textChanges(binding.etAccountNumber)
                .map { holder ->
                    holder.toString().isNotEmpty()
                }


        val invalidStream = Observable.combineLatest(
                accountHolderStream,
                accountNumberStream,
                Observable.fromCallable {
                    viewModel.bank != null
                }

        ) { holderValid: Boolean, numberValid: Boolean, bankValid: Boolean ->
            holderValid && numberValid && bankValid
        }

        invalidStream.subscribe { isValid ->
            binding.btnSave.isEnabled = isValid
        }


        binding.btnSave.setOnClickListener {
            if (viewModel.bank.value != null) {
                val newBankAccount = BankAccount(
                    args.account?.id,
                    viewModel.bank.value!!,
                    binding.etAccountHolder.text.toString(),
                    binding.etAccountNumber.text.toString()
                )
                val disposable = viewModel.addOrUpdateAccount(newBankAccount)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        hideKeyboard(view)
                        findNavController().navigateUp()
                    }
                compositeDisposable.add(disposable)
            } else {
                Toast.makeText(requireContext(), "No Bank Supplied.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = requireActivity().getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            view.applicationWindowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

}