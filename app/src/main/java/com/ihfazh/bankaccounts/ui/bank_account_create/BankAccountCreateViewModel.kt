package com.ihfazh.bankaccounts.ui.bank_account_create

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import javax.inject.Inject

@HiltViewModel
class BankAccountCreateViewModel @Inject constructor(val useCase: BankUseCase) : ViewModel() {
    val allBanks = LiveDataReactiveStreams.fromPublisher(useCase.getAllBanks())
    fun insertBankAccount(bankAccount: BankAccount): Completable {
        return useCase.addBankAccount(bankAccount)
    }
}
