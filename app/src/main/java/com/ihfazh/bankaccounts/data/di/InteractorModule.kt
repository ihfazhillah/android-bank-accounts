package com.ihfazh.bankaccounts.data.di

import com.ihfazh.bankaccounts.data.BankRepository
import com.ihfazh.bankaccounts.domain.usecase.BankInteractor
import com.ihfazh.bankaccounts.domain.usecase.BankUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class InteractorModule {
    @Provides
    fun provideUseCase(bankRepository: BankRepository): BankUseCase = BankInteractor(bankRepository)
}