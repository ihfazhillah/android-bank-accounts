package com.ihfazh.bankaccounts.ui.slideshow.di

import android.content.Context
import com.ihfazh.bankaccounts.core.domain.usecase.BankUseCase
import com.ihfazh.bankaccounts.ui.slideshow.BankAccountRecyclerViewAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(FragmentComponent::class)
class BankAccountListModule {
    @Provides
    fun bindBankAccountRecyclerAdapter(
            @ActivityContext context: Context,
            useCase: BankUseCase
    ): BankAccountRecyclerViewAdapter {
        return BankAccountRecyclerViewAdapter(context, useCase)
    }
}