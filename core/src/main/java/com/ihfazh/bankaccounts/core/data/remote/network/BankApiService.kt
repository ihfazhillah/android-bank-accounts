package com.ihfazh.bankaccounts.data.remote.network

import com.ihfazh.bankaccounts.core.data.remote.responses.BankResponse
import io.reactivex.Flowable
import retrofit2.http.GET

interface BankApiService {
    @GET("bank-list.json")
    fun listBank(): Flowable<BankResponse>
}