package com.ihfazh.bankaccounts.ui.banks

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.ihfazh.bankaccounts.domain.usecase.BankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BankViewModel @Inject constructor(private val useCase: BankUseCase) : ViewModel() {

    val allBanks = LiveDataReactiveStreams.fromPublisher(useCase.getAllBanks())
}