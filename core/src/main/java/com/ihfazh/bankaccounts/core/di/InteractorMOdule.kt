package com.ihfazh.bankaccounts.data.di

import com.ihfazh.bankaccounts.core.domain.usecase.BankInteractor
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import com.ihfazh.bankaccounts.data.BankRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class InteractorMOdule {
    @Provides
    fun provideUseCase(bankRepository: BankRepository): BankUseCase = BankInteractor(bankRepository)
}