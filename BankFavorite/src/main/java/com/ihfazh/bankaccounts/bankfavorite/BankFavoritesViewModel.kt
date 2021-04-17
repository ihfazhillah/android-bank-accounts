package com.ihfazh.bankaccounts.bankfavorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import javax.inject.Inject

class BankFavoritesViewModel @Inject constructor(private val useCase: BankUseCase) : ViewModel() {
    fun getFavorites() = useCase.getFavoritedbanks().toLiveData()
}