package com.ihfazh.bankaccounts.ui.bank_account_create

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(val useCase: BankUseCase) :
    ViewModel() {

    private val search = MutableLiveData<String>()
    fun setSearch(query: String) {
        search.postValue(query)
    }

    val allBanks = Transformations.switchMap(search) { query ->
        if (query == null || query.isEmpty()) {
            LiveDataReactiveStreams.fromPublisher(useCase.getAllBanks())
        } else {
            LiveDataReactiveStreams.fromPublisher(useCase.searchBank(query))
        }
    }

    val bank = MutableLiveData<Bank>()

    fun setBank(bank: Bank) {
        this.bank.postValue(bank)
    }

    fun addOrUpdateAccount(newBankAccount: BankAccount): Completable {
        return if (newBankAccount.id != null) {
            useCase.updateBankAccount(newBankAccount)
        } else {
            useCase.addBankAccount(newBankAccount)
        }
    }
}