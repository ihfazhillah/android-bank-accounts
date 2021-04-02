package com.ihfazh.bankaccounts.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ihfazh.bankaccounts.data.local.database.AppDatabase
import com.ihfazh.bankaccounts.data.local.database.BankDao
import com.ihfazh.bankaccounts.data.remote.network.BankApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {
    @Provides
    fun provideClient(): OkHttpClient  =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideBankApiService(client: OkHttpClient): BankApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/ihfazhillah/daftar-bank-indonesia/master/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(BankApiService::class.java)
    }


}