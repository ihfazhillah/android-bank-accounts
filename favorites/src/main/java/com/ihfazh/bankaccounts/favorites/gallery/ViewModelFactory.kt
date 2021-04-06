package com.ihfazh.bankaccounts.favorites.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(val bankUseCase: BankUseCase) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(bankUseCase) as T
        }
        throw IllegalArgumentException("not valid viewModel: ${modelClass.name}")
    }
}