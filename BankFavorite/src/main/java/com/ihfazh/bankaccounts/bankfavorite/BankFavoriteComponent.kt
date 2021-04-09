package com.ihfazh.bankaccounts.bankfavorite

import android.content.Context
import com.ihfazh.bankaccounts.di.FavoriteModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(
        dependencies = [FavoriteModuleDependencies::class],
        modules = [BankFavoriteModule::class]
)
interface BankFavoriteComponent {
    fun inject(fragment: BankFavoritesFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): BankFavoriteComponent
    }
}