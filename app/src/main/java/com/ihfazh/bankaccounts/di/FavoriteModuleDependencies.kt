package com.ihfazh.bankaccounts.di

import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun bankUseCase(): BankUseCase
}