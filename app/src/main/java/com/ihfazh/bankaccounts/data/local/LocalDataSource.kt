package com.ihfazh.bankaccounts.data.local

import com.ihfazh.bankaccounts.data.local.database.AppDatabase
import com.ihfazh.bankaccounts.data.local.entity.BankEntity
import io.reactivex.Completable
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val database: AppDatabase) {
    fun getAllBanks() = database.bankDao().getAll()
    fun addAll(data: List<BankEntity>): Completable = database.bankDao().addAll(data)
}