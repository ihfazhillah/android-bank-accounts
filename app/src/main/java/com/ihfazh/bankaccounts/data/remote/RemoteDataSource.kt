package com.ihfazh.bankaccounts.data.remote

import android.annotation.SuppressLint
import android.util.Log
import com.ihfazh.bankaccounts.data.remote.network.ApiResponse
import com.ihfazh.bankaccounts.data.remote.network.BankApiService
import com.ihfazh.bankaccounts.data.remote.responses.BanksItem
import com.ihfazh.bankaccounts.domain.data.Bank
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: BankApiService) {
    @SuppressLint("CheckResult")
    fun getAllBanks(): Flowable<ApiResponse<List<BanksItem>>>{
        val resultData = PublishSubject.create<ApiResponse<List<BanksItem>>>()

        val client = apiService.listBank()
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe(
                {
                response ->
                    resultData.onNext(if (response.banks.isNotEmpty()) ApiResponse.Success(response.banks) else ApiResponse.Empty)

                },
                {
                    error ->
                        resultData.onNext(ApiResponse.Error(error.toString()))
                        Log.e("RemoteDataSource: ", error.toString())
                }
            )
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

}