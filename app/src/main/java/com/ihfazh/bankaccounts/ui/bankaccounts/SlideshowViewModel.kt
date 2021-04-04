package com.ihfazh.bankaccounts.ui.bankaccounts

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import javax.inject.Inject

@HiltViewModel
class SlideshowViewModel @Inject constructor(private val useCase: BankUseCase) : ViewModel() {
    fun deleteBankAccount(bankAccount: BankAccount): Completable {
        return useCase.deleteBankAccount(bankAccount)
    }

    val bankAccounts = LiveDataReactiveStreams.fromPublisher(useCase.getAllBankAccounts())

}