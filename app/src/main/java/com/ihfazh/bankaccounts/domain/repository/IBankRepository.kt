package com.ihfazh.bankaccounts.domain.repository

import com.ihfazh.bankaccounts.data.Resource
import com.ihfazh.bankaccounts.domain.data.Bank
import io.reactivex.Completable
import io.reactivex.Flowable

interface IBankRepository {
    fun getAllBanks(): Flowable<Resource<List<Bank>>>
    fun getBankById(id: Number): Flowable<Bank>
    fun addBank(bank: Bank): Completable
    fun updateBank(bank: Bank): Completable
    fun deleteBank(bank: Bank): Completable
}