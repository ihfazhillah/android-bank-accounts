package com.ihfazh.bankaccounts.ui.bank_detail

import androidx.lifecycle.*
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import javax.inject.Inject

@HiltViewModel
class BankDetailViewModel @Inject constructor(val useCase: BankUseCase) : ViewModel() {
    private val _id: MutableLiveData<String> = MutableLiveData()
    val id: LiveData<String>
        get() = _id

    fun setId(id: String) = _id.postValue(id)

    val bank = Transformations.switchMap(_id) {
        useCase.getBankById(it).toLiveData()
    }

    fun toggleFavorite(): Completable {
        return useCase.toggleBankFavorite(bank.value!!)
    }

}