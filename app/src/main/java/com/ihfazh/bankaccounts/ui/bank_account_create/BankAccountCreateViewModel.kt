package com.ihfazh.bankaccounts.ui.bank_account_create

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import javax.inject.Inject

@HiltViewModel
class BankAccountCreateViewModel @Inject constructor(val useCase: BankUseCase) : ViewModel() {
    val allBanks = LiveDataReactiveStreams.fromPublisher(useCase.getAllBanks())
    fun insertOrUpdate(bankAccount: BankAccount): Completable {
        if (bankAccount.id != null) return useCase.updateBankAccount(bankAccount)
        return useCase.addBankAccount(bankAccount)
    }

    private val _accountId = MutableLiveData<String>()

    fun getBankAccount(accountId: String): LiveData<BankAccount> {
        return LiveDataReactiveStreams.fromPublisher(
            useCase.getBankAccount(accountId.toInt())
        )
    }

    fun setId(toString: String) {
        _accountId.postValue(toString)
    }


}
