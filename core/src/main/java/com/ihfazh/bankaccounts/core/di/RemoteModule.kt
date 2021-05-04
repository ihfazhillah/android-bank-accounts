package com.ihfazh.bankaccounts.data.di

import com.ihfazh.bankaccounts.data.remote.network.BankApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
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
    fun provideClient(): OkHttpClient {
        val hostname = "github.com"
        val certificatePinner = CertificatePinner.Builder()
                .add(hostname, "sha256/4PhpWPCTGkqmmjRFussirzvNSi4LjL7WWhUSAVFIXDc=")
                .add(hostname, "sha256/k2v657xBsOVe1PQRwOsHsw3bsGT2VzIqz5K+59sNQws=")
                .build()

        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
                .build()
    }

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