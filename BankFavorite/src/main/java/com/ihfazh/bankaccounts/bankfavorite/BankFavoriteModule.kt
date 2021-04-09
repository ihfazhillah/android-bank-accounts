package com.ihfazh.bankaccounts.bankfavorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ihfazh.bankaccounts.core.annotation.ViewModelKey
import com.ihfazh.bankaccounts.core.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class BankFavoriteModule {
    @Binds
    @IntoMap
    @ViewModelKey(BankFavoritesViewModel::class)
    abstract fun bindsBankFavoritesViewModel(viewModel: BankFavoritesViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}