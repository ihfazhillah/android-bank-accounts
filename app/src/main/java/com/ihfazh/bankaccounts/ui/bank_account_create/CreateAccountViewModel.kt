package com.ihfazh.bankaccounts.ui.bank_account_create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(val useCase: BankUseCase) :
    ViewModel() {

    val bank = MutableLiveData<Bank>()

    fun getAllBanks() = useCase.getAllBanks().toLiveData()
    fun setBank(bank: Bank) {
        this.bank.postValue(bank)
    }
}