package com.ihfazh.bankaccounts.bankfavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihfazh.bankaccounts.bankfavorite.databinding.FragmentBankFavoritesBinding
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.core.viewmodels.ViewModelFactory
import com.ihfazh.bankaccounts.di.FavoriteModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class BankFavoritesFragment : Fragment(), OnFavoriteItemClick {

    private lateinit var binding: FragmentBankFavoritesBinding

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: BankFavoritesViewModel by viewModels {
        factory
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentBankFavoritesBinding.inflate(
                layoutInflater,
                container,
                false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerBankFavoriteComponent.builder()
                .context(requireContext())
                .appDependencies(
                        EntryPointAccessors.fromApplication(
                                requireContext().applicationContext,
                                FavoriteModuleDependencies::class.java
                        )
                )
                .build()
                .inject(this)


        val rvAdapter = FavoritesAdapter(this)
        binding.rvBanks.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context.applicationContext)
        }
        viewModel.getFavorites().observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            binding.rvBanks.visibility = View.VISIBLE
            rvAdapter.submitList(it)
        }
    }

    override fun onCLick(item: Bank) {
        val direction = BankFavoritesFragmentDirections.actionBankFavoritesFragmentToBankDetailFragment(item.id, item.name)
        findNavController().navigate(direction)
    }

}