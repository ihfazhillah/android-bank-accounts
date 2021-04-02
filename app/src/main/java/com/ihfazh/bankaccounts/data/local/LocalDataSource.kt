package com.ihfazh.bankaccounts.data.local

import com.ihfazh.bankaccounts.data.local.database.AppDatabase
import com.ihfazh.bankaccounts.data.local.entity.BankAccountEntity
import com.ihfazh.bankaccounts.data.local.entity.BankEntity
import com.ihfazh.bankaccounts.domain.data.Bank
import com.ihfazh.bankaccounts.domain.data.BankAccount
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

class LocalDataSource @Inject constructor(private val database: AppDatabase) {
    fun getAllBanks() = database.bankDao().getAll()
    fun addAll(data: List<BankEntity>): Completable = database.bankDao().addAll(data)
    fun addBankAccount(bankAccount: BankAccountEntity): Completable = database.bankAccountDao().insert(bankAccount)
    fun getBankById(id: String): Flowable<BankEntity> = database.bankDao().getById(id)
}