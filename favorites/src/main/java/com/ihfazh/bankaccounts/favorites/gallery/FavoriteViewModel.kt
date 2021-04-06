package com.ihfazh.bankaccounts.favorites.gallery

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import io.reactivex.Completable
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
        val useCase: BankUseCase
) : ViewModel() {
    fun toggleFavorite(bankAccount: BankAccount): Completable {
        return useCase.toggleFavorite(bankAccount)
    }

    fun deleteBankAccount(bankAccount: BankAccount) = useCase.deleteBankAccount(bankAccount)

    val favoritedBankAccounts = LiveDataReactiveStreams.fromPublisher(useCase.getFavoritedBankAccounts())
}