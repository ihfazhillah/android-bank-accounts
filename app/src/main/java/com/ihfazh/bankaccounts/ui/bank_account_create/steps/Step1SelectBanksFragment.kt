package com.ihfazh.bankaccounts.ui.bank_account_create.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihfazh.bankaccounts.data.Resource
import com.ihfazh.bankaccounts.databinding.FragmentStep1SelectBanksBinding
import com.ihfazh.bankaccounts.ui.bank_account_create.CreateAccountViewModel
import com.ihfazh.bankaccounts.ui.bank_account_create.CreateBankListAdapter
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Step1.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class Step1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentStep1SelectBanksBinding
    private val viewModel: CreateAccountViewModel by viewModels()

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
        binding = FragmentStep1SelectBanksBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Step1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Step1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getAllBanks().observe(requireActivity(), { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvBanks.visibility = View.GONE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.rvBanks.visibility = View.VISIBLE
                    val rvAdapter = CreateBankListAdapter()
                    rvAdapter.submitList(resource.data!!)
                    binding.rvBanks.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = rvAdapter
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        requireContext(),
                        "ERROR: ${resource.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }
}