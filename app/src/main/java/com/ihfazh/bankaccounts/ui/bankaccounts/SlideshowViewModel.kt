package com.ihfazh.bankaccounts.ui.bankaccounts

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import javax.inject.Inject


enum class ListChoice {
    All,
    Favorites
}

@HiltViewModel
class SlideshowViewModel @Inject constructor(private val useCase: BankUseCase) : ViewModel() {
    fun deleteBankAccount(bankAccount: BankAccount): Completable {
        return useCase.deleteBankAccount(bankAccount)
    }

//    val bankAccounts = LiveDataReactiveStreams.fromPublisher(useCase.getAllBankAccounts())

    fun toggleFavorite(bankAccount: BankAccount): Completable = useCase.toggleFavorite(bankAccount)

    private val _listChoice = MutableLiveData(ListChoice.All)
    fun getListChoice() = _listChoice
    fun setListChoice(choice: ListChoice) = _listChoice.postValue(choice)

    val bankAccounts = Transformations.switchMap(_listChoice) {
        if (it == ListChoice.All) {
            LiveDataReactiveStreams.fromPublisher(useCase.getAllBankAccounts())
        } else {
            LiveDataReactiveStreams.fromPublisher(useCase.getFavoritedBankAccounts())

        }
    }


}