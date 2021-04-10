package com.ihfazh.bankaccounts.ui.bank_account_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ihfazh.bankaccounts.databinding.FragmentCreateAccountBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                binding.btnAddBank.text = "Change Bank"
            }
        }

        binding.btnAddBank.setOnClickListener {
            val dialog = ListBankDialogFragment()
            dialog.show(childFragmentManager, ListBankDialogFragment.TAG)
        }
    }

}