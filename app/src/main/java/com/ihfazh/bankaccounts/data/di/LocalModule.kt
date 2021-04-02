package com.ihfazh.bankaccounts.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ihfazh.bankaccounts.data.local.database.AppDatabase
import com.ihfazh.bankaccounts.data.local.database.BankDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    fun provideBankDao(appDatabase: AppDatabase): BankDao  = appDatabase.bankDao()

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "bank-account.db"
        ).fallbackToDestructiveMigration()
            .build()


}