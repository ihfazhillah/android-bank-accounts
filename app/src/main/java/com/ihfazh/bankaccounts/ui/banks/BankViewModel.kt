package com.ihfazh.bankaccounts.ui.banks

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BankViewModel @Inject constructor(private val useCase: BankUseCase) : ViewModel() {

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
}