package com.ihfazh.bankaccounts.ui.slideshow

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SlideshowViewModel @Inject constructor(useCase: BankUseCase) : ViewModel() {
    val bankAccounts = LiveDataReactiveStreams.fromPublisher(useCase.getAllBankAccounts())

}